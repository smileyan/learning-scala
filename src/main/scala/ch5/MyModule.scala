package ch5

/**
  * Created by huay on 26/02/2016.
  */
class MyModule {

}

object MyModule {
  val queens = List(1,2,3,4).map(_ + 10).filter(_ % 2 == 0).map(_ * 3)

  //  Non-strictness is a property of a function

  def square(x: Double): Double = x * x

  var result = if (true) sys.error("empyt") else false

  def if2[A](cond: Boolean, onTrue: () => A, onFalse: () => A): A =
    if (cond) onTrue() else onFalse()

  def maybeTwice(b: Boolean, i: => Int) = if (b) i+i else 0

  def maybeTwice2(b: Boolean, i: => Int): Int = {
    lazy val j = i
    if (b) j + j else 0
  }


  def main(args: Array[String]) {
    if2(11 < 22, () => println("a"), () => println("b"))

    val x = maybeTwice(true, { println("1"); 1 + 41 })

    val y = maybeTwice2(true, { println("1"); 1 + 41 })
  }
}
