package ch3

class ServiceImportant(name: String) {
  def work(i: Int):Int = {
    i + 1
  }
}

trait Logging {
  def info(message: String): Unit
  def warning(message: String): Unit
  def error(message: String): Unit
}

trait StdoutLogging extends Logging {
  def info(message: String): Unit = println(s"INFO $message")
  def warning(message: String): Unit = println(s"WARNING $message")
  def error(message: String): Unit = println(s"ERROR $message")
}