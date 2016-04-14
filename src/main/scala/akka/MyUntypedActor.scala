package akka

import akka.actor.{Props, ActorSystem. Actor}

import TeachProtocal.QuoteRequest

/**
  * Created by huay on 14/04/2016.
  */
object StudentSimulatorApp {
  val actorSystem = ActorSystem("UniversityMessageSystem")

  val teacherActorRef = actorSystem.actorOf(Props[TeacherActor])

  teacherActorRef!QuoteRequest

  Thread.sleep(2000)

  actorSystem.shutdown()
}

object TeachProtocal {
  case class QuoteRequest()
  case class QuoteResponse(quoteString:String)
}

class TeachActor extends Actor {
  def receive = {
    case QuoteRequest => {

    }
  }
}