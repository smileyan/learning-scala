package ch7

import java.util.concurrent.{Callable, TimeUnit, Future, ExecutorService}

/**
  * Created by huay on 8/03/2016.
  */

object Par {

  type Par[A] = ExecutorService => Future[A]

  // Creates a computation that immediately results in the value a.
  def unit[A](a: A): Par[A] = (es: ExecutorService) => UnitFuture(a)

  private case class UnitFuture[A](get: A) extends Future[A] {
    def isDone = true
    def get(timeout: Long, units: TimeUnit) = get
    def isCancelled = false
    def cancel(evenIfRunning: Boolean): Boolean = false
  }

  def get[A](a: Par[A]): A = ???

  // Combines the results of two parallel computations with a
  // binary function.
  // It simply passes the ExecutorService on to both Par values,
  // waits for the results of the futures af and bf, applies f to
  // them, and wraps them in a UnitFuture.
  def map2[A,B,C](a: Par[A], b: Par[B])(f: (A,B) => C): Par[C] =
    (es: ExecutorService) => {
      val af = a(es)
      val bf = b(es)
      UnitFuture(f(af.get, bf.get))
    }

  // Marks a computation for concurrent evaluation by run.
  // The evaluation won`t actually occur until forced by run.
  def fork[A](a: => Par[A]): Par[A] =
    (es: ExecutorService) => es.submit(new Callable[A] {
      override def call(): A = a(es).get
    })

  // Wraps the expression a for concurrent evaluation by run.
  // wraps its unevaluated argument in a Par and marks it for
  // concurrent evaluation.
  def lazyUnit[A](a: => A): Par[A] = fork(unit(a))

  // Fully evaluates a given Par, spawning parallel computations
  // as requested by fork and extracting the resulting value.
  // run extracts a value from a Par by actually performing the computation.
  def run[A](s: ExecutorService)(a: Par[A]): Future[A] = a(s)

  def asyncF[A,B](f: A => B): A => Par[B] =
    a => lazyUnit(f(a))

  def sortPar(parList: Par[List[Int]]): Par[List[Int]] =
    map2(parList, unit(()))((a,_) => a.sorted)

  def map[A,B](pa: Par[A])(f: A => B) : Par[B] =
    map2(pa, unit(()))((a,_) => f(a))

  def sortedPar_1(parList: Par[List[Int]]) = map(parList)(_.sorted)

  def sequence[A](ps: List[Par[A]]): Par[List[A]] =
    ps.foldRight[Par[List[A]]] (unit(List())) ( (h,t) => map2(h,t)(_::_) )

  def parMap[A,B](ps: List[A])(f: A => B): Par[List[B]] = fork {
    val fbs: List[Par[B]] = ps.map(asyncF(f))
    sequence(fbs)
  }

  def parFilter[A](as: List[A])(f: A => Boolean): Par[List[A]] = {
    val pars: List[Par[List[A]]] =
      as map asyncF((a: A) => if (f(a))
                                 List(a)
                               else
                                 List())
    map(sequence(pars))(_.flatten) // convenience method on `List` for concatenating a list of lists
  }

  def delay[A](fa: => Par[A]): Par[A] =
    es => fa(es)

  def choice[A](cond: Par[Boolean])(t: Par[A], f: Par[A]): Par[A] =
    es =>
      if (run(es)(cond).get) t(es)
      else f(es)

  def choiceN[A](n: Par[Int])(choices: List[Par[A]]): Par[A] =
    es => {
      val ind = run(es)(n).get()
      run(es)(choices(ind))
    }

  def choiceViaChoiceN[A](a: Par[Boolean])(ifTrue: Par[A], ifFalse: Par[A]): Par[A] =
    choiceN(map(a)(a => if(a) 0 else 1))(List(ifTrue,ifFalse))

  def choiceMap[K,V](key: Par[K])(choices: Map[K,Par[V]]): Par[V] =
    es => {
      val k = run(es)(key).get()
      run(es)(choices(k))
    }

  // it's a parallel computation that, when run, will run an
  // an initial computation whose result is used to determi-
  // ne a second computation
  def chooser[A,B](p: Par[A])(choices: A => Par[B]): Par[B] =
    es => {
      val k = run(es)(p).get()
      run(es)(choices(k))
    }

  def flatMap[A,B](a: Par[A])(f: A => Par[B]): Par[B] =
    es => {
      val k = run(es)(a).get
      run(es)(f(k))
    }

  def join[A](a: Par[Par[A]]): Par[A] =
    es => run(es)(run(es)(a).get())
}
