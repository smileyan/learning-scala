package scala.school

import org.specs2._

object AdvancedTypes extends org.specs2.mutable.Specification {
  "View bounds ( type classes )" >> {
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
      true must_== true
    }
  }
}