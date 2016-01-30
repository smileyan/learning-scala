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
}
