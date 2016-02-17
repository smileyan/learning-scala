package ch2

/**
  * Created by huay on 17/02/2016.
  */
class Exercise04 {
  def uncurry[A,B,C](f: A => B => C): (A, B) => C =
    (a, b) => f(a)(b)
}
