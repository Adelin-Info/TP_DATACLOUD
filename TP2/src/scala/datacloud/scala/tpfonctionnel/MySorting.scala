package datacloud.scala.tpfonctionnel

object MySorting {
  def isSorted[A](t: Array[A], f: (A, A) => Boolean): Boolean = {
    var res = true
    for (i <- 1 until t.length) {
      if (!f(t(i - 1), t(i))) res = false
    }
    res
  }

  def ascending[T](a: T, b: T)(implicit ord: Ordering[T]): Boolean = ord.lteq(a,b)

  def descending[T](a: T, b: T)(implicit  ord: Ordering[T]): Boolean = ord.lteq(b,a)

}