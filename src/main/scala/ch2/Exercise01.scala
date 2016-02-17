package ch2

import scala.annotation.tailrec
/**
  * Created by huay on 17/02/2016.
  */
class Exercise01 {
  def fib(n: Int): Int = {
    @tailrec
    def loop(n: Int, pre: Int, cur: Int): Int = {
      if (n == 0)
        0
      else
        loop(n - 1, cur, pre + cur)
    }
    loop(n, 0, 1)
  }

}
