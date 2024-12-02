package datacloud.scala.tpfonctionnel

object Premiers {

  def isPrem(cur: Int, i: Int): Boolean = {
    if (cur == i) true
    else if (cur % i == 0) false
    else true
  }

  def premiers(n: Int): List[Int] = {
    var l = List.range(2, n)
    for (i <- l) {
      l = l.filter(x => isPrem(x, i))
    }
    l
  }

  def premiersWithRec(n: Int): List[Int] = {
    def f(l: List[Int]): List[Int] = {
      if (l.head * l.head > l.last) {
        l
      }
      else {
        l.head :: f(l.tail.filter(x => isPrem(x, l.head)))
      }
    }
    val l = List.range(2, n)
    f(l)
  }
}