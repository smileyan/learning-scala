package ch3

import Exercise13.foldRight
/**
  * Created by huay on 19/02/2016.
  */
object Exercise19 {
  def filter[A](as: List[A])(f: A => Boolean): List[A] =
    foldRight(as, Nil: List[A])((e, acc) => if (f(e)) Cons(e, acc) else acc)
}
