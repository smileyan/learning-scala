package ch8

/**
  * Created by huay on 10/02/2016.
  */
class Button(val label: String) extends Widget {
  def click(): Unit = updateUI()

  def updateUI(): Unit = {}
}
