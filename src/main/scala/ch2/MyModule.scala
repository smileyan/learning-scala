package ch2

import scala.annotation.tailrec
/**
  * Created by huay on 16/02/2016.
  */
object MyModule {
  def abs(n: Int): Int =
    if (n < 0) -n
    else n

  def factorial(n: Int): Int = {
    @tailrec
    def go(n: Int, acc: Int): Int = {
      if (n <= 0) acc
      else go(n-1, n*acc)
    }

    go(n,1)
  }

  def fib(n: Int): Int = {
    @tailrec
    def loop(n: Int, prev: Int, cur: Int): Int = {
      if (n == 0) 0
      else loop(n-1, cur, cur+prev)
    }

    loop(n, 0, 1)
  }

  def formatResult (name: String, n: Int, f: Int => Int) = {
    val msg = "%s %d %d"
    msg.format(name, n, f(n))
  }

  private def formatAbs(x: Int) = {
    val msg = "The absolute value of %d is %d"
    msg.format(x, abs(x))
  }

  private def formatFactorial(n: Int) = {
    val msg = "%d is %d"
    msg.format(n, factorial(n))
  }

//  def findFirst (ss: Array[String], key: String): Int = {
//    @tailrec
//    def loop(n: Int): Int =
//      if (n >= ss.length) -1
//      else if (ss(n) == key) n
//      else loop(n + 1)
//
//    loop(0)
//  }

  def findFirst[A] (ss: Array[A], p: A => Boolean): Int = {
    @tailrec
    def loop(n: Int): Int =
      if (n >= ss.length) -1
      else if (p(ss(n))) n
      else loop(n + 1)

    loop(0)
  }

  def partial1[A,B,C](a: A, f: (A,B) => C): B => C = (b: B) => f(a, b)

  def main(args: Array[String]) {
    println(formatAbs(-42))
    printf(formatFactorial(7))
    findFirst(Array(7,9,13), (x: Int) => x == 9)
    val f = (x: Double) => math.Pi / 2 - x
    val cos = f andThen math.sin
  }
}
