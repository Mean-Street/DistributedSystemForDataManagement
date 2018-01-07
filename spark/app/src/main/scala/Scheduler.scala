//Schedule l'écriture vers Kafka --> inutile si marathon s'en charge à la place
//TODO Installer AKKA
/*
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;

object ProducerScheduler {
	def main(args: Array[String]) {
          /*
		val actorSystem = ActorSystem()
		val scheduler = actorSystem.scheduler
		val task = new Runnable { def run() = WeatherProducer.main(Array("topics", "@broker")) }
		implicit val executor = actorSystem.dispatcher

		scheduler.schedule(
		  initialDelay = Duration.Zero(),
		  interval = Duration(60, TimeUnit.SECONDS),
		  runnable = task)
*/
	}
}
*/
