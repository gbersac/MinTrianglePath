package app

import scala.io.Source
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ParseSpec extends AnyFlatSpec with Matchers {

  val exampleList: List[List[Long]] = List(
    List(7),
    List(6, 3),
    List(3, 8, 5),
    List(11, 2, 10, 9),
  )

  "The triangle parser" should "parse as expected" in {
    Triangle(exampleList) shouldEqual TriangleNode(7,
      TriangleNode(6,
        TriangleNode(3, TriangleNode(11, TriangleEnd, TriangleEnd), TriangleNode(2, TriangleEnd, TriangleEnd)),
        TriangleNode(8, TriangleNode(2, TriangleEnd, TriangleEnd), TriangleNode(10, TriangleEnd, TriangleEnd))
      ),
      TriangleNode(3,
        TriangleNode(8, TriangleNode(2, TriangleEnd, TriangleEnd), TriangleNode(10, TriangleEnd, TriangleEnd)),
        TriangleNode(5, TriangleNode(10, TriangleEnd, TriangleEnd), TriangleNode(9, TriangleEnd, TriangleEnd))
      )
    )
  }

  "Triangle.findMinPath" should "work as expected" in {
    Triangle(exampleList) match {
      case t @ TriangleNode(_, _, _) => 
        Triangle.findMinPath(t) shouldEqual List(7, 6, 3, 2)
      case _ =>
        fail("Parsing error")
    }    
  }

  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println("elapsed time: " + ((t1 - t0) / 1000000) + "ms")
    result
  }

  def readFromFile(path: String) =
    Source.fromFile(path)
      .getLines
      .map(s => s.split(" ").map(_.toLong).toList)
      .toList

  "Triangle.findMinPath" should "work as expected even for a 15 line input" in {
    println("For a 15 line triangle")
    print("For parsing ")
    val input = time { readFromFile("testData/15.txt") }
    Triangle(input) match {
      case t @ TriangleNode(_, _, _) => 
        print("For solution ")
        val res = time { Triangle.findMinPath(t) }
        res shouldEqual List(74, 54, 80, 32, 23, 43, 51, 18, 31, 33, 25, 12, 18, 11, 35) // = 540
      case _ =>
        fail("Parsing error")
    }
  }

  "Triangle.findMinPath" should "work as expected even for a 25 line input" in {
    println("For a 25 line triangle")
    print("For parsing ")
    val input = time { readFromFile("testData/25.txt") }
    Triangle(input) match {
      case t @ TriangleNode(_, _, _) => 
        print("For solution ")
        val res = time { Triangle.findMinPath(t) }
        res shouldEqual List(
          74, 54, 80, 32, 23, 43, 51, 18, 31, 33, 25, 12, 18, 11, 35, 16, 12, 30, 14, 39, 77, 11, 79, 12, 30
        ) // = 860
      case _ =>
        fail("Parsing error")
    }
  }

}
