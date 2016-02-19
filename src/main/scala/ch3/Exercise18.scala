package ch3

import Exercise13.foldRight
/**
  * Created by huay on 19/02/2016.
  */
object Exercise18 {
  def map[A,B](as: List[A])(f: A=>B): List[B] =
    foldRight(as, Nil: List[B])((e, acc) => Cons(f(e), acc))
}
