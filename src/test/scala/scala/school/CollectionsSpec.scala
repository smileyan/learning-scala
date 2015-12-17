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
      numbers.filter(isEven) must_== List(2, 4)
    }
    "zip" >> {
      // zip aggregates the contents of two lists into a single list of pairs.
      val azip = List(1, 2, 3).zip(List("a", "b", "c"))

      azip must beAnInstanceOf[List[(Int, String)]]
      azip(0) must_== (1, "a")
      azip(0)._1 must_== 1
    }
    "partition" >> {
      // partition splits a list based on where it falls with respect to a predicate function.
      val numbers = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
      val par_result = numbers.partition((i: Int) => i % 2 == 0)

      par_result must beAnInstanceOf[(List[Int], List[Int])]
      par_result._1(0) must_== 2
    }
    "find" >> {
      // find returns the first element of a collection that matches a predicate function.
      val numbers = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
      val f_result = numbers.find((i: Int) => i > 5)

      f_result must beAnInstanceOf[Option[Int]]
      f_result.get must_== 6

    }
    "drop & dropWhile" >> {
      // drop drops the first i elements
      val numbers = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
      val drop_result = numbers.drop(5)
      drop_result.size must_== 5

      // dropWhile removes the first elements that match a predicate function.
      // For example, if we dropWhile odd numbers from our list of numbers,
      // 1 gets dropped (but not 3 which is "shielded" by 2)
      val dropWhile_result = numbers.dropWhile(_ % 2 != 0)
      dropWhile_result.size must_== 9
      dropWhile_result(0) must_== 2
      dropWhile_result(2) must_== 4
    }
    "foldLeft" >> {
      val numbers = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
      val result = numbers.foldLeft(0)((m: Int, n: Int) => m + n)
      // 0 is the starting value, and m acts as an accumulator
      // numbers.foldLeft(0) { (m: Int, n: Int) => println("m: " + m + " n: " + n); m + n }
      result must_== 55

      val div_result = numbers.foldLeft(1.0)((m: Double, n: Int) => m / n )
      div_result must beCloseTo(2.7557319223985894E-7, 0.001)
    }
    "foldRight" >> {
      val numbers = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
      val result = numbers.foldRight(0)((m: Int, n: Int) => m + n)
      result must_== 55

      val div_result = numbers.foldRight(1.0)((m: Int, n: Double) => m / n )
      div_result must beCloseTo(0.24609375, 0.001)
    }
    "flatten" >> {
      // flatten collapses one level of nested structure
      val flatten_result = List(List(1, 2), List(3, 4)).flatten
      flatten_result must beAnInstanceOf[List[Int]]
      flatten_result.size must_== 4
    }
    "flatMap" >> {
      // flatMap is a frequently used combinator that combines mapping and flattening.
      // flatMap takes a function that works on the nested lists and then concatenates the results back together.
      val nestedNumbers = List(List(1, 2), List(3, 4))
      nestedNumbers must beAnInstanceOf[List[List[Int]]]

      val flatMapresult = nestedNumbers.flatMap(x => x.map(_ * 2))
      flatMapresult.size must_== 4
      flatMapresult.head must_== 2
      flatMapresult.tail must_== List(4, 6, 8)

      val map_then_flat = nestedNumbers.map(x => x.map(_ * 2)).flatten
      map_then_flat.size must_== 4
      map_then_flat.head must_== 2
      map_then_flat.tail must_== List(4, 6, 8)

      val flat_and_map = nestedNumbers.flatten.map(_ * 2)
      flat_and_map.size must_== 4
      flat_and_map.head must_== 2
      flat_and_map.tail must_== List(4, 6, 8)

      val chars = 'a' to 'z'
      val perms = chars flatMap { a =>
        chars flatMap { b =>
          if (a != b) Seq("%c%c".format(a, b))
          else Seq()
        }
      }

      val perms_for = for {
        a <- chars
        b <- chars
        if a != b
      } yield "%c%c".format(a, b)

      true must_== true
    }
    "Generalized functional combinators" >> {
      def ourMap(numbers: List[Int], fn: Int => Int): List[Int] = {
        numbers.foldRight(List[Int]()) { (x: Int, xs: List[Int]) =>
          fn(x) :: xs
        }
      }

      val numbers = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
      def timesTwo(n: Int):Int = {
        n * 2
      }
      val res0 = ourMap(numbers, timesTwo(_))
      res0(1) must_== 4
    }
    "Map?" >> {
      val extensions = Map("steve" -> 100, "bob" -> 101, "joe" -> 201)
      extensions must beAnInstanceOf[Map[String, Int]]

      val namePhoneLessThan200 = extensions.filter((namePhone: (String, Int)) => namePhone._2 < 200)
      namePhoneLessThan200.get("joe") must_== None
      namePhoneLessThan200.get("steve").get must_== 100

      val npPatterMatch = extensions.filter( { case (nane, extension) => extension < 200 } )
      npPatterMatch.get("joe").getOrElse(200) must_== 200
      npPatterMatch.get("steve").get must_== 100
    }
  }
}