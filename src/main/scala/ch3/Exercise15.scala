package ch3

import Exercise14.append
import Exercise13.foldRight
/**
  * Created by huay on 19/02/2016.
  */
object Exercise15 {
  def concat[A](as: List[List[A]]): List[A] =
    foldRight(as, List[A]())(append)
}

