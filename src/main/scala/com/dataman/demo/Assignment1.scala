package com.dataman.demo

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkContext, SparkConf}

import scala.collection.immutable.HashMap

/**
  * Created by mymac on 15/11/3.
  */
object Assignment1 {

  def main(args: Array[String]) = {

    val conf = new SparkConf().setAppName("assignment1")
    val sc = new SparkContext(conf)

    Logger.getRootLogger.setLevel(Level.WARN)

    val data = sc.textFile("hdfs://192.168.70.141:8020/Assignment1")

//    data.map(doc => parse(doc)).grou
    //please code here.

    sc.stop()
  }

//  def parse(doc: String): Array[Tuple2[String, String]] = {

//    return doc.split(" ").foldLeft(Array[Tuple2[String, String]])
//  }
}