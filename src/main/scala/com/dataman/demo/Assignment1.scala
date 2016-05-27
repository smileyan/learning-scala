package com.dataman.demo

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkContext, SparkConf}
import org.json4s.JsonAST.{JField, JObject}

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
    data.flatMap(doc => parse(doc)).groupBy(_._1).map(w => {
      JObject(JField)
    })

    sc.stop()
  }

  def parse(doc: String): List[(String, List[(String, Int)])] = {

    val title = doc.split(",").head

    val newDoc = doc.substring(title.length + 1)

    newDoc.split("\\s").foldLeft(List[(String, Int)]())((acc, word) => acc match
    {
      case Nil => (word, 0) :: Nil
      case (w, l) :: t => {
        (word, l + w.length + 1) :: acc
      }
    }).groupBy(_._1).toList.map(w => {
      (w._1, w._2.map(j => (title, j._2)))
    })
  }
}