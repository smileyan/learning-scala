package ch3

/**
  * Created by huay on 22/02/2016.
  */
class Exercise23 {
  def zipWith[A,B,C](as: List[A], bs: List[B])(f: (A,B) => C): List[C] = (as, bs) match {
    case (Nil,_) => Nil
    case (_,Nil) => Nil
    case (Cons(h1,t1), Cons(h2,t2)) => Cons(f(h1,h2),zipWith(t1,t2)(f))
  }
}
