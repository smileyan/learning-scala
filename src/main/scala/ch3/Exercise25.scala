package ch3

/**
  * Created by huay on 22/02/2016.
  */
class Exercise25 {
  def size[A](t: Tree[A]): Int = t match {
    case Branch(l, r) => size(l) + size(r) + 1
    case Leaf(v) => 1
  }

}
