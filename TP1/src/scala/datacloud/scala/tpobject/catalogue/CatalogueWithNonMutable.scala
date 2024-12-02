package datacloud.scala.tpobject.catalogue

class CatalogueWithNonMutable extends Catalogue {
  private var products: Map[String, Double] = Map()

  def getPrice(productName: String): Double = {
    products.getOrElse(productName, -1.0)
  }

  def removeProduct(productName: String): Unit = {
    products = products - productName
  }

  def selectProducts(minPrice: Double, maxPrice: Double): Iterable[String] = {
    products.filter({case (_, price) => price >= minPrice && price <= maxPrice}).keys
  }

  def storeProduct(productName: String, price: Double): Unit = {
    products =  products + (productName -> price)
  }
}