package ch8.ui2

import ch8.Subject

/**
  * Created by huay on 11/02/2016.
  */
trait ObservableClicks extends Clickable with Subject[Clickable] {
  abstract override def click(): Unit = {
    super.click()
    notifyObservers(this)
  }
}

