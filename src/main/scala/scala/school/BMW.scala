package scala.school

trait Car {
  val brand: String
}

trait Shiny {
  val shineRefraction: Int
}

class BMW extends Car with  Shiny {
  val brand = "BMW"
  val shineRefraction = 12
}