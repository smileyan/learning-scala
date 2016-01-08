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
  "unapplySeq Method" >> {
    val nonEmptyList = List(1,2,3,4,5)
    val emptyList = Nil
    val nonEmptyMap = Map("one" -> 1, "two" -> 2,"three" -> 3)

    def windows[T](seq: Seq[T]): String = seq match {
      case Seq(h1, h2, _*) =>
        s"($h1,, $h2), " + windows(seq.tail)
      case Seq(h, _*) =>
        s"($h, _), " + windows(seq.tail)
      case Nil => "Nil"
    }

    for( seq <- Seq(nonEmptyList, emptyList, nonEmptyMap.toSeq)) {
      println(windows(seq))
    }

    1 must_== 1
  }
  "Matching on Variable Argument Lists" >> {

    object Op extends Enumeration {
      type Op = Value

      val EQ = Value("=")
      val NE = Value("!=")
      val LT = Value("<")
      val GT = Value(">")

    }

    import Op._

    case class WhereOp[T](columnName: String, op: Op, value: T)
    case class WhereIn[T](columnName: String, val1: T, vals: T*)

    val wheres = Seq(
      WhereIn("state", "IL", "CA", "VA"),
      WhereOp("state", EQ, "IL")
    )

    for( where <- wheres) {
      where match {
        case WhereIn(col, val1, vals @ _*) => 
          val valStr = (val1 +: vals).mkString(", ")
          println(s"WHERE $col IN ($valStr)")
        case WhereOp(col, op, value) => println(s"WHERE $col $op $value")
        case _ => println(s"ERROR: Unknown expression: $where")
      }
      
    }
    1 must_== 1
  }
  "Matching on regular Expressions" >> {

    val BookExtractorRE = """Book: title=([^,]+),\s+author=(.+)""".r
    val MagazineExtractorRE = """Magazine: title=([^,]+),\s+issue=(.+)""".r

    val catalog = Seq(
      "Book: title=scala, author=d",
      "Magazine: title=t n y, issue=2014",
      "unexpected"
      )

    for( item <- catalog) {
      item match {
        case BookExtractorRE(title, author) => println(s"$title $author")
        case MagazineExtractorRE(title, author) => println(s"$title $author")
        case entry => println(s"$entry")
      }
      
    }

    1 must_== 1
  }
  "More on Binding Variables in case Clauses" >> {
    case class Address(street: String, city: String, country: String)
    case class Person(name: String, age: Int, address: Address)

    val alice = Person("Alice", 25, Address("1 s", "c", "USA"))
    val bob = Person("Bob", 29, Address("2 j", "m", "USA"))
    val charlie = Person("Charlie", 32, Address("3 p", "b", "USA"))

    for( person <- Seq(alice, bob, charlie)) {
      person match {
        case p @ Person("Alice", 25, address) => println("Hi Alice!")
        case p @ Person("Bob", 29, a @ Address("2 j", "m", "USA")) => println("Hi Bob!")
        case p @ Person(name, age, _) => println(s"$age $name")
      }
    }


    1 must_== 1
  }
  "More on Type Matching" >> {

    def doSeqMatch[T](seq: Seq[T]): String = seq match {
      case Nil => "Nil"
      case head +: _ => head match {
        case _ : Double => "double"
        case _ : String => "String"
        case _ => "unma"
      }
      
    }

    for( x <- Seq(List(5.5, 6.6, 7.7), List("a","b"))) yield {
      x match {
        case seq: Seq[_] => (s"seq ${doSeqMatch(seq)}", seq)
        case _ => ("Unknown", x)
      }
      
    }

    1 must_== 1
  }
  "Sealed Hierarchies and Exhaustive Matches" >> {
    sealed abstract class HttpMethod() {
      def body: String
      def bodyLength = body.length
    }

    case class Connect(body: String) extends HttpMethod
    case class Delete (body: String) extends HttpMethod
    case class Get    (body: String) extends HttpMethod
    case class Head   (body: String) extends HttpMethod
    case class Options(body: String) extends HttpMethod
    case class Post   (body: String) extends HttpMethod
    case class Put    (body: String) extends HttpMethod
    case class Trace  (body: String) extends HttpMethod

    def handle (method: HttpMethod) = method match {
      case Connect (body) => s"Connect: (length: ${method.bodyLength}) $body"
      case Delete (body) => s"Connect: (length: ${method.bodyLength}) $body"
      case Get (body) => s"Connect: (length: ${method.bodyLength}) $body"
      case Head (body) => s"Connect: (length: ${method.bodyLength}) $body"
      case Options (body) => s"Connect: (length: ${method.bodyLength}) $body"
      case Post (body) => s"Connect: (length: ${method.bodyLength}) $body"
      case Put (body) => s"Connect: (length: ${method.bodyLength}) $body"
      case Trace (body) => s"Connect: (length: ${method.bodyLength}) $body"
    }
    
    val methods = Seq(
      Connect("Connect"),
      Delete("Delete"),
      Get("get")
      )
    methods foreach (method => println(handle(method)))

    1 must_== 1
  }
  "Other Uses of Pattern Matching" >> {
    val h +: t = List(1,2,3)

    h must_== 1

    val h1 +: h2 +: t1 = Vector(1,2,3)
    h1 must_== 1
    h2 must_== 2

    def sum_count(ints: Seq[Int]) = (ints.sum, ints.size)
    val (s, c) = sum_count(List(1,2,3,4,5))
    s must_== 15
    c must_== 5

    1 must_== 1
  }
}
