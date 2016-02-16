/**
  * Created by huay on 16/02/2016.
  */
class Parent(val value: Int) {
  override def toString = s"${this.getClass.getName}"
}

class Child(value: Int) extends Parent(value)

val op1: Option[Parent] = Option(new Child(1))
val p1: Parent = op1.getOrElse(new Parent(10))
