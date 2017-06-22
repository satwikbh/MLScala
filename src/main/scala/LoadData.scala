import MLAlgos.ClusteringAlgos
import common.Helper.HelperClass
import org.apache.log4j.Logger
import org.apache.spark.{SparkConf, SparkContext}
import services.Conversion.ConversionLogic
import services.ReadData.ReadFromMongo

import scala.collection.JavaConverters._

/**
  * Created by satwik on 5/30/17.
  */
object LoadData extends App {

  val log: Logger = Logger.getLogger(LoadData.getClass)

  def sparkInvocations: SparkContext = {
    val conf: SparkConf = new SparkConf()
      .setAppName("MLScala")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    val sc: SparkContext = new SparkContext(conf)
    sc
  }

  def newStart(): Unit = {
    log.info("************ Program Started *************")
    val startTime = System.currentTimeMillis()
    val sc = sparkInvocations

    val allMongoDocs = ReadFromMongo.getAllMongoDocs.asScala.map(line => {
      (line._1, line._2.asScala.toArray)
    }
    ).toMap

    val arrayOfBOW = allMongoDocs.map(eachDocPair => {
      val something = eachDocPair._2.map(eachDoc => {
        val some = ConversionLogic.getDoc(eachDoc).asScala.toArray
        some
      })
      something.flatten.distinct
    }).toArray

    val sparseMatrix = HelperClass.newGetSparseMatrix(sc, arrayOfBOW)
    println(sparseMatrix.length)
    val inputData = sparseMatrix.map(line => line.toArray)

    val inputDataRDD = sc.parallelize(inputData)
    val clusters = ClusteringAlgos.sparkMllibKmeans(inputDataRDD, 10)

    // Shows the result.
    log.info("Cluster Centers: ")
    clusters.foreach(println)

    //    val model = ClusteringAlgos.smileKMeans(input_data, 10)
    //    println(model.mkString)

    //    val inputData = sc.parallelize(sparseMatrix)
    //    val lshBasedClusters = LSHAlgos.mrSqueezeLSH(input_data = inputData, p = 65537, m = 1000, numRows = 1000, numBands = 100, minClusterSize = 2)
    //    println(lshBasedClusters.saveAsTextFile("/home/satwik/Documents/Research/MLScalaMVN/lsh"))

    log.info("************ Program Ends *************")
    log.info(s"Time taken : ${System.currentTimeMillis() - startTime}")
    sc.stop()
  }

  newStart()
}
