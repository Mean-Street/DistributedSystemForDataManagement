package server

import akka.actor.{ ActorRef, ActorSystem }

import scala.concurrent.duration._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.delete
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.MethodDirectives.post
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.http.scaladsl.server.directives.PathDirectives.path

import scala.concurrent.Future
import server.WeatherActor._
import akka.pattern.ask
import akka.util.Timeout

//#user-routes-class
trait Routes {

  // we leave these abstract, since they will be provided by the App
  implicit def actorSystem: ActorSystem
  def weatherActor: ActorRef

  // Required by the `ask` (= '?'') method below
  implicit lazy val timeout = Timeout(5.seconds)

  //#all-routes
  //#users-get-post
  //#users-get-delete   
  lazy val httpRoutes: Route = {
    path("weather") {
      get {
        /*onComplete(weatherActor ? GetWeather) {
          case util.Success(value) => complete(200, value.toString)
          case util.Failure(ex) => complete(500, ex.toString)
        }*/
        complete {"hey\n"}
      }
    } ~
    path("example") {
      get {
        complete {
          "test réussi\n"
        }
      }
    }
  }
}