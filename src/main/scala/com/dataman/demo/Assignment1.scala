package com.dataman.demo

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by mymac on 15/11/3.
  */
object Assignment1 {

  def main(args: Array[String]) = {

    val conf = new SparkConf().setAppName("assignment1")
    val sc = new SparkContext(conf)

    Logger.getRootLogger.setLevel(Level.WARN)

    val data = sc.textFile("hdfs://192.168.70.141:8020/Assignment1")

    //please code here.
    data.flatMap(doc => parse(doc)).groupBy(_._1)

    sc.stop()
  }

  def parse(doc: String): List[(String, (String, Int))] = {

    val title = doc.split(",").head

    doc.split(" ")
       .drop(0)
       .foldLeft(List[(String, (String, Int))]())((acc, word) => acc match
    {
      case Nil => (word, (title, 0)) :: Nil
      case h :: t => {
        (word, (title, h._2._1.length + h._2._2 + 1)) :: acc
      }
    })
  }
}