package MLAlgos


import smile.clustering.KMeans

/**
  * Created by satwik on 15/6/17.
  */
object ClusteringAlgos extends App {

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
    val model = new KMeans(input_data, k, maxIter, runs)
    model.getClusterLabel
  }


}