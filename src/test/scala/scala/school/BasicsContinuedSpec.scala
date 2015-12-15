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
    "Matching on type" >> {
      def bigger(o: Any): Any = {
        o match {
          case i: Int if i < 0 => i - 1
          case i: Int => i + 1
          case d: Double if d < 0.0 => d - 0.1
          case d: Double => d + 0.1
          case text: String => text + "s"
        }
      }

      val mi = bigger(-1)
      mi must_== -2

      val i = bigger(1)
      i must_== 2
      
      val md = bigger(-1.1)
      md.asInstanceOf[Double] must be ~(-1.2 +/- 0.00001)

      val d = bigger(1.1)
      d.asInstanceOf[Double] must be ~(1.2 +/- 0.00001)

      val s = bigger("s")
      s must_== "ss"
    }
    "Matching on class members" >> {
      // def calcType(calc: Calculator) = calc match {
      //   case _ if calc.brand == "ph" && calc.model == "20B" => "financial"
      //   case _ if calc.brand == "ph" && calc.model == "48G" => "scientific"
      //   case _ if calc.brand == "ph" && calc.model == "30B" => "business"
      //   case _ => "unknown"
      // }
      true must_== true
    }
    "Case Classes" >> {
      val hp20b = MyCalculator("hp", "20b")
      val hp20B = MyCalculator("hp", "20b")

      hp20b must_== hp20B
    }
    "Case Classes with pattern matching" >> {

      def calcType(calc: MyCalculator) = calc match {
        case MyCalculator("hp", "20B") => "financial"
        case MyCalculator("hp", "48G") => "scientific"
        case MyCalculator("hp", "30B") => "business"
        // case MyCalculator(ourBrand, ourModel) => "Calculator: %s %s is of unknown type".format(ourBrand, ourModel)
        // case MyCalculator(_, _) => "Calculator of unknown type"
        case _ => "Calculator of unknown type"
      }

      val hp20b = calcType(MyCalculator("hp", "20B"))
      hp20b must_== "financial"

      // val hp120b = calcType(MyCalculator("hp1", "20B"))
      // hp120b must_== "Calculator: hp1 20B is of unknown type"

      // val hp120b = calcType(MyCalculator("hp1", "20B"))
      // hp120b must_== "Calculator of unknown type"

      val hp120b = calcType(MyCalculator("hp1", "20B"))
      hp120b must_== "Calculator of unknown type"
    }
    "Exceptions" >> {
      val remoteCalculatorService = MyCalculator("hp", "20b")
      remoteCalculatorService.add(1,1) must throwA[java.lang.IllegalArgumentException]

      val result: Int = try {
        remoteCalculatorService.add(1, 2)
      } catch {
        case e: IllegalArgumentException => {
          // log.error(e, "the remote calculator service is unavailable. should have kept your trusty HP.")
          0
        }
      } finally {
        remoteCalculatorService.close()
      }

      result must_== 0

    }
  }
}