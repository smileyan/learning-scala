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
  }
  "Variance" >> {
    "covariant" >> {
      class Covariant[+A]
      val cv: Covariant[AnyRef] = new Covariant[String]
      true must_== true
    }
    "contravariant" >> {
      class Contravariant[-A]
      val cv: Contravariant[String] = new Contravariant[AnyRef]

      // trait Function1 [-T1, +R] extends AnyRef

      // A function parameters are contravariant
      val getTweet: (Bird => String) = (a: Animal) => a.sound

      // A function’s return value type is covariant.
      val hatch: (() => Bird) = (() => new Chicken)

      true must_== true
    }
  }
  "Bounds" >> {
    // def cacophony[T](things: Seq[T]) = things map (_.sound)
    // value sound is not a member of type parameter T

    def biophony[T <: Animal](things: Seq[T]) = things.map(_.sound)
    val res5 = biophony(Seq(new Chicken, new Bird ))

    res5 must beAnInstanceOf[Seq[String]]

    val flock = List(new Bird, new Bird )
    flock must beAnInstanceOf[List[Bird]]

    // List[+T] is covariant; a list of Birds is a list of Animals.
    // List defines an operator ::(elem T) that returns a new List with elem prepended.
    // The new List has the same type as the original:
    val res53 = new Chicken :: flock
    res53 must beAnInstanceOf[List[Bird]]

    // List also defines ::[B >: T](x: B) which returns a List[B].
    // Notice the B >: T. That specifies type B as a superclass of T.
    // That lets us do the right thing when prepending an Animal to a List[Bird]:
    val res59 = new Animal :: flock
    res59 must beAnInstanceOf[List[Animal]]
  }
  "Quantification" >> {
    // def count[A](l: List[A]) = l.size
    def count(l: List[_]) = l.size
    // def count(l: List[T forSome { type T }]) = l.size
    val res1 = count(List(1,2))
    res1 must_== 2

    def hashCodes(l: Seq[_ <: AnyRef]) = l map (_.hashCode)
    // val res2 = hashCodes(List(1, 2, 3))
    // res2 must beAnInstanceOf[List[Int]]
    // [error]  found   : Int(2)
    // [error]  required: AnyRef
    // [error] Note: an implicit exists from scala.Int => java.lang.Integer, but
    // [error] methods inherited from Object are rendered ambiguous.  This is to avoid
    // [error] a blanket implicit which would convert any scala.Int to any AnyRef.
    // [error] You may wish to use a type ascription: `x: java.lang.Integer`.
    // [error]     val res2 = hashCodes(List(1, 2, 3))
    val res2 = hashCodes(List("one", "two", "three"))
    res2 must beAnInstanceOf[List[Int]]
    
  }
}