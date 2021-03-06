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
val junit = "junit" % "junit" % "4.11"
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
    // http://mvnrepository.com/artifact/com.h2database/h2
    libraryDependencies += "com.h2database" % "h2" % "1.4.191",

    // libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "2.7.2",
    // http://mvnrepository.com/artifact/org.apache.hadoop/hadoop-common
    // libraryDependencies += "org.apache.hadoop" % "hadoop-common" % "2.7.2",
    // http://mvnrepository.com/artifact/org.apache.hadoop/hadoop-hdfs
    // libraryDependencies += "org.apache.hadoop" % "hadoop-hdfs" % "2.7.2",
    // http://mvnrepository.com/artifact/org.apache.hadoop/hadoop-minicluster
    libraryDependencies += "org.apache.hadoop" % "hadoop-minicluster" % "2.7.2",

    libraryDependencies += "org.json" % "json" % "20160212",
    // http://mvnrepository.com/artifact/org.hamcrest/hamcrest-all
    libraryDependencies += "org.hamcrest" % "hamcrest-all" % "1.3",
    // http://mvnrepository.com/artifact/org.apache.hive/hive-exec
    libraryDependencies += "org.apache.hive" % "hive-exec" % "1.2.1",
    // http://mvnrepository.com/artifact/org.apache.pig/pig
    libraryDependencies += "org.apache.pig" % "pig" % "0.15.0",
    // http://mvnrepository.com/artifact/jline/jline
    libraryDependencies += "jline" % "jline" % "2.12.1",
    // http://mvnrepository.com/artifact/org.apache.sqoop/sqoop
    libraryDependencies += "org.apache.sqoop" % "sqoop" % "1.4.6.2.4.2.5-1",
    // http://mvnrepository.com/artifact/org.apache.crunch/crunch-core
    libraryDependencies += "org.apache.crunch" % "crunch-core" % "0.14.0",
    // http://mvnrepository.com/artifact/org.apache.crunch/crunch-test
    libraryDependencies += "org.apache.crunch" % "crunch-test" % "0.14.0",
    // http://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk
    libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.11.7",
    // https://mvnrepository.com/artifact/org.apache.mahout/mahout-core
    libraryDependencies += "org.apache.mahout" % "mahout-core" % "0.9",

    scalacOptions in Test ++= Seq("-Yrangepos")
  )

resolvers ++= Seq(
  "Maven central" at "http://repo1.maven.org/maven2/",
  "Hadoop Releases" at "https://repository.cloudera.com/content/repositories/releases/",
  "clojars" at "https://clojars.org/repo",
  "conjars" at "http://conjars.org/repo",
  "plugins" at "http://repo.spring.io/plugins-release",
  "sonatype" at "http://oss.sonatype.org/content/groups/public/",
  "Hortonworks Releases" at "http://repo.hortonworks.com/content/repositories/releases/")
