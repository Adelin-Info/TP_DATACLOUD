package datacloud.spark.core.matrix

import datacloud.scala.tpobject.vector.VectorInt
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD


object MatrixIntAsRDD {
  implicit def vectorToMatrix(value: RDD[VectorInt]): MatrixIntAsRDD = new MatrixIntAsRDD(value)

  def makeFromFile(url: String, nbPartitions: Int, sc: SparkContext): MatrixIntAsRDD = {
    val d1 = sc.textFile(url)
    val d2 = d1.map(line => line.split(" ").map(word => word.toInt))
    val d3 = d2.map(list => new VectorInt(list))
    val d4 = d3.zipWithIndex()
    val d5 = d4.sortBy(t => t._2, true, nbPartitions)
    val d6 = d5.map(t => t._1)
    val res = new MatrixIntAsRDD(d6)
    res
  }
}

class MatrixIntAsRDD(val lines: RDD[VectorInt]) {

  def nbLines: Int = lines.count().toInt

  def nbColumns: Int = lines.first().length

  def get(i: Int, j: Int): Int = {
    lines.zipWithIndex().filter(t => t._2.toInt == i).map(t => t._1).first().get(j)
  }

  override def equals(a: Any): Boolean = a match {
    case other: MatrixIntAsRDD =>
      if ((this.nbLines != other.nbLines) || (this.nbColumns != other.nbColumns)) {
        false
      } else {
        this.lines.zip(other.lines).map(t => t._1.equals(t._2)).reduce(_ && _)
      }
    case _ => false
  }

  def +(other: MatrixIntAsRDD): MatrixIntAsRDD = {
    this.lines.zip(other.lines).map(t => t._1.+(t._2))
  }

  def transpose(): MatrixIntAsRDD = {
    val d1 = lines.zipWithIndex()
    val d2 = d1.flatMap(t1 => t1._1.elements.zipWithIndex.map(t2 => (t2._2, (t1._2, t2._1))))
    val d3 = d2.groupByKey()
    val d4 = d3.sortByKey()
    val res = d4.map(t => new VectorInt(t._2.toSeq.sortBy(_._1).map(_._2).toArray))
    new MatrixIntAsRDD(res)
  }

  def *(other: MatrixIntAsRDD): MatrixIntAsRDD = {
    val thisTranspose = this.transpose()
    val d1 = thisTranspose.lines.zip(other.lines)
    val d2 = d1.flatMap(v => v._1.prodD(v._2).zipWithIndex.map(_.swap))
    val d3 = d2.reduceByKey(_ + _)
    val d4 = d3.sortByKey()
    val res = d4.map(v => v._2)
    new MatrixIntAsRDD(res)
  }

  override def toString: String = {
    val sb = new StringBuilder()
    lines.collect().foreach(line => sb.append(line+"\n"))
    sb.toString();
  }
}