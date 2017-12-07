package ClientApi

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.{HttpApp, Route}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val grenobleTemperatureFormat: RootJsonFormat[GrenobleTemperatureRequest]
      = jsonFormat0(GrenobleTemperatureRequest.apply)
  implicit val temperatureFormat: RootJsonFormat[TemperatureRequest]
      = jsonFormat1(TemperatureRequest.apply)
}

object WeatherRoutes extends HttpApp with JsonSupport {

  override protected def routes: Route = {
    post {
      pathPrefix("temperature-grenoble") {
        entity(as[GrenobleTemperatureRequest]) { request =>
          // DO COMPUTATION
          complete("MOCK TEMPERATURE")
        }
      } ~
      pathPrefix("temperature") {
        entity(as[TemperatureRequest]) { request =>
          // DO COMPUTATION
          complete("MOCK TEMPERATURE FOR: " + request.city)
        }
      }
    }
  }

}

case class GrenobleTemperatureRequest()
case class TemperatureRequest(city: String)
