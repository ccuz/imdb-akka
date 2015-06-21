package com.jso.camp2015.imdb.repository;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.jso.camp2015.imdb.model.Movie;
import com.mongodb.Function;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.async.client.MongoIterable;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

/**
 * Created by camp2015 on 6/15/15.
 */
public class MovieRepositoryActor extends UntypedActor {
    LoggingAdapter loggingAdapter = Logging.getLogger(getContext().system(), this);

    public static final class ListAllMoviesCommand {
    }

    public static final class MoviesDetailsCommand {
        private final String id;
        public MoviesDetailsCommand(String id){
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    private static final class MovieDocumentMapper implements Function<Document, Movie>{
        @Override
        public Movie apply(Document document) {
            //Seems some Imdb may be populated with objectId. Mine definitively not. I only have _id as primary key...
            Movie movie = new Movie(document.containsKey("objectId")? document.getObjectId("objectId").toHexString()
                    : document.get("_id").toString());
            movie.setMovieID(document.getString("MovieID"));
            movie.setSeriesID(document.getString("SeriesID"));
            movie.setSeriesType(document.getString("SeriesType"));
            movie.setSeriesEndYear(document.getString("SeriesEndYear"));
            movie.setReleaseYear(document.getString("ReleaseYear"));
            return movie;
        }
    }

    private static final String dbInstanceName = "movies";

    /**
     * Connect to a running mangodb
     * @param connectionUrl mongodb://localhost/movie
     * @return
     */
    public static Props propsFor(final String connectionUrl){
        return Props.create(MovieRepositoryActor.class, ()->new MovieRepositoryActor(connectionUrl));
    }

    public static Props propsFor(){
        return Props.create(MovieRepositoryActor.class, ()->new MovieRepositoryActor(null));
    }

    private MongoClient mongoClient;

    private MovieRepositoryActor(String connectionUrl){
        if (connectionUrl == null){
            connectionUrl = getContext().system().settings().config().getString("movie.mongodb.url");
        }
        mongoClient = MongoClients.create(connectionUrl);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof ListAllMoviesCommand){
            final List<Movie> movies = new ArrayList<Movie>();
            final MongoIterable<? extends Movie> result =
                    db().getCollection("Movie").find().limit(10).map(new MovieDocumentMapper());
            result.forEach(movie -> movies.add(movie),
                    (allmovies, throwbale) -> loggingAdapter.debug("ListAllMoviesCommand: "));
            getSender().tell(movies, getSelf());
        }
        else if (message instanceof MoviesDetailsCommand){
            final List<Movie> movies = new ArrayList<Movie>();
            final String id = ((MoviesDetailsCommand)message).getId();
            final MongoIterable<? extends Movie> result =
                    db().getCollection("Movie").find(eq("_id", id)).map(new MovieDocumentMapper());
            result.forEach(movie -> movies.add(movie),
                    (allmovies, throwbale) -> loggingAdapter.debug("MoviesDetailsCommand: ")
            );
            getSender().tell(movies, getSelf());
        }
    }


    private MongoDatabase db(){
        return mongoClient.getDatabase(dbInstanceName);
    }
}
