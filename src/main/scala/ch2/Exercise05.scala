package ch2

/**
  * Created by huay on 17/02/2016.
  */
class Exercise05 {
  def compose[A,B,C](f: B => C, g: A => B): A => C =
    a => f(g(a))

}
