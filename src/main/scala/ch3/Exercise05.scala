package ch3

/**
  * Created by huay on 18/02/2016.
  */
object Exercise05 {
  def dropWhile[A](l: List[A], f: A => Boolean): List[A] = l match {
    case Nil => Nil
    case Cons(h,t) => if (f(h)) dropWhile(t,f) else Cons(h,dropWhile(t,f))
  }

  def dropWhile1[A](l: List[A], f: A => Boolean): List[A] = l match {
    case Cons(h,t) if f(h) => dropWhile1(t,f)
    case _ => l
  }

  def dropWhile2[A](l: List[A])(f: A => Boolean): List[A] = l match {
    case Cons(h,t) if f(h) => dropWhile2(t)(f)
    case _ => l
  }

  def main(args: Array[String]) {
    val xs: List[Int] = List(1,2,3,4,5)
    val ex1 = dropWhile(xs, (x: Int) => x < 4)
  }
}
