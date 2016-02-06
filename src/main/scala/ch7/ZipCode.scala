package ch7

/**
  * Created by huay on 6/02/2016.
  */
case class ZipCode(zip: Int, extension: Option[Int] = None) {
  require(valid(zip,extension),
          s"Invalid")

  protected def valid(z: Int, e: Option[Int]): Boolean = {
    if (z > 0 && z < 99999) e match {
      case None => validUSPS(z, 0)
      case Some(e) => 0 < e && e < 9999 && validUSPS(z, e)
    } else {
      false
    }
  }

  protected def validUSPS(z: Int, e: Int) : Boolean = true

  override def toString =
    if (extension != None) {
      s""
    } else {
      zip.toString
    }
}

object ZipCode {
  def apply(z: Int, e: Int): ZipCode =
    new ZipCode(z, Some(e))
}