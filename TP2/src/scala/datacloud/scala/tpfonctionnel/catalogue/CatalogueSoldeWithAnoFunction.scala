package datacloud.scala.tpfonctionnel.catalogue

class CatalogueSoldeWithAnoFunction extends CatalogueWithNonMutable with CatalogueSolde {

  def solde(percent: Int): Unit = {
    val diminution = (a: Double, percent: Int) => a * ((100.0 - percent) / 100.0)
    products = products.view.mapValues(price => diminution(price, percent)).toMap
  }
}