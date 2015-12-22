package scala.school

import math.Ordering

case class MyList[A](list: List[A]){
  def sortBy1[B](f: A => B)(implicit ord: Ordering[B]) = 
    list.sortBy(f)(ord)

  // context bound
  def sortBy2[B: Ordering](f: A => B): List[A] =
    list.sortBy(f)(implicitly[Ordering[B]])
}

// def createMenu(imp session: Session): Menu = {
//   val defaultItems = List(helpItem, searchItem)
//   val accountItems =
//     if (session.loggedin()) List(viewAccountItem, editAccountItem)
//     else List(loginItem)
//   Menu(defaultItems ++ accountItems)
// }

// val dogBreeds = List("doberman", "yorkshire terrier", "dachshund",
//                      "scottish terrier", "great dane", "portuguese water dog")

// val filteredBreeds = for {
//   breed <- dogBreeds
//   if breed.contains("terrier") && !breed.startsWith("yorkshire")
// } yield breed