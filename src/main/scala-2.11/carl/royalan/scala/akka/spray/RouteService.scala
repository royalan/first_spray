/**
  * Created by carl.huang on 1/18/16.
  */

package carl.royalan.scala.akka.spray

import akka.actor.Actor
import spray.http.MediaTypes._
import spray.routing.HttpService
import spray.util._

import scala.concurrent.duration._

class RouteServiceActor extends Actor with RouteService {

  override implicit def actorRefFactory = context;

  override def receive = runRoute(helloRoute);

}

trait RouteService extends HttpService {

  // we use the enclosing ActorContext's or ActorSystem's dispatcher for our Futures and Scheduler
  implicit def executionContext = actorRefFactory.dispatcher

  val helloRoute =
    get {
      // request method
      path("") {
        respondWithMediaType(`text/html`) {
          // XML is marshalled to `text/xml` by default, so we simply override here
          complete {
            <html>
              <body>
                <h1>Say hello to
                  <i>spray-routing</i>
                  on
                  <i>spray-can</i>
                  !</h1>
              </body>
            </html>
          }
        }
      } ~
      path("ping") {
        complete("PONG")
      }
    } ~
    (post) {
      path("stop") {
        complete {
          in(1.second) {
            actorSystem.shutdown()
          }
          "Shutting down in 1 second..."
        }
      }
    }

  def in[U](duration: FiniteDuration)(body: => U): Unit =
    actorSystem.scheduler.scheduleOnce(duration)(body)

}
