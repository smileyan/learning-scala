package ch1

/**
  * Created by huay on 16/02/2016.
  */
class Cafe {
  def buyCoffee(cc: CreditCard): Coffee = {
    val cup = new Coffee()
    cc.charge(cup.price)
    cup
  }
}

class CreditCard {
  def charge(price: Int): Int = ???
}

class Coffee() {
  def price(): Int = ???
}