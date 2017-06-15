package services

import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.ReadConfig
import com.mongodb.spark.rdd.MongoRDD
import org.apache.log4j.Logger
import org.apache.spark.ml.feature.{BucketedRandomProjectionLSH, BucketedRandomProjectionLSHModel}
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}
import org.bson.Document

import scala.collection.JavaConverters._
import scala.collection.mutable

case class doc(features: org.apache.spark.ml.linalg.SparseVector)

/**
  * Created by satwik on 5/30/17.
  */
object LoadData {

  val keyValuePair = new mutable.HashMap[String, java.util.Set[String]]()
  val log: Logger = Logger.getLogger(LoadData.getClass)

  def sparkInvocations: SparkContext = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("MinLSH").set("spark.mongodb.keep_alive_ms", "60000")
    val sc: SparkContext = new SparkContext(conf)
    sc
  }

  def mongoConnectorRDD(sc: SparkContext): MongoRDD[Document] = {
    val readConfig = ReadConfig(Map(
      "spark.mongodb.input.uri" -> "mongodb://localhost:27017/admin",
      "spark.mongodb.input.database" -> "cuckoo",
      "spark.mongodb.input.collection" -> "cluster2db",
      "spark.mongodb.input.readPreference.name" -> "primaryPreferred"
    ))
    val mongoRDD = MongoSpark.load(sc, readConfig)
    mongoRDD
  }

  /**
    * From the feature pool we pick the index where the elements of individual array are picked.
    *
    * @param individualArray
    * @param featurePool
    * @return
    */
  def oneHotVectorEncoding(individualArray: Array[String], featurePool: Array[String]): Array[Int] = {
    val some = individualArray.map(feature => featurePool.indexOf(feature))
    some
  }

  def getSparseMatrix(sc: SparkContext, train: RDD[Document]): RDD[doc] = {
    val individualPool = train.map { doc => {
      val ret = ConversionLogic.getDoc(doc).asScala
      val key = ret.keySet.toArray.apply(0)
      val value = ret.values.iterator.toArray.apply(0)
      (key, value)
    }
    }

    val featurePool = individualPool.filter(_._2 != null).flatMap(_._2.asScala).distinct().collect()
    val poolLength = featurePool.length

    val broadCastVariable = sc.broadcast(featurePool)

    val sparseMatrix = individualPool.map(arg1 => {
      if (arg1._2 != null) {
        oneHotVectorEncoding(arg1._2.asScala.toArray, broadCastVariable.value)
      } else {
        null
      }
    })

    val matrix = sparseMatrix.filter(_ != null).map(line => {
      val arg1 = line.toSeq.map(each => (each, 1.0))
      val sparseVec = Vectors.sparse(poolLength, arg1)
      doc(sparseVec.toSparse)
      // sparseVec.toSparse
    }
    )
    matrix
  }

  def bucketedRandomHashProjection(trainDF: DataFrame): BucketedRandomProjectionLSHModel = {
    val brp = new BucketedRandomProjectionLSH()
      .setBucketLength(2.0)
      .setNumHashTables(3)
      .setInputCol("features")
      .setOutputCol("hashes")

    val model = brp.fit(trainDF)
    model
  }

  def main(args: Array[String]): Unit = {

    val startTime = System.nanoTime()

    val sc = sparkInvocations
    val spark = SparkSession
      .builder
      .getOrCreate()

    val mongoMainRDD = mongoConnectorRDD(sc)
    val testTrainSplit = mongoMainRDD.randomSplit(weights = Array(0.70, 0.30), seed = 1)

    val train = testTrainSplit(0)
    val test = testTrainSplit(1)

    val trainLength = train.count().toInt
    println(s"Train Length $trainLength")

    val testLength = test.count().toInt
    println(s"Test Length $testLength")

    val trainMatrix = getSparseMatrix(sc, train)
    val testMatrix = getSparseMatrix(sc, test)

    val trainDF = spark.createDataFrame(trainMatrix)
    val testDF = spark.createDataFrame(testMatrix)

    //    val lsh = new Lsh(minCosineSimilarity = -1.0, dimensions = 100, numNeighbours = 20, numPermutations = 100)

    val model = bucketedRandomHashProjection(trainDF)

    val trainTransformedDF = model.transform(trainDF)
    val testTransformedDF = model.transform(testDF)

    val nonTransformed = model.approxSimilarityJoin(trainDF, testDF, 0.8)
    println("Non Transformed results")
    println(nonTransformed.count())

    val transformed = model.approxSimilarityJoin(trainTransformedDF, testTransformedDF, 0.8)
    println("Transformed results")
    println(transformed.count())


    println(s"Time taken : ${System.nanoTime() - startTime}")

    sc.stop()
  }

}
