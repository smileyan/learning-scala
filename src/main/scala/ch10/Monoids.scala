package ch10

/**
  * Created by huay on 27/03/2016.
  */
trait Monoid[A] {

  // op(op(x,y),z) == op(x,op(y,z))
  def op(a1: A, a2: A): A

  // op(zero,x) == op(x,zero)
  def zero: A
}

object Monoid {
  val stringMonoid = new Monoid[String] {
    def op(a1: String, a2: String) = a1 + a2
    val zero = ""
  }

  def listMonoid[A] = new Monoid[List[A]] {
    def op(a1: List[A], a2: List[A]) = a1 ++ a2
    val zero = Nil
  }

  val intAddition = new Monoid[Int] {
    def op(a1:Int,a2:Int) = a1 + a2
    val zero = 0
  }

  val intMultiplicatin = new Monoid[Int] {
    def op(a1:Int,a2:Int) = a1 * a2
    val zero = 1
  }

  val booleanOr = new Monoid[Boolean] {
    def op(a1:Boolean, a2:Boolean) = a1 || a2
    val zero = false
  }

  val booleanAnd = new Monoid[Boolean] {
    def op(a1:Boolean,a2:Boolean) = a1 && a2
    val zero = true
  }

  def optionMonoid[A]: Monoid[Option[A]] = new Monoid[Option[A]] {
    // op(op(x,y),z) == op(x,op(y,z))
    override def op(a1: Option[A], a2: Option[A]): Option[A] = a1 orElse(a2)

    // op(zero,x) == op(x,zero)
    override def zero: Option[A] = None
  }

  def dual[A](m: Monoid[A]): Monoid[A] = new Monoid[A] {
    // op(op(x,y),z) == op(x,op(y,z))
    override def op(a1: A, a2: A): A = m.op(a2,a1)

    // op(zero,x) == op(x,zero)
    override def zero: A = m.zero
  }

  def firstOptionMonoid[A]: Monoid[Option[A]] = optionMonoid[A]
  def lastOptionMonoid[A]: Monoid[Option[A]] = dual(optionMonoid[A])

  def endoMonoid[A]: Monoid[A => A] = new Monoid[(A) => A] {
    // op(op(x,y),z) == op(x,op(y,z))
    override def op(a1: (A) => A, a2: (A) => A): (A) => A = a1 andThen(a2)

    // op(zero,x) == op(x,zero)
    override def zero: (A) => A = (a: A) => a
  }

  // def foldRight[B](z: B)(f: (A,B) => B): B
  // def foldLeft[B](z: B)(f: (B,A) => B): B
  val words = List("a","b","c")
  val s = words.foldRight(stringMonoid.zero)(stringMonoid.op)
  val t = words.foldLeft(stringMonoid.zero)(stringMonoid.op)

  def concatenate[A](as: List[A], m: Monoid[A]): A =
    as.foldLeft(m.zero)(m.op)

  def foldMap[A,B](as: List[A], m: Monoid[B])(f: A => B): B =
    //as.map(f).foldLeft(m.zero)(m.op)
    as.foldLeft(m.zero)((b,a) => m.op(b, f(a)))

}
