package scala

import org.specs2._

/**
  * Created by huay on 30/01/2016.
  */
object ForComprehensionSpec extends org.specs2.mutable.Specification {
  "for-map" >> {
    val states = List("a", "b", "c", "d")
    val us = for {
      s <- states
    } yield s.toUpperCase

    us.head must_== "A"

    val sm = states map (_.toUpperCase)

    sm.head must_== "A"
  }
  "for flatmap" >> {
    val states = List("Ala", "Al", "Vir", "Wy")

    val us = for {
      s <- states
      c <- s.toSeq
    } yield s"$c-${c.toUpper}"
    us.head must_== "A-A"

    val sfm = states flatMap (_.toSeq map (c => s"$c-${c.toUpper}"))
    sfm.head must_== "A-A"
  }
  "for guard" >> {
    val states = List("Ala", "Al", "Vir", "Wy")

    val us = for {
      s <- states
      c <- s.toSeq
      if c.isLower
    } yield s"$c-${c.toUpper}"
    us.head must_== "l-L"

    val sfm = states flatMap (_.toSeq withFilter (_.isLower) map (c => s"$c-${c.toUpper}"))
    sfm.head must_== "l-L"
  }
  "translating for comprehension" >> {
    val z @ (x, y) = (1, 2)
    z must_== (1, 2)
    x must_== 1
    y must_== 2
  }
  "for options seq" >> {
    val results: Seq[Option[Int]] = Vector(Some(10), None, Some(20))

    // Translation step #1
    val results2b = for {
      Some(i) <- results withFilter {
        case Some(i) => true
        case None => false
      }
    } yield (2 * i)

    val result2 = for {
      Some(i) <- results
    } yield (2 * i)

    val results2c = results withFilter {
      case Some(i) => true
      case None    => false
    } map {
      case Some(i) => (2 * i)
    }

    result2 must_== Vector(20, 40)
  }
  "for options good" >> {
    def positive(i: Int): Option[Int] =
      if (i > 0) Some(i) else None

    val result = for {
      i1 <- positive(5)
      i2 <- positive(10 * i1)
      i3 <- positive(25 * i2)
      i4 <- positive(2  * i3)
    } yield (i1 + i2 + i3 + i4)

    result.get must_== 3805

    val result1 = for {
      i1 <- positive(5)
      i2 <- positive(-10 * i1)
      i3 <- positive(25 * i2)
      i4 <- positive(2  * i3)
    } yield (i1 + i2 + i3 + i4)

    result1 must_== None

  }
  "for-eighers-good" >> {
    def positive(i: Int): Either[String, Int] =
      if (i > 0) Right(i) else Left(s"nonpositive number $i")

    val result = for {
      i1 <- positive(5).right
      i2 <- positive(10 * i1).right
      i3 <- positive(25 * i2).right
      i4 <- positive(2 * i3).right
    } yield (i1 + i2 + i3 + i4)
    result.right.get must_== 3805
  }
  "ref transparency" >> {
    def addInts2(s1: String, s2: String): Either[NumberFormatException, Int]=
      try {
        Right(s1.toInt + s2.toInt)
      } catch {
        case nfe: NumberFormatException => Left(nfe)
      }

    addInts2("1", "2").right.get must_== 3

  }
}
