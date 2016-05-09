lazy val commonSettings = Seq(
  organization := "smile",
  version := "0.1",
  scalaVersion := "2.11.7"
  //scalaVersion := "2.10.6"
)

val specs2 = "org.specs2" %% "specs2-core" % "3.6.6" % "test"
val jackson = "org.codehaus.jackson" % "jackson-core-asl" % "1.9.13"
val akka = "com.typesafe.akka" % "akka-actor_2.11" % "2.3.15"
val akkaTestkit = "com.typesafe.akka" % "akka-testkit_2.11" % "2.3.15"
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

    libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "2.7.2",
//    libraryDependencies += "org.apache.mrunit" % "mrunit" % "1.1.0",
    libraryDependencies += "org.json" % "json" % "20160212",

    scalacOptions in Test ++= Seq("-Yrangepos")
  )