package scala.school

import org.specs2._
// import ch4._

object PatternMatchingSpec extends org.specs2.mutable.Specification {
  "A simple match" >> {
    val bools = Seq(true, false)

    for( bool <- bools) {
      bool match {
        case true => "Got head"
        case false => "Got tails"
      }
      
    }
    1 must_== 1
  }
  "Values, Variables, and Types in Matches" >> {
    for {
      x <- Seq(1, 2, 2,7, "one", "two", 'four)
    } {
      val str = x match {
        case 1   => "int 1"
        case _: Int => "other int: " + x
        case d: Double => "a double:" + d
        case "one" => "string one"
        case s: String => "other string" + s
        case unexpected => "unexpected value:" + unexpected
      }
      println(str)
    }

    def checkY(y: Int) = {
      for {
        x <- Seq(99, 100, 101)
      } {
        val str = x match {
          case `y` => "found y!"
          case i: Int => "int: " + i
        }
        println(str)
      }      
    }
    checkY(100)

    for {
      x <- Seq(1, 2, 2,7, "one", "two", 'four)
    } {
      val str = x match {
        case 1   => "int 1"
        case _: Int | _: Double => "other int: " + x
        case "one" => "string one"
        case s: String => "other string" + s
        case unexpected => "unexpected value:" + unexpected
      }
      println(str)
    }
    1 must_== 1
  }
  "Matching on Sequences" >> {
    val nonEmptySeq = Seq(1,2,3,4,5)
    val emptySeq    = Seq.empty[Int]
    val nonEmptyList = List(1,2,3,4,5)
    val emptyList    = Nil
    val nonEmptyVector = Vector(1,2,3,4,5)
    val emptyVector   = Vector.empty[Int]
    val nonEmptyMap = Map("one" -> 1, "two" -> 2,"three" -> 3)
    val emptyMap    = Map.empty[String,Int]

    def seqToString[T](seq: Seq[T]): String = seq match {
      case head +: tail => s"$head +: " + seqToString(tail)
      case Nil => "Nil"
    }

    def seqToString2[T](seq: Seq[T]): String = seq match {
      case head +: tail => s"($head +: ${seqToString2(tail)})"
      case Nil => "Nil"
    }

    for( seq <- Seq(
      nonEmptySeq, emptySeq, nonEmptyList, emptyList,
      nonEmptyVector, emptyVector, nonEmptyMap.toSeq, emptyMap.toSeq)) {
      println(seqToString(seq))
    }

    for( seq <- Seq(
      nonEmptySeq, emptySeq, nonEmptyList, emptyList,
      nonEmptyVector, emptyVector, nonEmptyMap.toSeq, emptyMap.toSeq)) {
      println(seqToString2(seq))
    }

    1 must_== 1
  }
  "Matching on Tuples" >> {

    val langs = Seq(
      ("Scala", "m", "o"),
      ("Clojure", "r", "h"),
      ("Lisp", "j", "m"))

    for (tuple <- langs) {
      tuple match {
        case ("Scala", _, _) => println("Found Scala")
        case (lang, first, last) =>
          println(s"Found other lan: $lang ($first, $last)")
      }
    }

    1 must_== 1
  }
  "Guards in case Clauses" >> {
    for( i <- Seq(1,2,3,4)) {
      i match {
        case _ if i%2 == 0 => println(s"even: $i")
        case _             => println(s"odd: $i")
      }
    }

    1 must_== 1
  }
  "Matching on case Classes" >> {
    case class Address(street: String, city: String, country: String)
    case class Person(name: String, age: Int, address: Address)

    val alice = Person("Alice", 25, Address("1 s", "c", "USA"))
    val bob = Person("Bob", 29, Address("2 j", "m", "USA"))
    val charlie = Person("Charlie", 32, Address("3 p", "b", "USA"))

    for( person <- Seq(alice, bob, charlie)) {
      person match {
        case Person("Alice", 25, Address(_, "c", _)) => println("Hi Alice!")
        case Person("Bob", 29, Address("2 j", "m", "USA")) => println("Hi Bob!")
        case Person(name, age, _) => println(s"$age $name")
      }
    }

    val itemCosts = Seq(("pen", 0.52), ("paper", 1.35), ("note", 2.43))
    val itemCostsIndices = itemCosts.zipWithIndex
    for( itemCostIndex <- itemCostsIndices) {
      itemCostIndex match {
        case ((item, cost), index) => println(s"$index: $item $cost")
      }
    }

    def processSeq2[T](l: Seq[T]): Unit = l match {
      case +:(head, tail) => 
        print("%s +: ", head)
        processSeq2(tail)
      case Nil => print("Nil")
    }

    case class With[A, B](a: A, b: B)

    val with1: With[String, Int] = With("foo", 1)
    val with2: String With Int = With("bar", 2)

    Seq(with1, with2) foreach { w =>
      w match {
        case s With i => println(s"$s with $i")
        case _ => println(s"un $w")
      }
      

    }

    val nonEmptyList = List(1,2,3,4,5)
    val nonEmptyVector = Vector(1,2,3,4,5)
    val nonEmptyMap = Map("one" -> 1, "two" -> 2,"three" -> 3)

    def reverseSeqToString[T](l: Seq[T]): String = l match {
      case prefix :+ end => reverseSeqToString(prefix) + s" :+ $end"
      case Nil => "Nil"
    }

    for( seq <- Seq(nonEmptyList, nonEmptyVector, nonEmptyMap.toSeq)) {
      println(reverseSeqToString(seq))
    }
    1 must_== 1
  }
}
