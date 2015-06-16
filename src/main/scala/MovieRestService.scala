/*package com.jso.camp2015.imdb.rest
import akka.actor.{ActorRef, ActorSystem}
import akka.http.server.Directives._
import akka.http.server.japi.HttpService
import akka.http.server.{Route, HttpService}

class MovieRestService(val system: ActorSystem, val controllerActor: ActorRef, val hostname: String, val portNumber: Int) {



  HttpService.bindRoute(hostname, portNumber, createRestServiceRoutes(), system)

  def createRestServiceRoutes(): Route = {
    route(pathSingleSlash.route(getFromResource("web/index.html")), pathPrefix("js").route(getFromResourceDirectory("web/js/")), path("movie").route(get(handleWith(getMoviesHandler))), path("movie", movieId).route(get(handleWith(getMoviesWithIdHandler, movieId))))
  }

}
*/