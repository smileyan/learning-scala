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
  }
}