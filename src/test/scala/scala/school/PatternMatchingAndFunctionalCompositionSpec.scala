package scala.school

import org.specs2._

object PatternMatchingAndFunctionalCompositionSpec extends org.specs2.mutable.Specification {
  "Function Composition" >> {
    "compose" >> {
      val p = new PatternMatchingAndFunctionalComposition
      val fComposeG = p.f _ compose p.g _
      fComposeG("yay") must_== "f(g(yay))"
    }
    "andThen" >> {
      val p = new PatternMatchingAndFunctionalComposition
      val fAndThenG = p.f _ andThen p.g _
      fAndThenG("yay") must_== "g(f(yay))"

    }
  }
  "Understanding PartialFunction" >> {
    "range and domain" >> {
      val one: PartialFunction[Int, String] = { case 1 => "one" }
      one must beAnInstanceOf[PartialFunction[Int, String]]
      one.isDefinedAt(1) must_== true
      one.isDefinedAt(2) must_== false
    }
    "composition with orElse" >> {
      val one: PartialFunction[Int, String] = { case 1 => "one" }
      val two: PartialFunction[Int, String] = { case 2 => "two" }
      val three: PartialFunction[Int, String] = { case 3 => "three" }
      val wildcard: PartialFunction[Int, String] = { case _ => "something else" }
      val partial = one orElse two orElse three orElse wildcard

      partial(5) must_== "something else"
      partial(2) must_== "two"
    }
    "The mystery of case." >> {
      case class PhoneExt(name: String, ext: Int)
      val extensions = List(PhoneExt("steve", 100), PhoneExt("robey", 200))
      extensions.filter { case PhoneExt(name, extension) => extension < 200 }.size must_== 1
    }
  }
}