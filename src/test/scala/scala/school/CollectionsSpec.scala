package scala.school

import org.specs2._

object CollectionsSpec extends org.specs2.mutable.Specification {
  "Basic Data Structures " >> {
    "Lists" >> {
      val numbers = List(1, 2, 3, 4)

      numbers.head must_== 1
      numbers.length must_== 4
      numbers must beAnInstanceOf[List[Int]]
    }
    "Sets" >> {
      val s = Set(2,2,1)

      s.head must_== 2
      s.size must_== 2
      s must beAnInstanceOf[Set[Int]]
    }
    "Tuple" >> {
      val hostPort = ("https", "localhost", 80)

      hostPort._1 must_== "https"
      hostPort._2 must_== "localhost"
      hostPort._3 must_== 80

      val r = hostPort match {
        case ("https", "localhost", port) => 1
        case _ => 2
      }

      r must_== 1

      val (protocal, host, port) = hostPort
      protocal must_== "https"
      port must_== 80
    }
    "Maps" >> {
      val m = Map(1 -> 2)
      m.get(1).get must_== 2

      def timesTwo(n: Int):Int = {
        n * 2
      }

      val t = Map("timesTwo" -> { timesTwo(_) } )

      t.get("timesTwo").get(2) must_== 4
    }
    "Option" >> {
      // trait Option[T] {
      //   def isDefined: Boolean
      //   def get: T
      //   def getOrElse(t: T): T
      // }
      // Option itself is generic and has two subclasses : Some[T] or None

      val numbers = Map("one" -> 1, "two" -> 2)
      val two = numbers.get("two")

      two must beAnInstanceOf[Option[Int]]
      two must beAnInstanceOf[Some[Int]]

      val three = numbers.get("three")
      three must_== None
      three must beAnInstanceOf[Option[Int]]

      // We want to multiply the number by two, otherwise return 0.
      val result = if (three.isDefined) {
        three.get * 2
      } else {
        0
      }
      result must_== 0

      // getOrElse lets you easily define a default value
      val result_1 = two.getOrElse(0) * 2
      result_1 must_== 4

      // Pattern matching fits naturally with Option
      val result_2 = two match {
        case Some(n) => n * 2
        case None => 0
      }
      result_2 must_== 4

      // writing : safer
      var username: Option[String] = None
      username = Some("foobar")
      username.get must_== "foobar"
      // instead of
      // var username: String = null
      // username = "foobar"

      def operate(s: String):String = s
      var opt: Option[String] = None
      opt = Some("tell, don't ask")
      // if (opt.isDefined) println(opt.get)
      val result_3 = opt map { operate(_) }
      result_3.get must_== "tell, don't ask"
    }
  }
  "Functional Combinators" >> {
    "map" >> {
      val numbers = List(1, 2, 3, 4)

      // Evaluates a function over each element in the list, returning a list with the same number of elements
      val s = numbers.map( (i: Int) => i * 2 )
      s must_== List(2, 4, 6, 8)

      // or pass in a function(the scala complier automatically converts our method to a function)
      def timesTwo(i: Int): Int = i * 2
      val s1 = numbers.map( timesTwo )
      s1 must_== List(2, 4, 6, 8)
    }
    "foreach" >> {
      // foreach is like map but returns nothing. foreach is intended for side-effects only.
      val numbers = List(1, 2, 3, 4)
      var sum = 0

      numbers.foreach(sum += _)
      sum must_== 10
    }
    "filter" >> {
      // removes any elements where the function you pass in evaluates to false.
      // Functions that reture a Boolean are often called predicate functions.
      val numbers = List(1, 2, 3, 4)

      val s1 = numbers.filter((i: Int) => i % 2 == 0)
      s1 must_== List(2, 4)

      def isEven(i: Int):Boolean = i % 2 == 0
      numbers.filter(isEven _) must_== List(2, 4)
    }
  }
}