package ch3

/**
  * Created by huay on 22/02/2016.
  */
class Exercise22 {
  def addPairList(as: List[Int], bs: List[Int]): List[Int] = (as, bs) match {
    case (Nil,_) => Nil
    case (_, Nil) => Nil
    case (Cons(h1, t1), Cons(h2, t2)) => Cons(h1 + h2, addPairList(t1, t2))
  }

}
