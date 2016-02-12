package ch8.ui2

/**
  * Created by huay on 12/02/2016.
  */
trait VetoableClicks extends Clickable {
  val maxAllowed = 1
  private var count = 0

  abstract override def click() = {
    if (count < maxAllowed) {
      count += 1
      super.click()
    } else {

    }
  }
}

