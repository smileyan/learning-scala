package scala.school

import org.specs2._

object BasicContinuedSpec extends org.specs2.mutable.Specification {
  "basics continued" >> {
    "apply" >> {
      class Foo {}
      object FooMaker {
        def apply() = new Foo
      }

      val newFoo = FooMaker()
      newFoo must beAnInstanceOf[Foo]

      class Bar {
        def apply() = 0
      }

      val bar = new Bar
      bar() must_== 0
    }
    "Objects" >> {
      // Objects are used to hold single instances of a class
      // Often used for factories.
      object Timer {
        var count = 0

        def currentCount(): Long = {
          count += 1
          count
        }
      }

      Timer.currentCount() must_== 1

      class Bar(foo: String)

      object Bar {
        def apply(foo: String) = new Bar(foo)
      }

      val bar = Bar("bar")
      bar must beAnInstanceOf[Bar]
    }
    "Functions are Objects" >> {
      // A function is a set of traits.
      object addOne extends Function1[Int, Int] {
        def apply(m: Int): Int = m + 1
      }
      addOne(1) must_== 2

      class AddOne extends Function1[Int, Int] {
        def apply(m: Int) = m + 1
      }

      val pulsOne = new AddOne()
      pulsOne(2) must_== 3

      class AddTwo extends (Int => Int) {
        def apply(m: Int) = m + 2
      }

      val pulsTow = new AddTwo()
      pulsTow(2) must_== 4
    }
    "Packages" >> {
      com.twitter.example.colorHolder.BLUE must_== "Blue"
    }
    "Pattern Matching" >> {
      val times = 1

      val r = times match {
        case 1 => "one"
        case 2 => "two"
        case _ => "some other number"
      }

      r must_== "one"

      val n = 3
      val m = n match {
        case i if i == 1 => "one"
        case i if i == 2 => "two"
        case _ => "some other number" 
      }

      m must_== "some other number"

    }
  }
}