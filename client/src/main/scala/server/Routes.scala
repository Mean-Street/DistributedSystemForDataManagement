package server
import akka.actor.{ ActorRef, ActorSystem }
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.{ delete, get, post }
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Future
import scala.concurrent.duration._

import server.WeatherActor._

import spark.Compute

import spray.json._


final case class WeatherRequest(begin: String, end: String)
final case class WeatherResponse(res: List[Compute.DataEntry])

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val weatherRequestFormat  = jsonFormat2(WeatherRequest)
  implicit val dataEntryFormat       = jsonFormat3(Compute.DataEntry)
  implicit val weatherResponseFormat = jsonFormat1(WeatherResponse)
}

//#user-routes-class
trait Routes extends JsonSupport {

  // we leave these abstract, since they will be provided by the App
  implicit def actorSystem: ActorSystem
  def weatherActor: ActorRef

  // Required by the `ask` (= '?'') method below
  implicit lazy val timeout = Timeout(60.seconds)

  lazy val httpRoutes: Route = {
    path("weather") {
      post {
        entity(as[WeatherRequest]) { request =>
          onComplete(weatherActor ? GetWeather(request.begin, request.end)) {
            case util.Success(result) => complete(WeatherResponse(result.asInstanceOf[List[Compute.DataEntry]]))
            case util.Failure(ex)     => complete(500, ex.toString)
          }
        }
      }
    } ~
    path("example") {
      get {
        complete {
          "test réussi"
        }
      }
    }
  }
}
