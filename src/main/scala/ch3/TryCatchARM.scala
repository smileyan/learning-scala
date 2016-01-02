package ch3

import scala.language.reflectiveCalls
import scala.util.control.NonFatal

object manage {
  def apply[R <: { def close():Unit }, T] (resource: => R ) (f: R => T): T = {
    var res: Option[R] = None
    try {
      res = Some(resource)
      f(res.get)
    } catch {
      case NonFatal(ex) =>
        println(s"manage.apply(): Non fatal exception! $ex")
        throw ex
    } finally {
      if (res != None) {
        println(s"Closing resource...")
        res.get.close
      }
    }
  }
}

object TryCatchRAM{
  def main(args: Array[String]) = {
    args foreach ( arg => countLine(arg) )
  }

  import scala.io.Source
  def countLine(fileName: String) = {
    manage(Source.fromFile(fileName)) { source =>
      val size = source.getLines.size
    }
  }
}

object CallByName{
  @annotation.tailrec
  def continu(conditional: Boolean)(body: => Unit):Unit = {
    if(conditional) {
      body
      continu(conditional)(body)
    }
  }

  var count = 0
  continu(count < 5) {
    println(s"at count")
    count = count + 1
  }
}