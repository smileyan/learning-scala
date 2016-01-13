package ch4 {
  package db_api {
    case class InvalidColumnName(name: String)
      extends RuntimeException(s"Invalid column name $name")
    trait Row {
      def getInt    (column: String): Int
      def getDouble (column: String): Double
      def getText   (column: String): String
    }
  }

  package javadb {
    import db_api._

    case class JRow(representation: Map[String, Any]) extends Row {
      private def get(colName: String): Any =
        representation.getOrElse(colName, throw InvalidColumnName(colName))

      def getInt    (colName: String): Int    = get(colName).asInstanceOf[Int]
      def getDouble (colName: String): Double = get(colName).asInstanceOf[Double]
      def getText   (colName: String): String = get(colName).asInstanceOf[String]

    }

    object JRow {
      def apply(pairs: (String, Any)*) = {
        new JRow(Map(pairs :_*))
      }
    }
  }

  package scaladb {
    object implicits {
      import javadb.JRow

      implicit class SRow(jrow: JRow) {
        def get[T](colName: String)(implicit toT: (JRow, String) => T): T =
          toT(jrow, colName)
      }

      implicit val jrowToInt: (JRow,String) => Int =
        (jrow: JRow, colName: String) => jrow.getInt(colName)
      implicit val jrowToDouble: (JRow,String) => Double =
        (jrow: JRow, colName: String) => jrow.getDouble(colName)
      implicit val jrowToString: (JRow,String) => String =
        (jrow: JRow, colName: String) => jrow.getText(colName)
    }
  }
}
