package ch7

import java.util.concurrent.{CountDownLatch, ExecutorService}
import java.util.concurrent.atomic.AtomicReference

/**
  * Created by huay on 12/03/2016.
  */
object Nonblocking {
  private[ch7] sealed trait Future[A] {
    def apply(k: A => Unit): Unit
  }

  type Par[+A] = ExecutorService => Future[A]

  object Par {
    def run[A](es: ExecutorService)(p: Par[A]): A = {
      val ref = new AtomicReference[A]()
      val latch = new CountDownLatch(1)
      p(es) { a => ref.set(a); latch.countDown() }
      ref.get()
    }

    def unit[A](a: A): Par[A] = {
      es => new Future[A] {
        def apply(cb: A => Unit): Unit = cb(a)
      }
    }
  }
}
