package ch7

import ch10.Address

/**
  * Created by huay on 7/02/2016.
  */
case class Person(name: String,
                  age: Option[Int] = None,
                  Address: Option[Address] = None)

class Employee(name: String,
               age: Option[Int] = None,
               address: Option[Address] = None,
               val title: String = "Unknow",
               val manager: Option[Employee]) extends Person(name, age, address) {
  override def toString = s"$name"

}
