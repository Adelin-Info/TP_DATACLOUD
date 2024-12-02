package datacloud.scala.tpfonctionnel.test

import datacloud.scala.tpfonctionnel.Counters._
import org.junit.Assert._
import org.junit.Test

class CountersTest {
  
  @Test
  def f(){
    val l = List("Hadoop est une plateforme distribuee",
        "Spark en est une autre", 
        "scala est un langage", 
        "Java aussi")
    assertEquals(76,nbLetters(l))
  }
}