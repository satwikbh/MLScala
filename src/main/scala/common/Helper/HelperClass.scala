package common.Helper

import org.apache.spark.SparkContext
import org.apache.spark.ml.linalg
import org.apache.spark.ml.linalg.{SparseVector, Vectors}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.DataFrame
import org.bson.Document
import services.Conversion.ConversionLogic

import scala.collection.JavaConverters._

case class doc(features: org.apache.spark.ml.linalg.SparseVector)

/**
  * Created by satwik on 15/6/17.
  */
object HelperClass extends App {

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
        oneHotVectorEncodingInt(arg1._2.asScala.toArray, broadCastVariable.value)
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

  def newGetSparseMatrix(sc: SparkContext, listOfDocs: Array[Array[String]]): Array[SparseVector] = {
    val featurePool = listOfDocs.flatten.distinct
    val poolLength = featurePool.length

    val broadCastVariable = sc.broadcast(featurePool)

    val matrix = listOfDocs.map(line => {
      oneHotVectorEncodingInt(line, broadCastVariable.value)
    })

    val sparseMatrix = matrix.filter(_ != null).map(line => {
      val arg1 = line.toSeq.map(each => (each, 1.0))
      val sparseVec = Vectors.sparse(poolLength, arg1)
      sparseVec.toSparse
    })
    sparseMatrix
  }

  /**
    * From the feature pool we pick the index where the elements of individual array are picked.
    *
    * @param individualArray
    * @param featurePool
    * @return
    */
  def oneHotVectorEncodingDouble(individualArray: Array[String], featurePool: Array[String]): Array[Double] = {
    val some = individualArray.map(feature => featurePool.indexOf(feature).toDouble)
    some
  }

  def oneHotVectorEncodingInt(individualArray: Array[String], featurePool: Array[String]): Array[Int] = {
    val some = individualArray.map(feature => featurePool.indexOf(feature))
    some
  }

}
