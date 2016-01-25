package carl.royalan.scala.akka.spray

import akka.actor.{Actor, ActorLogging}
import spray.can.Http
import spray.http.HttpMethods._
import spray.http._
import spray.routing.HttpService

import scala.concurrent.duration._

/**
  * Created by carl.huang on 1/18/16.
  */

class CanServiceActor extends Actor with ActorLogging with HttpService {

  override implicit def actorRefFactory = context

  import context.dispatcher // ExecutionContext for the futures and scheduler

  override def receive = {
    case _: Http.Connected =>
      log.info("Http connected!")
      sender ! Http.Register(self)

    case HttpRequest(GET, Uri.Path("/ping"), _, _, _) =>
      sender ! HttpResponse(entity = "PONG!")

    case HttpRequest(POST, Uri.Path("/stop"), _, _, _) =>
      sender ! HttpResponse(entity = "Shutting down in 1 second ...")
      sender ! Http.Close
      context.system.scheduler.scheduleOnce(1.second) {
        context.system.shutdown()
      }

    case _: HttpRequest =>
      log.info("Default")
      sender ! HttpResponse(status = 404, entity = "Unknown resource!")
  }

}