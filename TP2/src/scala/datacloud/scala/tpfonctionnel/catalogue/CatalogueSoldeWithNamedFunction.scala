package datacloud.scala.tpfonctionnel.catalogue

class CatalogueSoldeWithNamedFunction extends CatalogueWithNonMutable with CatalogueSolde {

  def diminution(a: Double, percent: Int): Double = a * ((100.0 - percent) / 100.0)

  def solde(percent: Int): Unit = {
    products = products.view.mapValues(price => diminution(price, percent)).toMap
  }
}