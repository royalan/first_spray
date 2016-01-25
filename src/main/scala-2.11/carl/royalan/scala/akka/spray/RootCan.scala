/**
  * Created by carl.huang on 1/18/16.
  */

package carl.royalan.scala.akka.spray

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import spray.can.Http

import scala.concurrent.duration._

object RootCan extends App {

  implicit val system = ActorSystem("first-spray")

  val service = system.actorOf(Props[RouteServiceActor],"hello-spray")

  implicit val timeout : Timeout = 10.seconds

  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8980)
}
