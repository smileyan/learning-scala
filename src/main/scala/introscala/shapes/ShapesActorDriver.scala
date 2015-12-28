package introscala.shapes
import akka.actor.{Props, Actor, ActorRef, ActorSystem}
import com.typesafe.config.ConfigFactory

private object Start

object ShapesActorDriver {
  def main(args: Array[String]) = {
    val system = ActorSystem("DrawingActorSystem", ConfigFactory.load())
    val drawer = system.actorOf(
      Props(new ShapesDrawingActor ), "drawingActor")
    val driver = system.actorOf(
      Props(new ShapesActorDriver(drawer) ), "drawingService")
    driver ! Start
  }
}

class ShapesActorDriver(drawerActor: ActorRef) extends Actor {
  import Messages._

  def receive = {
    case Start =>
      drawerActor ! Circle(Point(0.0, 0.0), 1.0)
      drawerActor ! 314
      drawerActor ! Exit
    case Finished =>
      println(s"ShapesActorDriver cleaning up...")
      context.system.shutdown()  
    case response: Response =>
      println("ShapesActorDriver Response:" + response)  
    case unexcepted =>
      println("ub")  
  }
}