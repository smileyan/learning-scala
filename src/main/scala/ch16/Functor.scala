package ch16

/**
  * Created by huay on 15/02/2016.
  */
trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object SeqF extends Functor[Seq] {
  def map[A, B](seq: Seq[A])(f: A => B): Seq[B] = seq map f
}