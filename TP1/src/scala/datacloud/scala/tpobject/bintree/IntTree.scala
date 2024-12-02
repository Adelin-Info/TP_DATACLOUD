package datacloud.scala.tpobject.bintree

sealed abstract class IntTree
case object EmptyIntTree extends IntTree
case class NodeInt(elem : Int, left : IntTree, right : IntTree) extends IntTree