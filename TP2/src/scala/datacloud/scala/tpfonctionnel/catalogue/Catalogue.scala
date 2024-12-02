package datacloud.scala.tpfonctionnel.catalogue

trait Catalogue {
  def getPrice(productName: String): Double
  def removeProduct(productName: String): Unit
  def selectProducts(minPrice: Double, maxPrice: Double): Iterable[String]
  def storeProduct(productName: String, price: Double): Unit
}