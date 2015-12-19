package scala.school

import org.specs2._

object TypeBasics extends org.specs2.mutable.Specification {
  // What are static types? Why are they useful?
  // f: R -> N

  // Types in Scala
  // 1.parametric polymorphism roughly, generic programming
  // 2.(local) type inference roughly, why you needn’t say val i: Int = 12: Int
  // 3.existential quantification roughly, defining something for some unnamed type
  // 4.views we’ll learn these next week; roughly, “castability” of values of one type to another
  "Parametric polymorphism" >> {
    val unParmetricPolymorphism = 2 :: 1 :: "bar" :: "foo" :: Nil
    unParmetricPolymorphism.head must_== 2

    // Polymorphism is achieved through specifying type variables.
    def drop1[A](l: List[A]) = l.tail

    val rest = drop1(List(1, 2, 3))
    rest must_== List(2, 3)
    // Scala has rank-1 polymorphism
    // def toList[A](a: A) = List(a)
    // def foo[A, B](f: A => List[A], b: B) = f(b)
    // def foo[A](f: A => List[A], i: Int) = f(i)
  }
  "Type inference" >> {
    // In Scala, for example, you cannot do the following:
    // { x => x }
    // Whereas in OCaml, you can:
    // fun x -> x;;
    def id[T](x: T) = x

    val x = id(233)
    x must_== 233

    val hi = id("hey")
    hi must_== "hey"

    val l = id(List(1, 2, 3, 4))
    l must_== List(1, 2, 3, 4)


    true must_== true
  }
}