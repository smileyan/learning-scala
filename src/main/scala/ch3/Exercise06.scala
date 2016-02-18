package ch3

/**
  * Created by huay on 18/02/2016.
  */
class Exercise06 {
  def init[A](l: List[A]): List[A] =
    l match {
      case Nil => Nil
      case Cons(h,Nil) => Nil
      case Cons(h,t) => Cons(h,init(t))
    }

}
