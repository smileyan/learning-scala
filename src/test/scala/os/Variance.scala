package os

/**
  * Created by huay on 13/02/2016.
  */
object Variance extends org.specs2.mutable.Specification {
  "func" >> {
    class CSuper             { def msuper() = println("CSuper") }
    class C extends CSuper   { def m() = println("C") }
    class CSub extends C     { def msub() = println("Sub") }

    var f: C => C = (c: C)      => new C
        f         = (c: CSuper) => new CSub
//        f         = (c: CSub)   => new CSuper
  //    Function1[C-, C+]
    1 must_== 1
  }
}
