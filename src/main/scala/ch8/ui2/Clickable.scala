package ch8.ui2

/**
  * Created by huay on 11/02/2016.
  */
trait Clickable {
  def click(): Unit = updateUI()

  protected def updateUI(): Unit
}
