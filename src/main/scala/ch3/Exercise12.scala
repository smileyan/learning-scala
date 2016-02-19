package ch3

import Exercise10.foldLeft
/**
  * Created by huay on 19/02/2016.
  */
object Exercise12 {
  def reverse[A](as: List[A]): List[A] =
    foldLeft(as, List[A]())((acc, h) => Cons(h, acc))
}
