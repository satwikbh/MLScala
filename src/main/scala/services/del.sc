import com.invincea.spark.hash.LSH
import org.apache.spark.SparkContext
import org.apache.spark.mllib.linalg.{SparseVector, Vectors}

val sc = new SparkContext()

val port_set: org.apache.spark.rdd.RDD[(List[Int], Int)] = sc.objectFile("data/sample.dat")
port_set.repartition(8)

println(port_set.count())

val port_set_filtered = port_set.filter(tpl => tpl._1.size > 3)

val vctr = port_set_filtered.map(r => r._1.map(i => (i, 1.0))).map(a => Vectors.sparse(65535, a).asInstanceOf[SparseVector])

val lsh = new LSH(data = vctr, p = 65537, m = 1000, numRows = 1000, numBands = 25, minClusterSize = 2)
val model = lsh.run