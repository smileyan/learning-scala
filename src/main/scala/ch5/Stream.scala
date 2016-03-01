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

  def append[B>:A](s: Stream[B]): Stream[B] =
    foldRight(s)((h,t) => cons(h,t))

  def flatMap[B](f: A => Stream[B]): Stream[B] =
    foldRight(empty[B])((h,t) => f(h) append t)

  def find(p: A => Boolean): Option[A] =
    filter(p).headOption
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

  val ones : Stream[Int] = Stream.cons(1, ones)

  // def constant[A](a: A): Stream[A] = Stream.cons(a, constant(a))

  def constant[A](a: A): Stream[A] = {
    lazy val tail: Stream[A] = Cons(() => a, () => tail)
    tail
  }

  def from(n: Int): Stream[Int] = Stream.cons(n, from(n+1))

  val fibs: Stream[Int] = {
    def go(cur: Int, next: Int): Stream[Int] = {
      cons(cur, go(next, cur + next))
    }
    go(0, 1)
  }

  def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] =
    f(z) match {
      case Some((h, s)) => cons(h, unfold(s)(f))
      case None => empty
    }
}
