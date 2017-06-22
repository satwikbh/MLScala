package common.utils

import com.mongodb.casbah.{WriteConcern => MongodbWriteConcern}
import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.ReadConfig
import com.mongodb.spark.rdd.MongoRDD
import com.stratio.datasource.mongodb._
import com.stratio.datasource.mongodb.config.MongodbConfig._
import com.stratio.datasource.mongodb.config.MongodbConfigBuilder
import org.apache.spark.SparkContext
import org.apache.spark.sql._
import org.bson.Document

/**
  * Created by satwik on 15/6/17.
  */
object DBUtilScala extends App {

  def mongoConnectorRDD(sc: SparkContext): MongoRDD[Document] = {
    val readConfig = ReadConfig(Map(
      "spark.mongodb.input.uri" -> "mongodb://localhost:27017/admin",
      "spark.mongodb.input.database" -> "cuckoo",
      "spark.mongodb.input.collection" -> "cluster2db",
      "spark.mongodb.input.readPreference.name" -> "primaryPreferred",
      "mongo.input.query" -> ""
    ))
    val mongoRDD = MongoSpark.load(sc, readConfig)
    mongoRDD
  }

  def stratioMongoConnector(spark: SparkSession): DataFrame = {
    // params -> ip: String, port: String, database: String, collection: String
    val builder = MongodbConfigBuilder(Map(Host -> List("localhost:27017"), Database -> "cuckoo", Collection -> "cluster2db"))
    val readConfig = builder.build()
    val mongoRDD = spark.sqlContext.fromMongoDB(readConfig)
    mongoRDD.createTempView("mongoTable")

    val dataFrame = spark.sql("SELECT * FROM mongoTable")
    dataFrame
  }

}
