package scala

import org.specs2._
// import ch4._

object FunctionalProgrammingSpec extends org.specs2.mutable.Specification {
  "Anonymous Functions, Lambdas, and Clsures" >> {
    val hofs = (1 to 10) filter (_ % 2 == 0) map (_ * 2) reduce (_ * _)
    hofs must_== 122880

    var factor = 2 // free variable
    val multiplier = (i: Int) => i * factor
    val r1 = (1 to 10) filter (_ % 2 == 0) map multiplier reduce (_ * _)
    r1 must_== 122880

    factor = 3
    val r2 = (1 to 10) filter (_ % 2 == 0) map multiplier reduce (_ * _)
    r2 must_== 933120

    def m1(multiplier: Int => Int) = {
      (1 to 10) filter (_ % 2 == 0) map multiplier reduce (_ * _)
    }

    def m2: Int => Int = {
      val factor = 2
      val multiplier = (i: Int) => i * factor
      multiplier
    }

    m1(m2) must_== 122880

    def factorial(i: BigInt): BigInt = {
      if (i == 1) i
      else i * factorial(i - 1)
    }

    factorial(3) must_== 6
  }
  "Tail Calls and Tail-Call Optimization" >> {
    import scala.annotation.tailrec

    def factorial(i: BigInt): BigInt = {
      @tailrec
      def fact(i: BigInt, accumulator: BigInt): BigInt =
        if(i == 1){
          accumulator
        } else {
          fact(i - 1, i * accumulator)
        }

      fact(i, 1)
    }
    factorial(3) must_== 6
  }
  // "Trampoline for Tail Calls" >> {
  //   import scala.util.control.TailCalls._

  //   def isEven(xs: List[Int]): TailRec[Boolean] =
  //     if (xs.isEmpty) done(true) else tailcall(isOdd(xs.tail))

  //   def isOdd(xs: List[Int]): TailRec[Boolean] =
  //     if (xs.isEmpty) done(false) else tailcall(isEven(xs.tail))

  //   isEven((1 to 1).toList).result must_== false

  // }
  "Partially Applied Functions Versus Partial Functions" >> {
    def cat1(s1: String)(s2: String) = s1 + s2
    cat1("Hello, ")("world!") must_== "Hello, world!"

    val hello = cat1("Hello, ")_ // <function1>
    hello("world!") must_== "Hello, world!"

  }
}