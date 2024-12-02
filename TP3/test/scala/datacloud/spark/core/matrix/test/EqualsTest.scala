package datacloud.spark.core.matrix.test

import java.io.FileOutputStream
import java.nio.file.Files

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

import datacloud.spark.core.matrix.MatrixIntAsRDD.makeFromFile
import scala.util.Random

class EqualsTest extends AbstractTest{
  @Test
  def test():Unit={
    val sizes = List(5,15,50)
    for(nbl <- sizes){
      for(nbc<- sizes){
				val nb_partitions= Math.sqrt(nbl).ceil.toInt
				
				val file = Files.createTempFile("matrice", ".dat")
				val marray =  Util.createRandomMatrixInt(nbl, nbc, file)
				val matrdd = makeFromFile(file.toUri().toASCIIString(), nb_partitions, sc)
				
				val filecopy = Files.createTempFile("matrice", ".dat")
				val fos = new FileOutputStream(filecopy.toFile())
				Files.copy(file, fos )
				fos.close()
				val matrddcopy = makeFromFile(filecopy.toUri().toASCIIString(), nb_partitions, sc)
				
				assertNotEquals("Bonjour",matrddcopy)
				assertNotEquals(3,matrdd)
				assertEquals(matrdd,matrddcopy)
				
				val file2 = Files.createTempFile("matrice2", ".dat")
				val marray2 =  Util.createRandomMatrixInt(nbl+1, nbc+1, file2)
				val matrdd2 = makeFromFile(file2.toUri().toASCIIString(), nb_partitions, sc)
				
				assertNotEquals(matrdd,matrdd2)
				
				
				val file3 = Files.createTempFile("matrice3", ".dat")
				val marray3 =  Util.createRandomMatrixInt(nbl, nbc, file3)
				val matrdd3 = makeFromFile(file3.toUri().toASCIIString(), nb_partitions, sc)
				
				assertNotEquals(matrdd,matrdd3)
				
				file3.toFile().delete()
				file2.toFile().delete()
				filecopy.toFile().delete()
				file.toFile().delete()
			}
		}
    
  }
}