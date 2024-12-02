package datacloud.scala.tpfonctionnel.catalogue

class CatalogueSoldeWithFor extends CatalogueWithNonMutable with CatalogueSolde {

  def solde(percent: Int): Unit = {
    for ((productName, price) <- products) {
      val newPrice = price * ((100.0 - percent) / 100.0)
      products = products + (productName -> newPrice)
    }
  }
}