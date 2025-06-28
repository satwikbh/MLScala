import MLAlgos.DimensionalityReduction
import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.ReadConfig
import common.utils.DBUtilScala
import org.apache.log4j.Logger
import org.apache.spark.ml.feature.{HashingTF, IDF}
import org.apache.spark.sql.SparkSession
import services.Conversion.ConversionLogic

import scala.collection.JavaConverters._

/**
  * Created by satwik on 5/30/17.
  */
object LoadData extends App {

  val log: Logger = Logger.getLogger(LoadData.getClass)

  def sparkInvocations: SparkSession = {
    val spark = SparkSession.builder()
      .appName("MLScala")
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .getOrCreate()
    spark
  }

  def newStart(): Unit = {
    log.info("************ Program Started *************")
    val startTime = System.currentTimeMillis()
    val spark = sparkInvocations
    val sc = spark.sparkContext

    val mongoRDD = DBUtilScala.mongoConnectorRDD(sc)
    val documents = mongoRDD.map(doc => {
      ConversionLogic.getDoc(doc.toJson).asScala.toArray
    }).map(arr => arr.mkString(" "))

    val documentsDF = spark.createDataFrame(documents.map(Tuple1.apply)).toDF("text")

    val hashingTF = new HashingTF()
      .setInputCol("text").setOutputCol("rawFeatures").setNumFeatures(1000)

    val featurizedData = hashingTF.transform(documentsDF)

    val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
    val idfModel = idf.fit(featurizedData)

    val rescaledData = idfModel.transform(featurizedData)

    val projectedVector = DimensionalityReduction.mllibPCA(rescaledData.select("features").rdd.map(row => row.getAs[org.apache.spark.ml.linalg.SparseVector](0).asML))

    projectedVector.foreach(line => {
      println(line.toArray.mkString)
    })

    log.info("************ Program Ends *************")
    log.info(s"Time taken : ${System.currentTimeMillis() - startTime}")
    sc.stop()
  }

  newStart()
}
