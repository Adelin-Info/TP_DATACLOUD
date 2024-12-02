package datacloud.scala.tpfonctionnel

object Counters {
  def nbLetters(l: List[String]): Int = {
    l.flatMap(line => line.split(" ")).map(word => word.length).reduce((x, y) => x + y)
  }
}