package ch2

/**
  * Created by huay on 17/02/2016.
  */
class Exercise02 {
  def isSorted[A] (as: Array[A], ordered: (A, A) => Boolean): Boolean = {
    def loop(n: Int): Boolean =
      if (n >= as.length) true
      else if (ordered(as(n), as(n+1))) false
      else loop(n+1)

    loop(0)
  }

}
