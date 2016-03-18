package ch8

import ch6.RNG
import ch6.State

/**
  * Created by huay on 18/03/2016.
  */
case class Gen[+A](sample: State[RNG, A]) {
  def listOf[A](a: Gen[A]): Gen[List[A]] = ???

  def listOfN[A](n: Int, a: Gen[A]): Gen[List[A]] = ???

  def forAll[A](a: Gen[A])(f: A => Boolean): Prop = ???

}

trait Prop { def &&(p: Prop): Prop }

object Gen {

}
