package common.utils

import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.ReadConfig
import com.mongodb.spark.rdd.MongoRDD
import common.config.Config
import org.apache.spark.SparkContext
import org.apache.spark.sql._
import org.bson.Document

/**
  * Created by satwik on 15/6/17.
  */
object DBUtilScala extends App {

  def mongoConnectorRDD(sc: SparkContext): MongoRDD[Document] = {
    val config = Config.getInstance()
    val readConfig = ReadConfig(Map(
      "spark.mongodb.input.uri" -> s"mongodb://${config.getMongoDBHost()}:${config.getMongoDBPort()}/${config.getMongoDBAuthDB()}",
      "spark.mongodb.input.database" -> config.getMongoDBName(),
      "spark.mongodb.input.collection" -> config.getMongoDBCollectionName(),
      "spark.mongodb.input.readPreference.name" -> "primaryPreferred"
    ))
    val mongoRDD = MongoSpark.load(sc, readConfig)
    mongoRDD
  }

  

}
