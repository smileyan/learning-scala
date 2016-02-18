package ch3

/**
  * Created by huay on 18/02/2016.
  */
object Exercise02 {
  def tail[A](l: List[A]): List[A] = l match {
    case Nil => Nil
    case Cons(h,t) => t
  }
}
