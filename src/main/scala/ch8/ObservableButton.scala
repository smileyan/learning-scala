package ch8

/**
  * Created by huay on 10/02/2016.
  */
class ObservableButton(name: String)
    extends Button(name) with Subject[Button] {

  override def click(): Unit = {
    super.click()
    notifyObservers(this)
  }
}
