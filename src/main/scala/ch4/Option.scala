package ch4

/**
  * Created by huay on 23/02/2016.
  */
sealed trait Option[+A] {
  def map[B](f: A => B): Option[B] = this match {
    case None => None
    case Some(a) => Some(f(a))
  }

  def flatMap[B](f: A => Option[B]): Option[B] =
    map(f) getOrElse None

  def getOrElse[B >: A](default: => B): B = this match {
    case None => default
    case Some(a) => a
  }

  def orElse[B >: A](ob: => Option[B]): Option[B] =
    this map (Some(_)) getOrElse(ob)

  def filter(f: A => Boolean): Option[A] = this match {
    case Some(a) if (f(a)) => this
    case None => None
  }

}
case class Some[+A](get: A) extends Option[A]

case object None extends Option[Nothing]
