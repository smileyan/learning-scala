package ch3

/**
  * Created by huay on 22/02/2016.
  */
class Exercise29 {
  def fold[A,B](t: Tree[A])(f: A => B)(g: (B, B) => B): B = t match {
    case Leaf(a) => f(a)
    case Branch(l,r) => g(fold(l)(f)(g), fold(r)(f)(g))
  }

  def size[A](t: Tree[A]): Int =
    fold(t)(a => 1)(1+_+_)

  def maximum(t: Tree[Int]): Int =
    fold(t)(a => a)(_ max _)

  def depth[A](t: Tree[A]): Int =
    fold(t)(a => 0)((b1,b2) => 1 + (b1 max b2))

  def map[A,B](t: Tree[A])(f: A => B): Tree[B] =
    fold(t)(a => Leaf(f(a)): Tree[B])(Branch(_,_))

}
