lazy val commonSettings = Seq(
  organization := "smile",
  version := "0.1",
  //scalaVersion := "2.11.6"
  scalaVersion := "2.10.6"
)

val specs2 = "org.specs2" %% "specs2-core" % "3.6.6" % "test"
val jackson = "org.codehaus.jackson" % "jackson-core-asl" % "1.9.13"
val akka = "com.typesafe.akka" %% "akka-actor" % "2.3.4"
val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % "2.3.4"
val spark = "org.apache.spark" %% "spark-core" % "1.6.0"
val junit = "junit" % "junit" % "4.11" % "test"
val junitinterface = "com.novocode" % "junit-interface" % "0.10" % "test"
lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "learning-scala",
    libraryDependencies += specs2,
    libraryDependencies += jackson,
    libraryDependencies += akka,
    libraryDependencies += akkaTestkit,
    libraryDependencies += spark,
    libraryDependencies += junit,
    libraryDependencies += junitinterface,
    scalacOptions in Test ++= Seq("-Yrangepos")
  )