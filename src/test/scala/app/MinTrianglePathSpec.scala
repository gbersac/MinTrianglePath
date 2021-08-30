package app

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ParseSpec extends AnyFlatSpec with Matchers {
  "The triangle parser" should "parse as expected" in {
    val l = List(
      List(7),
      List(6, 3),
      List(3, 8, 5),
      List(11, 2, 10, 9),
    )
    Triangle(l) shouldEqual TriangleNode(7,
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
}
