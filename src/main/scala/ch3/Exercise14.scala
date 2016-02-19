package ch3

import Exercise13.foldRight
/**
  * Created by huay on 19/02/2016.
  */
object Exercise14 {
  def append[A](as: List[A], r: List[A]): List[A] =
    foldRight(as, r)(Cons(_,_))

}
