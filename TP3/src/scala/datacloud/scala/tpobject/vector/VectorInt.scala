package datacloud.scala.tpobject.vector

class VectorInt(val elements: Array[Int]) extends Serializable {

  def length: Int = {
    elements.length
  }

  def get(i: Int): Int = {
    elements.apply(i)
  }

  override def toString: String = {
    elements.mkString
  }

  override def equals(a: Any): Boolean = a match {
    case a: VectorInt => this.elements.sameElements(a.elements)
    case _ => false
  }

  def +(other: VectorInt): VectorInt = {
    val sum = new Array[Int](this.length)
    for (i <- 1 to other.length by 1) {
      sum.update(i - 1, this.get(i - 1) + other.get(i - 1))
    }
    new VectorInt(sum)
  }

  def *(v: Int): VectorInt = {
    new VectorInt(this.elements.map(_ * v))
  }

  def prodD(other: VectorInt): Array[VectorInt] = {
    val res = new Array[VectorInt](this.length)
    for (i <- 1 to this.length by 1) {
      res.update(i - 1, new VectorInt(other.elements.map(_ * this.get(i - 1))))
    }
    res
  }
}

object VectorInt {
  implicit def ArrayToVectorInt(value: Array[Int]): VectorInt = new VectorInt(value)
}