package scala.school

import org.specs2._

object MoreCollectionsSpec extends org.specs2.mutable.Specification {
  "The basics" >> {
    // You can cons them up as you would expect in a functional language.
    List(1, 2, 3) must_== 1 :: 2 :: 3 :: Nil

    // Sets have no duplicates
    Set(1, 1, 2) must_== Set(1, 2)

    // Sequences have a defined order.
    // (Notice that returned a List. 
    // Seq is a trait; List is a lovely implementation of Seq. 
    // There’s a factory object Seq which, as you see here, creates Lists.)
    Seq(1, 1, 2) must_== 1 :: 1 :: 2 :: Nil

    // Maps are key value containers.
    Map('a' -> 1, 'b' -> 2)

    true must_== true
  }
  "The Hierarchy" >> {

    // Traversable
    // A trait for traversable collections.
    // All operations are guaranteed to be performed in a single-threaded manner.
    // This is a base trait of all kinds of Scala collections.
    // It implements the behavior common to all collections,
    //   in terms of a method foreach with signature:
    // def foreach[U](f: Elem => U): Unit

    // Iterable
    // A base trait for iterable collections.
    // This is a base trait for all Scala collections that 
    //   define an iterator method to step through one-by-one the collection's elements.
    // Implementations of this trait need to provide a concrete method with signature:
    // def iterator: Iterator[A]    

    // Seq
    // Sequence of items with ordering.
    
    // Set
    // A collection of items with no duplicates.

    // Map
    // Key Value Pairs.

    true must_== true
  }
  "The methods" >> {
    // Traversable

    // def head : A
    // def tail : Traversable[A]    

    // def map [B] (f: (A) => B) : CC[B]
    // returns a collection with every element transformed by f

    // def foreach[U](f: Elem => U): Unit
    // executes f over each element in a collection.

    // def find (p: (A) => Boolean) : Option[A]
    // returns the first element that matches the predicate function

    // def filter (p: (A) => Boolean) : Traversable[A]
    // returns a collection with all elements matching the predicate function

    // Partitioning:

    // def partition (p: (A) ⇒ Boolean) : (Traversable[A], Traversable[A])
    // Splits a collection into two halves based on a predicate function

    // def groupBy [K] (f: (A) => K) : Map[K, Traversable[A]] 

    // Interestingly, you can convert one collection type to another.

    // def toArray : Array[A]
    // def toArray [B >: A] (implicit arg0: ClassManifest[B]) : Array[B]
    // def toBuffer [B >: A] : Buffer[B]
    // def toIndexedSeq [B >: A] : IndexedSeq[B]
    // def toIterable : Iterable[A]
    // def toIterator : Iterator[A]
    // def toList : List[A]
    // def toMap [T, U] (implicit ev: <:<[A, (T, U)]) : Map[T, U]
    // def toSeq : Seq[A]
    // def toSet [B >: A] : Set[B]
    // def toStream : Stream[A]
    // def toString () : String
    // def toTraversable : Traversable[A]

    // Set
    // def contains(key: A): Boolean
    // def +(elem: A): Set[A]
    // def -(elem: A): Set[A]

    // Map
    // Sequence of key and value pairs with lookup by key.
    // Pass a List of Pairs into apply() like so 
    // Map("a" -> 1, "b" -> 2)
    // Map(("a", 2), ("b", 2))
    // "a" -> 2
    // "a".->(2)

    // Commonly-used subclasses
    // HashSet and HashMap Quick lookup,
    // the most commonly used forms of these collections. 
    // TreeMap A subclass of SortedMap, it gives you ordered access.
    // Vector Fast random selection and fast updates.
    // IndexedSeq(1, 2, 3)
    // res0: IndexedSeq[Int] = Vector(1, 2, 3)
    true must_== true
  }

}
