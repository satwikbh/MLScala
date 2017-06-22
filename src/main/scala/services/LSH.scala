package services

import com.soundcloud.lsh.Lsh
import org.apache.spark.ml.feature.{BucketedRandomProjectionLSH, BucketedRandomProjectionLSHModel}
import org.apache.spark.sql.DataFrame

/**
  * Created by satwik on 15/6/17.
  */
object LSH extends App {

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

  /*
  def smileLSH(keys: Array[Array[Double]], data: Array[_ <: Object]): LSH[Object] = {
    // TODO : Smile LSH doc says data can be E[] what is E ? For now keeping Any but need to check on this.
    // https://haifengl.github.io/smile/api/java/smile/neighbor/LSH.html
    val model = new LSH(keys, data)
    model
  }
  */

}
