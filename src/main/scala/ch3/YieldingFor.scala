package ch3
object YieldingFor{
  val db = List("d", "y t", "da", "s t", "g da", "p w do")

  val filteredB = for {
    b <- db
    if b.contains("t")
    if !b.startsWith("y")
  } yield b

  val upcaseBs = for {
    b <- db
    ucb = b.toUpperCase()    
  } yield ucb
}