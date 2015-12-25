lazy val commonSettings = Seq(
  organization := "smile",
  version := "0.1",
  scalaVersion := "2.11.6"
)

val specs2 = "org.specs2" %% "specs2-core" % "3.6.6" % "test"
val jackson = "org.codehaus.jackson" % "jackson-core-asl" % "1.9.13"

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "learning-scala",
    libraryDependencies += specs2,
    libraryDependencies += jackson,
    scalacOptions in Test ++= Seq("-Yrangepos")
  )