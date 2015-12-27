package introscala.shapes

case class Point(x: Double = 0, y: Double = 0)

abstract class Shape() {
  def draw(f: String => Unit): Unit = f(s"draw: ${this.toString}")
}

case class Circle(center: Point, radius: Double) extends Shape
case class Rectangle(lowerLeft: Point, height: Point, width: Point) extends Shape
case class Triangle(p1: Point, p2: Point, p3: Point) extends Shape