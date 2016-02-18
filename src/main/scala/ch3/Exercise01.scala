package ch3

/**
  * Created by huay on 18/02/2016.
  */
class Exercise01 {
  val x = List(1,2,3,4,5) match {
    case Cons(x, Cons(2, Cons(4, _))) => x
    case Nil => 42
    case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
    case _ => 101
  }

  def main(args: Array[String]) {
    // x == 1 + 2
  }
}
