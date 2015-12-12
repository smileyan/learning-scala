lazy val commonSettings = Seq(
  organization := "smile",
  version := "0.1",
  scalaVersion := "2.11.6"
)

val specs2 = "org.specs2" %% "specs2-core" % "3.6.6" % "test"

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "learning-scala",
    libraryDependencies += specs2,
    scalacOptions in Test ++= Seq("-Yrangepos")
  )