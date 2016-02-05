

import ch10.{Address, Person}
/**
  * Created by huay on 5/02/2016.
  */
object BasicOop extends org.specs2.mutable.Specification {
  "Address 10" >> {
    val a1 = new Address("1 sca", "any", "ca", "9")
    a1.city must_== "any"
    a1.zip must_== "9"
  }
}
