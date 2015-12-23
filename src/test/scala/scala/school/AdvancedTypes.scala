package scala.school

import org.specs2._

object AdvancedTypes extends org.specs2.mutable.Specification {
  "View bounds ( type classes )" >> {
    // Implicit Arguments
    def calcTax(amount: Float)(implicit rate: Float): Float = amount * rate

    implicit val currentTaxRate = 0.08F

    calcTax(50000F) must_== 4000.0
    calcTax(50000F)(0.8F) must_== 40000.0

    val list = MyList(List(1,3,5,2,4))

    val r1 = list sortBy1 (i => -i)

    r1 must_== List(5, 4, 3, 2, 1)

    // Implicit functions allow automatic conversion.
    // More precisely, they allow on-demand function application when this can help satisfy type inference. e.g.:
    implicit def strToInt(x: String) = x.toInt
    val ott = "123"

    ott must_== "123"

    val y: Int = "123"
    y must_== 123

    math.max("123", 111) must_== 123

    implicit def douToInt(x: Double) = x.toInt

    // This says that A has to be “viewable” as Int. Let’s try it.
    class Container[A <% Int] { def addIt(x: A) = 123 + x }

    (new Container[String]).addIt("123") must_== 246
    (new Container[Int]).addIt(123) must_== 246
    (new Container[Double]).addIt(123.1) must_== 246
  }
  "Other type bounds" >> {
    // Scala’s math library defines an implicit Numeric[T] for the appropriate types T. 
    // Then in List’s definition uses it:
    // sum[B >: A](implicit num: Numeric[B]): B
    // You can List(1,2).sum()
    // You cannot List("whoop").sum
    
    // type-relation operators
    // A =:= B      A must be equal to B
    // A <:< B      A must be a subtype of B
    // A <%< B      A must be viewable as B
    class Container[A](value: A) { def addIt(implicit e: A =:= Int) = 123 + value }

    (new Container(123) ).addIt must_== 246

    // (new Container("123") ).addIt must_== 246

    "Generic programming with views" >> {
      // In the Scala standard library, views are primarily used to implement generic functions over collections.
      // For example, the "min" function (on Seq[]), uses this technique:
      // def min[B >: A](implicit cmp: Ordering[B]): A = {
      //   if(isEmpty)
      //     throw new UnsupportedOperationException("empty.min")

      //   reduceLeft((x, y) => if (cmp.lteq(x, y)) x else y )
      // }
      List(1, 2, 3, 4).min must_== 1

      List(1, 2, 3, 4).min(new Ordering[Int] { def compare(a: Int, b: Int) = b compare a } ) must_== 4

      // There are views that translates Ordered into Ordering in the standard library
      // trait LowPriorityOrderingImplicits {
      //   implicit def ordered[A <: Ordered[A]]: Ordering[A] = new Odering[A] {
      //       def compare(x: A, y: A) = x.compare(y)
      //   }
      // }
    }
    "Context bounds & implicitly[]" >> {
      true must_== true
    }
    "Higher-kinded types & ad-hoc polymorphism" >> {

      trait Container[M[_]] {
        def put[A](x: A): M[A]
        def get[A](m: M[A]): A
      }

      val container = new Container[List] {
        def put[A](x: A) = List(x)
        def get[A](m: List[A]) = m.head
      }

      container.put("hey") must anInstanceOf[List[String]]

      container.put(123) must anInstanceOf[List[Int]]

      implicit val listContainer = new Container[List] {
        def put[A] (x: A) = List(x)
        def get[A] (m: List[A]) = m.head
      }

      implicit val optionContainer = new Container[Some] {
        def put[A] (x: A) = Some(x)
        def get[A] (m: Some[A]) = m.get
      }

      def tupleize[M[_]: Container, A, B](fst: M[A], snd: M[B]) = {
        val c = implicitly[Container[M]]
        c.put(c.get(fst), c.get(snd))
      }

      tupleize(Some(1), Some(2))
      tupleize(List(1), List(2))

      true must_== true

    }
  }
}