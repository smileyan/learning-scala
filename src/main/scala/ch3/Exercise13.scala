package ch3

import Exercise12.reverse
import Exercise10.foldLeft
/**
  * Created by huay on 19/02/2016.
  */
object Exercise13 {
  def foldRight[A,B](as: List[A], acc: B)(f: (A,B) => B): B =
    foldLeft(reverse(as), acc)((b, a) => f(a, b))
}
