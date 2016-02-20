package ch3

import Exercise10.foldLeft
/**
  * Created by huay on 19/02/2016.
  */
object Exercise11 {
  def sum[A](as: List[Int]): Int =
    foldLeft(as, 0)(_ + _)

  def product(as: List[Double]): Double =
    foldLeft(as, 0.0)(_ * _)
}
