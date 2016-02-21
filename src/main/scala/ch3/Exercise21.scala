package ch3

import Exercise20.flatMap
/**
  * Created by huay on 21/02/2016.
  */
object Exercise21 {
  def filter[A](as: List[A])(f: A => Boolean): List[A] =
    flatMap(as)(a => if (f(a)) List(a) else Nil )

}
