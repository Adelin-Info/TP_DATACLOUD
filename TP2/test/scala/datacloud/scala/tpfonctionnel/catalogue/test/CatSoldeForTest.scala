package datacloud.scala.tpfonctionnel.catalogue.test

import datacloud.scala.tpfonctionnel.catalogue.CatalogueSoldeWithFor
import org.junit.Test

class CatSoldeForTest extends AbstractCatSoldeTest {
  
  @Test
  def f()= super.testCatalogue(new CatalogueSoldeWithFor)
  
}