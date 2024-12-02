package datacloud.scala.tpfonctionnel

object Statistics {
  def average(l: List[(Double, Double)]): Double = {
    val sum_pond = l.map{ case (n, c) => n * c }.reduce((x, y) => x + y)
    val sum_coef = l.map{ case (_, c) => c }.reduce((x, y) => x + y)
    if (sum_coef != 0) sum_pond / sum_coef
    else 0.0
  }
}