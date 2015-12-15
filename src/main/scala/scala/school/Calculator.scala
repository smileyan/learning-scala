package scala.school

class Calculator(brand: String) {
  /**
   * A constructor.
   */
  val color: String = if (brand == "TI") {
    "blue"
  } else if (brand == "HP") {
    "black"
  } else {
    "white"
  }

  // A instance method.
  def add(x: Int, y: Int): Int = x + y
}

class ScientificCalculator(brand: String) extends Calculator(brand) {
  def log(m: Double, base: Double) = math.log(m) / math.log(base)
}

class EvenMoreScientificCalculator(brand: String) extends ScientificCalculator(brand) {
  def log(m: Int): Double = log(m, math.exp(1))
}

case class MyCalculator(brand: String, model: String) {

  def add(a: Int, b: Int):Int = {
    throw new IllegalArgumentException("test")
  }

  def close() = {
  }
  
}