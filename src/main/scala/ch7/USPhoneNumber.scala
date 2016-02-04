package ch7

/**
  * Created by huay on 4/02/2016.
  */
class USPhoneNumber(val s: String) extends AnyVal {
  override def toString = {
    val digs = digits(s)
    val areaCode = digs.substring(0, 3)
    val exchange = digs.substring(3, 6)
    val subnumber = digs.substring(6, 10)
    s"($areaCode) $exchange-$subnumber"
  }

  private def digits(str: String): String = str.replaceAll("""\D""", "")
}
