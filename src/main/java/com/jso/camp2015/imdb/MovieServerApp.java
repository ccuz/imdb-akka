package com.jso.camp2015.imdb;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.jso.camp2015.imdb.repository.MovieRepositoryActor;
import com.jso.camp2015.imdb.rest.MovieRestService;

/**
 * Created by camp2015 on 6/15/15.
 */
public class MovieServerApp {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("MovieServerApp");

        ActorRef calculatorServiceActor = system.actorOf(
                MovieRepositoryActor.propsFor("mongodb://localhost/movie"), "movieRepository");

        MovieRestService endpoint = new MovieRestService(system, calculatorServiceActor, "localhost", 3456);

        system.awaitTermination();
    }
}
