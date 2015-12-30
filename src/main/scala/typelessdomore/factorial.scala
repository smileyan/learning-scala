import scala.annotation.tailrec

object f {
  def factorial(i: Int): Long = {
    @tailrec
    def fact(i: Int, accu: Int): Long = {
      if (i <= 1) accu
      else fact(i - 1, accu * i)
    }

    fact(i, 1)
  }

  (0 to 5) foreach (i => println(factorial(i)))

  // @tailrec
  def fabonacci(i: Int): Long = {
    if (i <= 1) 1L
    else fabonacci(i - 1) + fabonacci(i - 2)
  }

  def countTo(n: Int): Unit = {
    def count(i: Int): Unit = {
      if (i <= n) { println(i); count(i + 1) }
    }

    count(1)
  }

  countTo(10)

}
object StringUtilV1 {
  def joiner(strings: String*): String = strings.mkString("_")

  def joiner(strings: List[String]): String = joiner(strings :_*)

}

println(StringUtilV1.joiner(List("a","b")))
