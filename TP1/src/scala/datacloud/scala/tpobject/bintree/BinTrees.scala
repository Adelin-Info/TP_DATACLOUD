package datacloud.scala.tpobject.bintree

object BinTrees {

  def contains(tree: IntTree, value: Int): Boolean = tree match {
    case EmptyIntTree => false
    case NodeInt(elem, left, right) =>
      if (value == elem) true
      else contains(left, value) || contains(right, value)
  }

  def size(tree: IntTree):Int = tree match {
    case EmptyIntTree => 0
    case NodeInt(_, left, right) => 1 + size(left) + size(right)
  }

  def insert(tree: IntTree, value: Int): IntTree =  tree match {
    case EmptyIntTree => NodeInt(value, EmptyIntTree, EmptyIntTree)
    case NodeInt(elem, left, right) =>
      if (size(left) < size(right)) NodeInt(value, insert(left, elem), right)
      else NodeInt(value, left, insert(right, elem))
  }

  def contains[A](tree: Tree[A], value: A): Boolean = tree match {
    case EmptyTree => false
    case Node(elem, left, right) =>
      if (value == elem) true
      else contains[A](left, value) || contains[A](right, value)
  }

  def size[A](tree: Tree[A]): Int = tree match {
    case EmptyTree => 0
    case Node(_, left, right) => 1 + size[A](left) + size[A](right)
  }

  def insert[A](tree: Tree[A], value: A): Tree[A] = tree match {
    case EmptyTree => Node(value, EmptyTree, EmptyTree)
    case Node(elem, left, right) =>
      if (size[A](left) < size[A](right)) Node(value, insert[A](left, elem), right)
      else Node(value, left, insert[A](right, elem))
  }
}