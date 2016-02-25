package ch4

/**
  * Created by huay on 25/02/2016.
  */
sealed trait Either[+E, +A] {
  def map[B] (f: A => B): Either[E, B] = this match {
    case Right(a) => Right(f(a))
    case Left(e) => Left(e)
  }

  def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B] = this match {
    case Right(a) => f(a)
    case Left(e) => Left(e)
  }

  def orElse[EE >: E, B >: A](b: => Either[EE, B]): Either[EE, B] = this match {
    case Right(a) => Right(a)
    case Left(e) => b
  }

  def map2[EE >: E, B, C](b: Either[EE, B])(f: (A, B) => C): Either[EE, C] =
    // for {
    //  a <- this
    //  b1 <- b
    // } yield f(a,b1)
    this flatMap(a =>(b map (bb => f(a,bb))))

}

case class Left[+E](value: E) extends Either[E, Nothing]
case class Right[+A](value: A) extends Either[Nothing, A]

object Either {
  def mean(xs: IndexedSeq[Double]): Either[String, Double] =
    if (xs.isEmpty)
      Left("mean of empty List!")
    else
      Right(xs.sum / xs.length)

  def safeDiv(x: Int, y: Int): Either[Exception, Int] =
    try Right(x / y)
    catch { case e: Exception => Left(e) }

  def Try[A](a: => A): Either[Exception, A] =
    try Right(a)
    catch { case e: Exception => Left(e) }

  def insuranceRateQuote(age: Int, tickets: Int): Double = ???

  def parseInsuranceRateQuote(age: String,
                              numberOfSpeedingTickets: String): Either[Exception, Double] =
    for {
      a <- Try { age.toInt }
      tickets <- Try { numberOfSpeedingTickets.toInt }
    } yield insuranceRateQuote(a,tickets)
}