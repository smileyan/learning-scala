package ch3

import scala.annotation.tailrec

/**
  * Created by huay on 19/02/2016.
  */
object Exercise10 {
  @tailrec
  def foldLeft[A,B](as: List[A], z: B)(f: (B, A) => B): B =
    as match {
      case Nil => z
      case Cons(h,t) => foldLeft((t),f(z,h))(f)
    }
}
