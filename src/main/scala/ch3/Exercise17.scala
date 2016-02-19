package ch3

import Exercise13.foldRight
/**
  * Created by huay on 19/02/2016.
  */
object Exercise17 {
  def doubleToStr(as: List[Double]): List[String] =
    foldRight(as, Nil:List[String])((a, acc) => Cons(a.toString, acc))
}
