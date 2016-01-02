package ch3

object TryCatch{
  def main(args: Array[String]) = {
    args foreach ( arg => countLine(arg) )
  }

  import scala.io.Source
  import scala.util.control.NonFatal

  def countLine(fileName: String) = {
    var source: Option[Source] = None
    try { 
      source = Some(Source.fromFile(fileName))
      val size = source.get.getLines.size
    } catch {
      case NonFatal(ex) => println(s"$ex")
    } finally {
      for (s <- source) {
        s.close
      }
    }
  }
}