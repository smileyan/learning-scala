package com.dataman.demo

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkContext, SparkConf}
import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._


/**
  * Created by mymac on 15/11/3.
  */
object Assignment1 {

  case class DocPostion(title: String, positions: List[Int])
  case class Row(word: String, positions: List[DocPostion])

  def main(args: Array[String]) = {
    println("############################################################################")
    if (true) {
      spark()
    } else {
      local()
    }
  }

  def local() = {
    val data = List("317,newsgroups rec motorcyclespath cantaloupe srv cs cmu cmu",
      "318,newsgroups rec motorcyclespath cantaloupe srv cs cmu cs")

    val result = data.flatMap(doc => parse(doc))
      .groupBy(_._1)
      .map(word => toJson(tupleToRow(word)))
    result.foreach(
      word => println(compact(render(word)))
    )
  }

  def spark() = {
    println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")

    val conf = new SparkConf().setAppName("assignment1")
    val sc = new SparkContext(conf)

    Logger.getRootLogger.setLevel(Level.WARN)

    val data = sc.textFile("hdfs://192.168.70.141:8020/Assignment1")

    data.foreach( d => {
      println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
      println(d)
    })
    //please code here.
    val result = data.flatMap(doc => parse(doc))
      .groupBy(_._1)
      .map(word => toJson(tupleToRow(word)))

    result.foreach(
      word => println(compact(render(word)))
    )
    sc.stop()
  }

  def tupleToRow(t: (String, Iterable[(String, (String, List[Int]))])): Row = {
      Row(t._1, t._2.toList.map(r => {
        DocPostion(r._2._1, r._2._2.reverse)
      }))
  }

  def toJson(row: Row): JValue = {
    JField(row.word,
      row.positions.map { w =>
        (w.title, w.positions)
      }
    )
  }

  def parse(doc: String): List[(String, (String, List[Int]))] = {

    val title = doc.split(",").head

    val content = doc.substring(title.length + 1)

    content.split("\\s").foldLeft(List[(String, Int)]())((acc, word) => acc match
    {
      case Nil => (word, 0) :: Nil
      case (w, l) :: t => {
        (word, l + w.length + 1) :: acc
      }
    }).groupBy(_._1).toList.map(w => {
      (w._1, (title, w._2.map(v => v._2))) // (word, (doc_id, [1,2]))
    })
  }

  def test() = {
    val docs = List("317,newsgroups rec motorcyclespath cantaloupe srv cs cmu cmu",
      "318,newsgroups rec motorcyclespath cantaloupe srv cs cmu cs")

    docs.flatMap(d => parse(d)).groupBy(_._1).map(word => tupleToRow(word))
  }
}