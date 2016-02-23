package ch4

/**
  * Created by huay on 23/02/2016.
  */
case class Employee(name: String, department: String) {
  def lookupByName(name: String): Option[Employee] = ???

  val joeDepartment: Option[String] =
    lookupByName("Joe").map(_.department)

  val dept: String =
    lookupByName("Joe").
      map(_.dept).
      filter(_ != "Accounting").
      getOrElse("Default Dept")
}
