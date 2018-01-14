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

import spark.{ Compute, Database }

import spray.json._
import java.util.{ Calendar, Date }
import java.text.SimpleDateFormat


final case class Request(begin: Calendar, end: Calendar)
final case class Response(res: List[Compute.AveragePair], r2: Double)


trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit object CalendarFormat extends JsonFormat[Calendar] {
    val calendarFormat = new SimpleDateFormat("yyyy-MM-dd HH")

    def write(date: Calendar) = JsString(calendarFormat.format(date.getTime()))
    def read(json: JsValue) = json match {
      case JsString(rawCalendar) =>
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTime(calendarFormat.parse(rawCalendar))
        calendar
      case error => deserializationError(s"Expected JsString, got $error")
    }
  }

  implicit val requestFormat  = jsonFormat2(Request)
  implicit val averagePairFormat = jsonFormat3(Compute.AveragePair)
  implicit val responseFormat = jsonFormat2(Response)
}

//#user-routes-class
trait Routes extends JsonSupport {
  // we leave these abstract, since they will be provided by the App
  implicit def actorSystem: ActorSystem
  def weatherActor: ActorRef

  // Required by the `ask` (= '?'') method below
  implicit lazy val timeout = Timeout(60.seconds)

  lazy val httpRoutes: Route = {
    path("test") {
      post {
        entity(as[Request]) { request =>
          val keyspace = Database.keyspace
          Database.keyspace = "test"
          onComplete(weatherActor ? GetWeather(request.begin, request.end)) {
            case util.Success(r) =>
                Database.keyspace = keyspace
                val result = r.asInstanceOf[(Array[Compute.AveragePair], Double)]
                complete(Response(result._1.toList, result._2))
            case util.Failure(ex) =>
                Database.keyspace = keyspace
                complete(500, ex.toString)
          }
        }
      }
    } ~
    path("weather") {
      post {
        entity(as[Request]) { request =>
          onComplete(weatherActor ? GetWeather(request.begin, request.end)) {
            case util.Success(r) =>
                val result = r.asInstanceOf[(Array[Compute.AveragePair], Double)]
                complete(Response(result._1.toList, result._2))
            case util.Failure(ex) => complete(500, ex.toString)
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
