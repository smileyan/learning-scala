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

    1 must_== 1

  }
}
