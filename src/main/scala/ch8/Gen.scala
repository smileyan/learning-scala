package ch8

import ch6.RNG
import ch6.State
import ch8.Prop.{SuccessCount, FailedCase}

/**
  * Created by huay on 18/03/2016.
  */
case class Gen[+A](sample: State[RNG, A]) {
  def listOf[A](a: Gen[A]): Gen[List[A]] = ???

  def listOfN[A](n: Int, a: Gen[A]): Gen[List[A]] = ???

  def forAll[A](a: Gen[A])(f: A => Boolean): Prop = ???

  def choose(start: Int, stopExclusive: Int): Gen[Int] =
    Gen(State(RNG.nonNegativeInt).map(n => start + n % (stopExclusive-start)))
}

trait Prop {
  // for running a property
  def check: Either[(FailedCase, SuccessCount), SuccessCount]

  // for composing properties
  //def &&(p: Prop): Prop = new Prop {
    // for running a property
  //  override def check = Prop.this.check && p.check
  //}
}

object Prop {
  type FailedCase = String
  type SuccessCount = Int
}
object Gen {

}
