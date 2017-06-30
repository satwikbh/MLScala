package MLAlgos

import org.apache.log4j.Logger
import org.apache.spark.mllib.linalg.distributed.RowMatrix
import org.apache.spark.mllib.linalg.{Matrix, SingularValueDecomposition, Vector}
import org.apache.spark.rdd.RDD

/**
  * Created by satwik on 23/6/17.
  */
object DimensionalityReduction {

  val log: Logger = Logger.getLogger(DimensionalityReduction.getClass)

  def findSum(arr: Array[Double], sum: Double): Int = {
    var count = 0
    var sumVal = 0.0
    for (element <- arr) {
      if (sumVal <= sum) {
        sumVal += element
        count += 1
      }
    }
    count
  }

  def thresholdPoint(sigma: Vector, cutoffPoint: Double): Int = {
    val arr = sigma.toArray
    val sum = arr.sum * cutoffPoint

    findSum(arr, sum)
  }

  def mllibSVD(inputMatrix: RDD[Vector], cutoffPoint: Double): Int = {
    val mat: RowMatrix = new RowMatrix(inputMatrix)

    // Compute the top 5 singular values and corresponding singular vectors.
    val svd: SingularValueDecomposition[RowMatrix, Matrix] = mat.computeSVD(mat.numCols.toInt - 1, computeU = true)

    // The singular values are stored in a local dense vector.
    val sigmaMatrix: Vector = svd.s

    thresholdPoint(sigmaMatrix, cutoffPoint)
  }

  def mllibPCA(inputMatrix: RDD[Vector]): RDD[Vector] = {
    val mat: RowMatrix = new RowMatrix(inputMatrix)

    val reducedDim = mllibSVD(inputMatrix, 0.9)

    val pc: Matrix = mat.computePrincipalComponents(reducedDim)
    val projected: RowMatrix = mat.multiply(pc)

    projected.rows
  }

}
