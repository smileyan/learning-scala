package ch3

import Exercise13.foldRight
/**
  * Created by huay on 19/02/2016.
  */
object Exercise16 {
  def add1(as: List[Int]): List[Int] =
    foldRight(as, Nil: List[Int])((a, acc) => Cons(a + 1, acc))
}
