package MLAlgos

import org.apache.log4j.Logger
import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.ml.linalg
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import smile.clustering.{KMeans => smileKMeans}

/**
  * Created by satwik on 15/6/17.
  */
object ClusteringAlgos {

  val log: Logger = Logger.getLogger(ClusteringAlgos.getClass)

  /**
    * The Smile Package KMeans.
    *
    * @param input_data The input data which is to be clustered.
    * @param k          Number of clusters.
    * @param maxIter    Maximum Number of Iterations. Default Value is 100.
    * @param runs       Maximum Number of Runs to get the best one. Default Value is 10.
    * @return
    */
  def smileKMeans(input_data: Array[Array[Double]], k: Int, maxIter: Int = 100, runs: Int = 10): Array[Int] = {
    val start_time = System.currentTimeMillis()
    log.info("************ Smile Package KMeans Clustering Starts *************")

    val model = new smileKMeans(input_data, k, maxIter, runs)

    log.info("************ Smile Package KMeans Clustering Starts *************")
    log.info("Time taken : %s".format(System.currentTimeMillis() - start_time))
    model.getClusterLabel
  }

  /**
    * Spark's MLlib Kmeans based clustering
    *
    * @param input_data The input_data in the form of an RDD of Array[Double]
    * @param k          The number of clusters
    * @return
    */
  def sparkMllibKmeans(input_data: RDD[Array[Double]], k: Int): Array[linalg.Vector] = {
    val start_time = System.currentTimeMillis()
    log.info("************ KMeans Clustering Starts *************")
    val sparkSession = SparkSession.builder().getOrCreate()
    // FIXME : I don't know what the below line does but it fixes an error. Should check more on this. This throws some error.
    import sparkSession.implicits._
    val inputDataset = sparkSession.createDataset(input_data)

    val kmeans = new KMeans().setK(k).setSeed(1L)
    val model = kmeans.fit(inputDataset)

    // Evaluate clustering by computing Within Set Sum of Squared Errors.
    val WSSSE = model.computeCost(inputDataset)
    log.info(s"Within Set Sum of Squared Errors = $WSSSE")

    log.info("************ KMeans Clustering Ends *************")
    log.info("Time taken : %s".format(System.currentTimeMillis() - start_time))
    model.clusterCenters
  }

}