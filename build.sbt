lazy val commonSettings = Seq(
  organization := "smile",
  version := "0.1",
  //scalaVersion := "2.11.6"
  scalaVersion := "2.10.6"
)

val specs2 = "org.specs2" %% "specs2-core" % "3.6.6" % "test"
val jackson = "org.codehaus.jackson" % "jackson-core-asl" % "1.9.13"
val akka = "com.typesafe.akka" %% "akka-actor" % "2.3.4"
val spark = "org.apache.spark" %% "spark-core" % "1.6.0"
lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "learning-scala",
    libraryDependencies += specs2,
    libraryDependencies += jackson,
    libraryDependencies += akka,
    libraryDependencies += spark,
    scalacOptions in Test ++= Seq("-Yrangepos")
  )