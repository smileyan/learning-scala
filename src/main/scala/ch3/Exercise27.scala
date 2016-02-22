package ch3

/**
  * Created by huay on 22/02/2016.
  */
class Exercise27 {
  def depth[A](t: Tree[A]): Int = t match {
    case Branch(l, r) => depth(l) max depth(r)
    case Leaf(v) => 0
  }

}
