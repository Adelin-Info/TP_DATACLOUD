package datacloud.spark.core.stereoprix

import org.apache.spark.{SparkConf, SparkContext}

object Stats {
  def chiffreAffaire(url: String, year: Int): Int = {
    val conf = new SparkConf().setAppName("Chiffre Affaire").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val d1 = sc.textFile(url)
    val d2 = d1.map(line => line.split(" "))
    val d3 = d2.map(fields => (fields(0).split("_")(2).toInt, fields(2).toInt))
    val d4 = d3.filter(t => t._1 == year)
    val d5 = d4.map(t => t._2)
    val res = d5.reduce(_ + _)
    sc.stop()
    res
  }

  def chiffreAffaireParCategorie(url_in: String, url_out: String): Unit = {
    val conf = new SparkConf().setAppName("Chiffre Affaire Par Categorie").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val d1 = sc.textFile(url_in)
    val d2 = d1.map(line => line.split(" "))
    val d3 = d2.map(fields => (fields(4), fields(2).toInt))
    val d4 = d3.reduceByKey(_ + _)
    val res = d4.map{ case (categorie, ca) => s"$categorie:$ca" }
    res.saveAsTextFile(url_out)
    sc.stop()
  }

  def produitLePlusVenduParCategorie(url_in: String, url_out: String): Unit = {
    val conf = new SparkConf().setAppName("Produit Le Plus Vendu Par Categorie").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val d1 = sc.textFile(url_in)
    val d2 = d1.map(line => line.split(" "))
    val d3 = d2.map(fields => ((fields(4), fields(3)), 1))
    val d4 = d3.reduceByKey(_ + _).map(t => (t._1._1, (t._1._2, t._2)))
    val d5 = d4.reduceByKey((p1, p2) => if (p1._2 > p2._2) p1 else p2)
    val res = d5.map{ case (categorie, (produit, _)) => s"$categorie:$produit" }
    res.saveAsTextFile(url_out)
    sc.stop()
  }
}