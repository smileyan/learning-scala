package ch8

/**
  * Created by huay on 9/02/2016.
  */
trait Observer[-State] {
  def receiveUpdate(state: State): Unit // The trait for clients who want to be notified of state changes.
  // They must implement the receiveUpdate message.
}

trait Subject[State] { // The trait for subjects will send notifications to observers.
  private var observers: List[Observer[State]] = Nil
  //  A list of observers to notify. It's mutable, so it's not thread-safe!

  def addObserver(observer: Observer[State]): Unit =
    observers ::= observer

  def notifyObservers(state: State): Unit =
    observers foreach(_.receiveUpdate(state))
}
