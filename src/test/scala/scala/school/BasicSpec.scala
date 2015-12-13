package scala.school

import org.specs2._

object BasicSpec extends org.specs2.mutable.Specification {
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
    "Functions" >> {
      def addOne(m: Int): Int = m + 1
      val three = addOne(2)
      three must_== 3

      def four() = 2 + 2
      four() must_== 4
      four must_==4
    }
    "Anonymous Functions" >> {
      val addTwo = (x: Int) => x + 2
      addTwo(2) must_== 4

      def timesTwo(x: Int): Int = {
        // println("Hello World")
        x * 2
      }

      timesTwo(4) must_== 8

      val timesThree = { i: Int =>
        // println("Hello World")
        i * 3
      }

      timesThree(3) must_==9
    }
    "Partial application" >> {
      def adder(x: Int, y: Int): Int = x + y

      val add2 = adder(_: Int, 3)
      add2(2) must_== 5
    }
    "Curried functions" >> {
      def multiply(x: Int)(y: Int): Int = x * y
      multiply(2)(3) must_== 6

      val timesFour = multiply(4)_
      timesFour(5) must_== 20

      def adder(x: Int, y: Int): Int = x + y
      val curriedAdd = (adder _).curried

      val addThree = curriedAdd(3)
      addThree(4) must_== 7
    }
    "Variable length arguments" >> {
      def capitalizeAll(args: String*) = {
        args.map { arg =>
          arg.capitalize
        }
      }

      val result = capitalizeAll("rarity", "applejack")
      result.toArray must_== Array("Rarity", "Applejack")
    }
    "Classes" >> {
      val calc = new Calculator
      calc.add(2, 4) must_== 6
      calc.band must_== "HP"
    }
  }
}
