package ch3
object Breed extends Enumeration {
  type Breed = Value
  val d = Value("d p")
  val y = Value("y t")
  val s = Value("s t")
  val da = Value("g da")
  val p = Value("po w do")
}

object WeekDay extends Enumeration {
  type WeekDay = Value
  val Mon, Tue, Wed, Thu, Fri, Sat, Sun = Value
}