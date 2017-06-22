package services

import common.Helper.HelperClass
import common.utils.DBUtilScala
import org.apache.log4j.Logger
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}
import services.Conversion.ConversionLogic
import services.ReadData.ReadFromMongo

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  * Created by satwik on 5/30/17.
  */
object LoadData extends App {

  val keyValuePair = new mutable.HashMap[String, java.util.Set[String]]()
  val log: Logger = Logger.getLogger(LoadData.getClass)

  def sparkInvocations: SparkContext = {
    val conf: SparkConf = new SparkConf()
      .setMaster("local[*]")
      .setAppName("MLScala")
      .set("spark.mongodb.keep_alive_ms", "60000")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    val sc: SparkContext = new SparkContext(conf)
    sc
  }

  def start(): Unit = {

    val startTime = System.nanoTime()

    val sc = sparkInvocations
    val spark = SparkSession
      .builder
      .getOrCreate()

    val hc = new HiveContext(sc)
    val sqlContext = new SQLContext(sc)

    val mongoMainRDD = DBUtilScala.mongoConnectorRDD(sc)
    val mongoDF = mongoMainRDD.toDF()
    mongoDF.createTempView("mongoTable")
    val df = hc.sql("select key, first(value), first(feature) from mongoTable group by key")
    df.rdd.saveAsTextFile("/home/satwik/Documents/spark_storage/100docs")

    //    val rddDoc = HelperClass.getSparseMatrix(sc, mongoMainRDD)

    //    val testTrainSplit = rddDoc.randomSplit(weights = Array(0.70, 0.30), seed = 1)

    //    val train = testTrainSplit(0)
    //    val test = testTrainSplit(1)

    //    val trainLength = train.count().toInt
    //    println(s"Train Length $trainLength")

    //    val testLength = test.count().toInt
    //    println(s"Test Length $testLength")

    //    val model = Clustering.smileKMeans(some, 10, 500, 20)
    //    println(model.mkString)

    println(s"Time taken : ${System.nanoTime() - startTime}")

    sc.stop()
  }

  def newStart(): Unit = {
    val startTime = System.nanoTime()

    val sc = sparkInvocations
    val spark = SparkSession
      .builder
      .getOrCreate()

    val allMongoDocs = ReadFromMongo.getAllMongoDocs.asScala.map(line => {
      (line._1, line._2.asScala.toArray)
    }
    ).toMap

    val arrayOfBOW = allMongoDocs.flatMap(eachDocPair => {
      eachDocPair._2.map(eachDoc => {
        ConversionLogic.getDoc(eachDoc).asScala.toArray
      })
    }).toArray

    val sparseMatrix = HelperClass.newGetSparseMatrix(sc, arrayOfBOW)
    println(sparseMatrix.length)
    //    val input_data = sparseMatrix.map(line => line.toArray)
    //    val model = Clustering.smileKMeans(input_data, 10, 500, 20)
    //    println(model.mkString)

    println(s"Time taken : ${System.nanoTime() - startTime}")
    sc.stop()
  }

  newStart()

}
