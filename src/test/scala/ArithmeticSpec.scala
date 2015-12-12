import org.specs2._

object ArithmeticSpec extends org.specs2.mutable.Specification {
  "basics" >> {
    "Expressions" >> {
      1 + 1 must_== 2
    }
    "Values" >> {
      val three = 1 + 1 + 1
      three must_== 3
    }
    "Variables" >> {
      var name = "steve"
      name must_== "steve"
      name = "marius"
      name must_== "marius"
    }
  }
}
