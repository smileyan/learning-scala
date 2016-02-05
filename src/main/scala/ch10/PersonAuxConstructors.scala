package ch10

/**
  * Created by huay on 5/02/2016.
  */
case class Address(street: String, city: String, state: String, zip: String) {
  def this(zip: String) =
    this("[unknown]", Address.zipToCity(zip), Address.zipToState(zip), zip)
}

object Address {
  def zipToCity(zip: String) = "Anytown"
  def zipToState(zip: String) = "CA"
}

case class Person(name: String, age: Option[Int], address: Option[Address]) {
  def this(name: String) = this(name, None, None)
}