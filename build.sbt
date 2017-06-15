name := "MinLSH"

version := "1.0"

scalaVersion := "2.10.4"

retrieveManaged := true

// https://mvnrepository.com/artifact/org.apache.spark/spark-core_2.10
libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "2.1.0"

// https://mvnrepository.com/artifact/org.apache.spark/spark-mllib_2.10
libraryDependencies += "org.apache.spark" % "spark-mllib_2.10" % "2.1.0"

// https://mvnrepository.com/artifact/org.apache.spark/spark-sql_2.10
libraryDependencies += "org.apache.spark" % "spark-sql_2.10" % "2.1.0"

// https://mvnrepository.com/artifact/org.apache.spark/spark-streaming_2.10
libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "2.1.0"

// https://mvnrepository.com/artifact/org.apache.spark/spark-hive_2.10
libraryDependencies += "org.apache.spark" % "spark-hive_2.10" % "2.1.0"

// https://mvnrepository.com/artifact/org.mongodb.spark/mongo-spark-connector_2.10
libraryDependencies += "org.mongodb.spark" % "mongo-spark-connector_2.10" % "2.0.0"

// https://mvnrepository.com/artifact/com.soundcloud/cosine-lsh-join-spark_2.10
libraryDependencies += "com.soundcloud" % "cosine-lsh-join-spark_2.10" % "1.0.1"

// https://mvnrepository.com/artifact/com.invincea/spark-hash
libraryDependencies += "com.invincea" % "spark-hash" % "0.1.3"