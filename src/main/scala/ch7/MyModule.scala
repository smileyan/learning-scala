package ch7

import java.util.concurrent.Executors

import ch7.Par.Par

/**
  * Created by huay on 8/03/2016.
  */
object MyModule {
  def sum(ints: IndexedSeq[Int]): Int =
    if (ints.size <= 0)
      ints.headOption getOrElse(0)
    else{
      val (l,r) = ints.splitAt(ints.length/2)
      sum(l) + sum(r)
    }

  def sum_1(ints: IndexedSeq[Int]): Int =
    if (ints.size <= 1)
      ints.headOption getOrElse(0)
    else {
      val (l,r) = ints.splitAt(ints.length/2)
      val sumL: Par[Int] = Par.unit(sum_1(l))
      val sumR: Par[Int] = Par.unit(sum_1(r))

      Par.get(sumL) + Par.get(sumR)
    }

  def sum_2(ints: IndexedSeq[Int]): Par[Int] =
    if (ints.size <= 1)
      Par.unit(ints.headOption getOrElse(0))
    else {
      val (l,r) = ints.splitAt(ints.length/2)
      Par.map2(sum_2(l),sum_2(r))(_ + _)
    }

  def sum_3(ints: IndexedSeq[Int]): Par[Int] =
    if (ints.length <= 1)
      Par.unit(ints.headOption getOrElse(0))
    else {
      val (l,r) = ints.splitAt(ints.length/2)
      Par.map2(Par.fork(sum_3(l)), Par.fork(sum_3(r)))(_ + _)
    }

  def main(args: Array[String]) {
    val r: Par[Int] = sum_3(IndexedSeq(1,2,3,4,5,6,7,8,9,1,1,1))
    System.out.println("##########################################################")
    System.out.println(Par.run(Executors.newFixedThreadPool(19))(r).get())
  }
}
