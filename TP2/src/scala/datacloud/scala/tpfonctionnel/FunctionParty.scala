package datacloud.scala.tpfonctionnel

object FunctionParty {
  def curryfie[A,B,C](f: (A,B) => C): A => B => C = {
    (a: A) => (b: B) => f(a, b)
  }

  def decurryfie[A,B,C](f: A => B => C): (A,B) => C = {
    (a: A, b: B) => f(a)(b)
  }

  def compose[A,B,C](f: B => C, g: A => B): A => C = {
    (a: A) => f(g(a))
  }

  def axplusb(a: Int, b: Int): Int => Int = {
    val mult = (a: Int, x: Int) => a * x
    val curried_mult = curryfie(mult)
    val add = (x: Int) => x + b
    compose(add, curried_mult(a))
  }
}