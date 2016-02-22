package ch3

/**
  * Created by huay on 22/02/2016.
  */
class Exercise26 {
  def maximum(t: Tree[Int]): Int = t match {
    case Branch(l,r) => maximum(l) max maximum(r)
    case Leaf(v) => v
  }

}
