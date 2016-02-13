/**
  * Created by huay on 12/02/2016.
  */

import ch8.Observer
import ch8.ui2._

class T {
  val button =
    new Button("Click me") with ObservableClicks with VetoableClicks {
      override val maxAllowed = 2
    }

  class ClickCountObserver extends Observer[Clickable] {
    var count = 0

    def receiveUpdate(state: Clickable): Unit = count += 1
  }

  val bco1 = new ClickCountObserver
  val bco2 = new ClickCountObserver

  button addObserver bco1
  button.addObserver(bco2)

  (1.to(5)).foreach(_ => button.click())

  assert(bco1.count == 2, s"")
  assert(bco2.count == 2, s"")

}