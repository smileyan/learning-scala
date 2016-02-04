package ch7

/**
  * Created by huay on 4/02/2016.
  */
class PhoneNumber(val s:String) extends AnyVal
  with Digitizer with Formatter {
  override def toString = {
    val digs = digits(s)
    val areacode = digs.substring(0, 3)
    val exchange = digs.substring(3, 6)
    val subnumber = digs.substring(6, 10)
    format(areacode, exchange, subnumber)
  }
}

trait Digitizer extends Any {
  def digits(s: String): String = s.replaceAll("""D""", "")
}

trait Formatter extends Any {
  def format(areacode: String, exchange: String, subnumber: String): String =
    s"($areacode) $exchange-$subnumber"
}