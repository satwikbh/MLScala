package MLAlgos

import com.invincea.spark.hash.LSH
import com.soundcloud.lsh.Lsh
import org.apache.spark.ml.feature.{BucketedRandomProjectionLSH, BucketedRandomProjectionLSHModel}
import org.apache.spark.mllib.linalg.SparseVector
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.DataFrame

/**
  * Created by satwik on 15/6/17.
  */
object LSHAlgos extends App {

  def bucketedRandomHashProjection(trainDF: DataFrame): BucketedRandomProjectionLSHModel = {
    val brp = new BucketedRandomProjectionLSH()
      .setBucketLength(2.0)
      .setNumHashTables(3)
      .setInputCol("features")
      .setOutputCol("hashes")

    val model = brp.fit(trainDF)
    model
  }

  def soundCloudLSH(minCosineSimilarity: Double, dimensions: Int, numNeighbours: Int, numPermutations: Int): Lsh = {
    val lsh = new Lsh(minCosineSimilarity = -1.0, dimensions = 100, numNeighbours = 20, numPermutations = 100)
    lsh
  }

  /**
    *
    * @param input_data     The data on which LSH is performed.
    * @param p              a prime number greater than the largest vector index. In the case of open ports, this number is set to 65537.
    * @param m              the number of "bins" to hash data into. Smaller numbers increase collisions. Default is 1000.
    * @param numRows        the total number of times to minhash a vector. numRows hash functions are generated.
    *                       Larger numbers produce more samples and increase the likelihood similar vectors will hash together.
    * @param numBands       how many times to chop numRows. Each band will have numRows/numBand hash signatures.
    *                       The larger number of elements the higher confidence in vector similarity.
    * @param minClusterSize Post processing filter function that excludes clusters below a threshold.
    * @return
    */
  def mrSqueezeLSH(input_data: RDD[SparseVector], p: Int, m: Int, numRows: Int, numBands: Int, minClusterSize: Int): RDD[(Long, Iterable[SparseVector])] = {
    val lsh = new LSH(input_data, p, m, numRows, numBands, minClusterSize)
    val model = lsh.run()
    model.clusters
  }

  /*
  def smileLSH(keys: Array[Array[Double]], data: Array[_ <: Object]): LSH[Object] = {
    // TODO : Smile LSH doc says data can be E[] what is E ? For now keeping Any but need to check on this.
    // https://haifengl.github.io/smile/api/java/smile/neighbor/LSH.html
    val model = new LSH(keys, data)
    model
  }
  */

}
