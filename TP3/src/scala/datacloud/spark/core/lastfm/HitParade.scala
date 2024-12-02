package datacloud.spark.core.lastfm

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

object HitParade {

  case class TrackId(id: String)
  case class UserId(id: String)

  def loadAndMergeDuplicates(sc: SparkContext, url: String): RDD[((UserId, TrackId), (Int, Int, Int))] = {
    val d1 = sc.textFile(url)
    val d2 = d1.map(line => line.split(" "))
    val d3 = d2.map(fields => ((UserId(fields(0)), TrackId(fields(1))), (fields(2).toInt, fields(3).toInt, fields(4).toInt)))
    val res = d3.reduceByKey((u1, u2) => (u1._1 + u2._1, u1._2 + u2._2, u1._3 + u2._3))
    res
  }

  def hitparade(hits: RDD[((UserId, TrackId), (Int, Int, Int))]): RDD[TrackId] = {
    val d1 = hits.map(t => (t._1._2, (if (t._2._1 > 0 || t._2._2 > 0) 1 else 0, t._2._1 + t._2._2, t._2._3)))
    val d2 = d1.reduceByKey((u1, u2) => (u1._1 + u2._1, u1._2 + u2._2, u1._3 + u2._3))
    val d3 = d2.map(t => (t._1, t._2._1, t._2._2 - t._2._3))
    val d4 = d3.sortBy(t => (-t._2, -t._3, t._1.id))
    val res = d4.map(t => t._1)
    res
  }
}