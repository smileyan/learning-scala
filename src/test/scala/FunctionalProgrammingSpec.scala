package scala

import ch7.{PhoneNumber, USPhoneNumber}
import org.specs2._
// import ch4._

object FunctionalProgrammingSpec extends org.specs2.mutable.Specification {
  "Anonymous Functions, Lambdas, and Clsures" >> {
    val hofs = (1 to 10) filter (_ % 2 == 0) map (_ * 2) reduce (_ * _)
    hofs must_== 122880

    var factor = 2 // free variable
    val multiplier = (i: Int) => i * factor
    val r1 = (1 to 10) filter (_ % 2 == 0) map multiplier reduce (_ * _)
    r1 must_== 122880

    factor = 3
    val r2 = (1 to 10) filter (_ % 2 == 0) map multiplier reduce (_ * _)
    r2 must_== 933120

    def m1(multiplier: Int => Int) = {
      (1 to 10) filter (_ % 2 == 0) map multiplier reduce (_ * _)
    }

    def m2: Int => Int = {
      val factor = 2
      val multiplier = (i: Int) => i * factor
      multiplier
    }

    m1(m2) must_== 122880

    def factorial(i: BigInt): BigInt = {
      if (i == 1) i
      else i * factorial(i - 1)
    }

    factorial(3) must_== 6
  }
  "Tail Calls and Tail-Call Optimization" >> {
    import scala.annotation.tailrec

    def factorial(i: BigInt): BigInt = {
      @tailrec
      def fact(i: BigInt, accumulator: BigInt): BigInt =
        if(i == 1){
          accumulator
        } else {
          fact(i - 1, i * accumulator)
        }

      fact(i, 1)
    }
    factorial(3) must_== 6
  }
  // "Trampoline for Tail Calls" >> {
  //   import scala.util.control.TailCalls._

  //   def isEven(xs: List[Int]): TailRec[Boolean] =
  //     if (xs.isEmpty) done(true) else tailcall(isOdd(xs.tail))

  //   def isOdd(xs: List[Int]): TailRec[Boolean] =
  //     if (xs.isEmpty) done(false) else tailcall(isEven(xs.tail))

  //   isEven((1 to 1).toList).result must_== false

  // }
  "Partially Applied Functions Versus Partial Functions" >> {
    def cat1(s1: String)(s2: String) = s1 + s2
    cat1("Hello, ")("world!") must_== "Hello, world!"

    val hello = cat1("Hello, ")_ // <function1>
    hello("world!") must_== "Hello, world!"

  }
  "Curring and Other Transformations on Functions" >> {
    def cat2(s1: String) = (s2: String) => s1 + s2
    val cat2Hello = cat2("Hello, ")
    cat2Hello("world!") must_== "Hello, world!"

    def cat3(s1: String, s2: String) = s1 + s2
    cat3("hello", "w") must_== "hellow"

    val cat3c = (cat3 _).curried
    cat3c("h")("w") must_== "hw"


    val f1: String => String => String = (s1: String) => (s2: String) => s1 + s2
    val f2: String => (String => String) = (s1: String) => (s2: String) => s1 + s2

    f1("h")("w") must_== f2("h")("w")

    val cat3uc = Function.uncurried(cat3c)
    cat3uc("h", "w") must_== "hw"

    val ff1 = Function.uncurried(f1)
    ff1("h", "w") must_== "hw"

    def multiplier(i: Int)(factor: Int): Int = i * factor
    val byFive = multiplier(5) _
    val byTen = multiplier(10) _

    byFive(2) must_== 10
    byTen(1) must_== 10

    def mult(d1: Double, d2: Double, d3: Double) = d1 * d2 * d3
    val d3 = (2.2, 3.3, 4.4)

    mult(d3._1, d3._2, d3._3) must_== 31.944000000000003

    val multTuple = Function.tupled(mult _)
    multTuple(d3) must_== 31.944000000000003

    val finicky: PartialFunction[String, String] = {
      case "finiki" => "h"
    }

    finicky("finiki") must_== "h"

    val finickyL = finicky.lift

    finickyL("finiki").getOrElse("w") must_==("h")

  }
  "Functional Data Structure" >> {
    val l1 = List("s", "p")
    l1.head must_== "s"

    val l2 = "h" :: "w" :: l1
    l2.head must_== "h"

    val seq1 = Seq("s", "p")
    val seq2 = seq1 :+ "r"
    seq2.head must_== "s"

    val v1 = Vector("p", "s")
    val v2 = "pe" +: "sh" +: "re" +: v1
    v2.head must_== "pe"
    v2(1) must_== "sh"
  }
  "Traversal" >> {
    List(1,2,3) foreach { i => println(i) }
    Map("a" -> "A", "b" -> "B") foreach { case (k, v) => println("k = " + k + ", v = " + v) }
    val l = List("now", "is", "", "the", "time")
    val fm = l flatMap(s => s.toList)

    val list = List(1,2,3,4,5,6)
    list reduce (_+_) must_== 21
    list.fold(10) (_*_) must_== 7200
    (list fold 10) (_*_) must_== 7200
    val fr = (List(1,2) foldRight List.empty[String]) {
      (x, list) => ("[" + x + "]") :: list
    }
    fr must_== List("[1]", "[2]")

    val sl = List(1,2,3,4,5)
    (sl scan 10)(_+_) must_== List(10,11,13,16,20,25)

    val fncLeft  = (accum: Int, x: Int) => accum - x
    val fncRight = (x: Int, accum: Int) => accum - x

    val list1 = List(1,2,3,4,5)

    list1 reduceLeft  fncLeft  must_== -13
    list1 reduceRight fncRight must_== -5

    import scala.math.BigInt

    lazy val fibs: Stream[BigInt] = BigInt(0) #:: BigInt(1) #:: fibs.zip(fibs.tail).map( n => n._1 + n._2)

    fibs take 10 reduce (_ + _) must_== 88
  }
  "type erasure" >> {
    object C {
      def m(seq: Seq[Int]): Unit = print(s"Seq[Int]: $seq")
//      def m(seq: Seq[String]): Unit = print(s"Seq[String: $seq")
    }

    1 must_== 1
  }
  "value class" >> {
    val benjamin = new ch7.Dollar(100)

    benjamin.toString must_== "$100.00"

    val number = new USPhoneNumber("987654-3210")

    number.toString must_== "(987) 654-3210"

    val r = new PhoneNumber("9870654321")

    r.toString must_== "(987) 065-4321"

  }
}