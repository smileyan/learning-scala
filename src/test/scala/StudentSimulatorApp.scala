import TeacherProtocal.{QuoteResponse, QuoteRequest}
import akka.actor.{Props, ActorSystem, Actor}

/**
  * Created by huay on 12/04/2016.
  */
class StudentSimulatorApp {
  val actorSystem = ActorSystem("UniversityMessageSystem")

  val teacherActorRef = actorSystem.actorOf(Props[TeacherActor])

  teacherActorRef!QuoteRequest

  Thread.sleep(2000)

  actorSystem.shutdown()
}

object TeacherProtocal {
  case class QuoteRequest()
  case class QuoteResponse(quoteString: String)
}

class TeacherActor extends Actor {

  val quotes = List(
    "1",
    "2"
  )
  def receive = {
    case QuoteRequest => {
      import util.Random

      val quoteResponse = QuoteResponse(quotes(Random.nextInt(quotes.size)))

      println(quoteResponse)
    }
  }
}