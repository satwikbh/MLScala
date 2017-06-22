name := "MLScala"

version := "1.0"

scalaVersion := "2.11.8"

XitrumPackage.copy()

// https://mvnrepository.com/artifact/org.apache.spark/spark-core_2.11
libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.1.1" % "provided"

// https://mvnrepository.com/artifact/org.apache.spark/spark-sql_2.11
libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.1.1" % "provided"

// https://mvnrepository.com/artifact/org.apache.spark/spark-mllib_2.11
libraryDependencies += "org.apache.spark" % "spark-mllib_2.11" % "2.1.1" % "provided"

// https://mvnrepository.com/artifact/org.apache.spark/spark-streaming_2.11
libraryDependencies += "org.apache.spark" % "spark-streaming_2.11" % "2.1.1" % "provided"

// https://mvnrepository.com/artifact/org.apache.spark/spark-hive_2.11
libraryDependencies += "org.apache.spark" % "spark-hive_2.11" % "2.1.1" % "provided"

// https://mvnrepository.com/artifact/org.mongodb.spark/mongo-spark-connector_2.11
libraryDependencies += "org.mongodb.spark" % "mongo-spark-connector_2.11" % "2.0.0"

// https://mvnrepository.com/artifact/com.soundcloud/cosine-lsh-join-spark_2.11
libraryDependencies += "com.soundcloud" % "cosine-lsh-join-spark_2.11" % "1.0.1"

// https://mvnrepository.com/artifact/com.github.haifengl/smile-core
libraryDependencies += "com.github.haifengl" % "smile-core" % "1.3.1"

// https://mvnrepository.com/artifact/com.github.haifengl/smile-plot
libraryDependencies += "com.github.haifengl" % "smile-plot" % "1.3.1"

libraryDependencies ++= Seq(
  "com.stratio.datasource" %% "spark-mongodb" % "0.12.0",
  "net.ruippeixotog" %% "scala-scraper" % "1.2.0"
)