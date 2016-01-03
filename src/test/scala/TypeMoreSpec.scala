package scala.school

import org.specs2._
import ch3._

object TypeMore extends org.specs2.mutable.Specification {
  // "Semicolons"
  // Variable Declarations
  // Ranges
  // Partial Functions
  // MatchError
  // pf1 orElse pf2 ...

  // Method Declaratins
  // Method default and named Arguments

  // Methods with multiple Argument Lists
  // A Taste of Futures

  // Nested Method Definitions and Recursion
  "Methods with Empty Argument List" >> {
    val l = List(1, 2, 3)
    l.size must_== 3
    l.length must_== 3
    "hello".length() must_== 5
    "hello".length must_== 5
  }
  "no dot better" >> {
    def isEven(i: Int) = (i % 2) == 0
    def id(i: Int) = i
    val r = List(1, 2, 3, 4) filter isEven map id

    r must_== List(2, 4)
  }
  "precedence rules" >> {
    val r = 'a' :: List('b', 'c', 'd')
    r must_== List('a', 'b', 'c', 'd')
  }
  "YieldingFor" >> {
    YieldingFor.filteredB must_== List("s t")
    YieldingFor.upcaseBs must_== List("D", "Y T", "DA", "S T", "G DA", "P W DO")
  }
  "Enumeration" >> {
    import ch3.Breed._
    val bs = Breed.values
    bs.size must_== 5
    val bt = Breed.values filter (_.toString.endsWith("t"))
    bt.size must_== 2

    def isT(b: Breed) = b.toString.endsWith("t")

    val bts = Breed.values filter isT
    bts.size must_== 2

    import ch3.WeekDay._
    def isWorkingDay(d: WeekDay) = !(d == Sat || d == Sun)
    val wd = WeekDay.values filter isWorkingDay
    wd.size must_== 5
  }
}