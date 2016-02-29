package ch5

import Stream._
/**
  * Created by huay on 27/02/2016.
  */
sealed trait Stream[+A] {
  def headOption: Option[A] = this match {
    case Empty => None
    case Cons(h, t) => Some(h())
  }

  def headOption1: Option[A] =
    foldRight(None: Option[A])((h,_) => Some(h))

  def toList: List[A] = this match {
    case Empty => Nil
    case Cons(h, t) => h()::t().toList
  }

  def take(n: Int): Stream[A] = this match {
    case Empty => Empty
    case Cons(h, t) if (n == 1) => cons(h(),empty)
    case Cons(h, t) if (n > 1) => cons(h(),t().take(n-1))
  }

  def drop(n: Int): Stream[A] = this match {
    case Empty => Empty
    case Cons(h,t) if (n>0) => t().drop(n-1)
  }

  def exists(p: A => Boolean): Boolean = this match {
    case Cons(h,t) => p(h()) || t().exists(p)
    case _ => false
  }

  def forAll(p: A => Boolean): Boolean =
    foldRight(true)((a, b) => p(a) && b)

  def takeWhile(p: A => Boolean): Stream[A] =
    foldRight(empty[A])((a,b) =>
      if (p(a)) cons(a,b)
      else      empty)

  def exists1(p: A => Boolean): Boolean =
    foldRight(false)((a, b) => p(a) || b)

  def foldRight[B](z: => B)(f: (A, => B) => B): B =
    this match {
      case Cons(h,t) => f(h(), t().foldRight(z)(f))
      case _ => z
    }

  def map[B](f: A => B): Stream[B] =
    foldRight(empty: Stream[B])((h, t) => cons(f(h),t))

  def filter(f: A => Boolean): Stream[A] =
    foldRight(empty: Stream[A])((h,t) =>
      if (f(h)) cons(h,t)
      else      t)

  def append(s: Stream[A]): Stream[A] =
    foldRight(s)((h,t) => cons(h,t))
}
case object Empty extends Stream[Nothing]
case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

object Stream {
  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
    lazy val head = hd
    lazy val tail = tl

    Cons(() => head, () => tail)
  }

  def empty[A]: Stream[A] = Empty

  def apply[A](as: A*) : Stream[A] = {
    if (as.isEmpty)
      empty
    else
      cons(as.head, apply(as.tail: _*))
  }
}
