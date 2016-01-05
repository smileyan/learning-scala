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
}
