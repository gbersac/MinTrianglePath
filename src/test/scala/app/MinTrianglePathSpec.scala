package app

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ParseSpec extends AnyFlatSpec with Matchers {

  val exampleList = List(
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

  "Triangle.findMinPath" should "parse as expected" in {
    Triangle(exampleList) match {
      case t @ TriangleNode(_, _, _) => 
        Triangle.findMinPath(t) shouldEqual List(7, 6, 3, 2)
      case _ =>
        fail("Parsing error")
    }
    
  }

}
