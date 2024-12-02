package datacloud.scala.tpobject.catalogue.test

import org.junit.Test;
import org.junit.Assert._

import datacloud.scala.tpobject.catalogue.CatalogueWithNonMutable;

class CatalogueWithNonMutableTest extends AbstractTest {
  
  @Test
  def testContent:Unit={
    val cl_cat = classOf[CatalogueWithNonMutable]
    assertEquals(1,cl_cat.getDeclaredFields
              .filter(f => f.getType.equals(classOf[scala.collection.immutable.Map[_,_]]))
              .length)
    val cat   = new CatalogueWithNonMutable
    
    cat.storeProduct("prod", 3.4);
    
    val field = cl_cat.getDeclaredFields
              .filter(f => f.getType.equals(classOf[scala.collection.immutable.Map[_,_]]))(0)
    field.setAccessible(true)
    val map =  field.get(cat)
    map.asInstanceOf[scala.collection.immutable.Map[String,Double]].get("prod") match{
      case None => fail()
      case Some(x) => assertEquals(3.4,x,0.0)
    }
  }
  
  
  @Test
  def test=testCatalogue(new CatalogueWithNonMutable)
}