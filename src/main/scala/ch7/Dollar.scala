package ch7

/**
  * Created by huay on 4/02/2016.
  */
class Dollar(val value: Float) extends AnyVal {
  override def toString = "$%.2f".format(value)
}
