package datacloud.scala.tpobject.catalogue

import scala.collection.mutable

class CatalogueWithMutable extends Catalogue {
  private val products: mutable.Map[String, Double] = mutable.Map()

  def getPrice(productName: String): Double = {
    products.getOrElse(productName, -1.0)
  }

  def removeProduct(productName: String): Unit = {
    products.remove(productName)
  }

  def selectProducts(minPrice: Double, maxPrice: Double): Iterable[String] = {
    products.filter({case (_, price) => price >= minPrice && price <= maxPrice}).keys
  }

  def storeProduct(productName: String, price: Double): Unit = {
    products.put(productName, price)
  }
}