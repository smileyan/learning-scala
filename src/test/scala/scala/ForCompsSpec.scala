package scala

import org.specs2._

object ForCompsSpec extends org.specs2.mutable.Specification {
  "for each" >> {
    val states = List("a", "l", "v", "w")
    for {
      s <- states
    } println(s)
    states foreach println

    for {
      s <- states
    } yield s.toUpperCase
    states map (_.toUpperCase)

    
    1 must_== 1
  }

}