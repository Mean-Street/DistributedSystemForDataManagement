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
import java.util.{ Calendar, Date }
import java.text.SimpleDateFormat


final case class Request(begin: String, end: String)
final case class Response(res: List[Compute.AveragePair], r2: Double)


trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit object CalendarFormat extends JsonFormat[Calendar] {
    val calendarFormat = new SimpleDateFormat("dd/mm/yyyy hh")

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
  implicit val averagePairFormat     = jsonFormat3(Compute.AveragePair)
  implicit val responseFormat = jsonFormat2(Response)
}

//#user-routes-class
trait Routes extends JsonSupport {
  def toCalendar(date: String): Calendar = {
    val calendar: Calendar = Calendar.getInstance()
    calendar.setTime(new SimpleDateFormat("dd/mm/yyyy hh").parse(date))
    calendar
  }

  // we leave these abstract, since they will be provided by the App
  implicit def actorSystem: ActorSystem
  def weatherActor: ActorRef

  // Required by the `ask` (= '?'') method below
  implicit lazy val timeout = Timeout(60.seconds)

  lazy val httpRoutes: Route = {
    path("weather") {
      post {
        entity(as[Request]) { request =>
          onComplete(weatherActor ? GetWeather(toCalendar(request.begin), toCalendar(request.end))) {
            case util.Success(r) => 
                val result = r.asInstanceOf[(Array[Compute.AveragePair], Double)]
                complete(Response(result._1.toList, result._2))
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
