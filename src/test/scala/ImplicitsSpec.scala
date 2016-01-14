package scala.school

import org.specs2._
// import ch4._

object ImplicitsSpec extends org.specs2.mutable.Specification {
  "Implicit Arguments" >> {
    def calTax(amount: Float)(implicit rate: Float): Float = amount * rate

    object SimpleStateSalesTax {
      implicit val rate = 0.08F
    }

    case class ComplicatedSalesTaxData(
      baseRate: Float,
      isTaxHoliday: Boolean,
      storeId: Int
      )
    object ComplicatedSalesTax{
      private def extralTaxRateForStore(id: Int): Float = {
        0.0F
      }

      implicit def rate(implicit cstd: ComplicatedSalesTaxData): Float = {
        if (cstd.isTaxHoliday) 0.0F
        else cstd.baseRate + extralTaxRateForStore(cstd.storeId)
      }
    }

    {
      import SimpleStateSalesTax.rate
      calTax(50000) must_== 4000.0
    }

    {
      import ComplicatedSalesTax.rate
      implicit val myStore = ComplicatedSalesTaxData(0.06F, false, 1010)

      val amount = 100F
      calTax(amount) must_== 6.0
    }
    "Using implicitly" >> {
      import math.Ordering

      case class MyList[A](list: List[A]) {
        def sortBy1[B](f: A => B)(implicit ord: Ordering[B]): List[A] = {
          list.sortBy(f)(ord)
        }

        def sortBy2[B: Ordering](f: A => B) = {
          list.sortBy(f)(implicitly[Ordering[B]])
        }
      } 

      val l = MyList(List(3,2,1,4,5))

      l sortBy1(i => -i) must_== List(5,4,3,2,1)
      l sortBy2(i => -i) must_== List(5,4,3,2,1)
    }
  }
  "Scenarios for implicit Arguments" >> {
    "Execute context" >> {
      // transaction thread pool, database connection, user session
      // apply[T](body: => T)(implicit executor: ExecuteContext): Future
      // import scala.concurrent.ExecuteContext.Implicits.global
      1 must_== 1
    }
    "Capabilities" >> {
      // def creatMenu(implicit session: Session): Menu = {
      //   val defaultItems = List(helpItem, searchItem)
      //   val accountItems =
      //     if (session.loggedin()) List(viewAccountItem, editAccountItem)
      //     else List(loginItem)
      //   Menu(defaultItems ++ accountItems)
      // }
      1 must_== 1
    }
  }
  "Constraining Allowed Instances" >> {
    import ch4.scaladb.implicits._
    import ch4.javadb

    val row = javadb.JRow("one" -> 1, "t" ->2.2, "th" -> "t")

    val one: Int = row.get("one")

    one must_== 1
  }
  "Implicit Evidence" >> {
    // trait TraversableOnce[+A] {
    //   def toMap[T, U](implicit ev: <:<[A, (T, U)]): immutable.Map[T, U]
    // }
    val l2 = List("one" -> 1, "two" -> 2, "three" -> 3)
    l2.toMap must_== Map("one" -> 1, "two" -> 2, "three" -> 3)
  }
  "Working Around Erasure" >> {
    // object C {
    //   def m(seq: Seq[Int]): Unit = println(s"Seq[Int]: $seq")
    //   def m(seq: Seq[String]): Unit = println(s"Seq[String]: $seq")
    // }

    object M {
      implicit object IntMarker
      implicit object StringMarker

      def m(seq: Seq[Int])(implicit i: IntMarker.type): String = s"Seq[Int]: $seq"
      def m(seq: Seq[String])(implicit s: StringMarker.type): String = s"Seq[String]: $seq"
    }
    import M._
    "Seq[Int]: List(1, 2, 3)" must_== m(List(1,2,3))
    "Seq[String]: List(one, two, three)" must_== m(List("one", "two", "three"))
  }
}
