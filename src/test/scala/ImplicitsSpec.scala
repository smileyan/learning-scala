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

  }
}
