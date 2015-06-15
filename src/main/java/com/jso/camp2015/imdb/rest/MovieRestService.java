package com.jso.camp2015.imdb.rest;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.marshallers.jackson.Jackson;
import akka.http.model.japi.StatusCodes;
import akka.http.server.japi.*;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.jso.camp2015.imdb.model.Movie;
import com.jso.camp2015.imdb.repository.MovieRepositoryActor;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.List;

import static akka.http.server.japi.Directives.*;
import static akka.http.server.japi.Directives.get;


/**
 * Sample REST service implementation using Akka Http.io extension
 */
public class MovieRestService {

    private final ActorSystem system;
    private final ActorRef controllerActor;

    public MovieRestService(ActorSystem system, ActorRef controllerActor, String hostname, int portNumber) {
        this.system = system;
        this.controllerActor = controllerActor;

        HttpService.bindRoute(hostname, portNumber, createRestServiceRoutes(), system);
    }

    private Route createRestServiceRoutes() {
        final Timeout timeout = new Timeout(Duration.create(5, "seconds"));

        final PathMatcher<String> movieId = PathMatchers.segment();

        Handler getMoviesHandler = new Handler() {
            @Override
            public RouteResult handle(RequestContext ctx) {
                Future<Object> future = Patterns.ask(controllerActor,
                        new MovieRepositoryActor.ListAllMoviesCommand(), timeout);
                try {
                    List<Movie> response = (List<Movie>) Await.result(future, timeout.duration());
                    return ctx.completeAs(Jackson.json(), response);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ctx.completeWithStatus(StatusCodes.INTERNAL_SERVER_ERROR);
                }
            }
        };

        Handler getMoviesWithIdHandler = new Handler() {
            @Override
            public RouteResult handle(RequestContext ctx) {
                final String id = movieId.get(ctx);
                Future<Object> future = Patterns.ask(controllerActor, new MovieRepositoryActor.MoviesDetailsCommand(id), timeout);
                try {
                    List<Movie> response = (List<Movie>) Await.result(future, timeout.duration());
                    return ctx.completeAs(Jackson.json(), response);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ctx.completeWithStatus(StatusCodes.INTERNAL_SERVER_ERROR);
                }
            }
        };

        return route(
                // matches the empty path -> static resource
                pathSingleSlash().route(
                        getFromResource("web/index.html")
                ),
                // static resources
                pathPrefix("js").route(
                        getFromResourceDirectory("web/js/")
                ),
                // matches paths like this: /movie
                path("movie").route(
                        get(handleWith(getMoviesHandler))
                ),
                // matches paths like this: /movie/id
                path("movie", movieId).route(
                        get(handleWith(getMoviesWithIdHandler, movieId))
                )
        );
    }


}
