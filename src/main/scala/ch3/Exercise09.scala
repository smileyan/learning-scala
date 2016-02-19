package ch3

import List.foldRight
/**
  * Created by huay on 19/02/2016.
  */
object Exercise09 {
  def length[A](as: List[A]): Int =
    foldRight(as, 0)((_,acc) => acc + 1)

}
