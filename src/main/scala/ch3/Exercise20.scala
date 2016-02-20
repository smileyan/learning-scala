package ch3

import Exercise13.foldRight
import Exercise14.append
/**
  * Created by huay on 20/02/2016.
  */
object Exercise20 {
  def flatMap[A,B](as: List[A])(f: A => List[B]): List[B] =
    foldRight(as, Nil:List[B])((a, b) => append(f(a), b))
}
