package ch3

import scala.annotation.tailrec

/**
  * Created by huay on 18/02/2016.
  */
object Exercise04 {
  @tailrec
  def drop[A](l: List[A], n: Int): List[A] =
    if (n==0) l
    else drop(Exercise02.tail(l), n-1)
}
