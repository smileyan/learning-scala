package ch3

/**
  * Created by huay on 18/02/2016.
  */
class Exercise03 {
  def setHead[A](a: A, l: List[A]): List[A] = l match {
    case Nil => Nil
    case Cons(_,t) => Cons(a, t)
  }
}
