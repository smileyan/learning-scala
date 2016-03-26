package ch8

import ch6.RNG
import ch6.State
import ch8.Prop.{Result, TestCases, SuccessCount, FailedCase}

/**
  * Created by huay on 18/03/2016.
  */
case class Gen[+A](sample: State[RNG, A]) {
  def listOf[A](a: Gen[A]): Gen[List[A]] = ???


  def forAll[A](a: Gen[A])(f: A => Boolean): Prop = ???

  def choose(start: Int, stopExclusive: Int): Gen[Int] =
    Gen(State(RNG.nonNegativeInt).map(n => start + n % (stopExclusive-start)))

  def unit[A](a: => A): Gen[A] =
    Gen(State.unit(a))

  def boolean: Gen[Boolean] =
    Gen(State(RNG.boolean))

  def flatMap[B](f: A => Gen[B]): Gen[B] =
    Gen(sample.flatMap(a => f(a).sample))

  def listOfN(size: Int): Gen[List[A]] =
    listOfN(size, this)

  def listOfN[A](n: Int, g: Gen[A]): Gen[List[A]] =
    Gen(State.sequence(List.fill(n)(g.sample)))

  def union[A](g1: Gen[A], g2: Gen[A]): Gen[A] =
    boolean.flatMap(b => if (b) g1 else g2)

  def weighted[A](g1: (Gen[A], Double), g2: (Gen[A],Double)): Gen[A] = {
    val g1Threshold = g1._2 / (g1._2.abs + g2._2.abs)

    Gen(State(RNG.double).flatMap(d =>
      if (d < g1Threshold) g1._1.sample else g2._1.sample))
  }
}

//trait Prop {
  // for running a property
  //def check: Either[(FailedCase, SuccessCount), SuccessCount]

  // for composing properties
  //def &&(p: Prop): Prop = new Prop {
    // for running a property
  //  override def check = Prop.this.check && p.check
  //}
//}

object Prop {
  type TestCases = Int
  type Result = Either[(FailedCase, SuccessCount), SuccessCount]
  type FailedCase = String
  type SuccessCount = Int
}

case class Prop(run: TestCases => Result)
object Gen {

}
