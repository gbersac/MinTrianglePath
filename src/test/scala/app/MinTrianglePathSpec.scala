package app

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ParseSpec extends AnyFlatSpec with Matchers {
  "The tree parser" should "parse as expected" in {
    val l = List(
      List(7),
      List(6, 3),
      List(3, 8, 5),
      List(11, 2, 10, 9),
    )
    Tree(l) shouldEqual TreeNode(7,
      TreeNode(6,
        TreeNode(3, TreeNode(11, TreeEnd, TreeEnd), TreeNode(2, TreeEnd, TreeEnd)),
        TreeNode(8, TreeNode(2, TreeEnd, TreeEnd), TreeNode(10, TreeEnd, TreeEnd))
      ),
      TreeNode(3,
        TreeNode(8, TreeNode(2, TreeEnd, TreeEnd), TreeNode(10, TreeEnd, TreeEnd)),
        TreeNode(5, TreeNode(10, TreeEnd, TreeEnd), TreeNode(9, TreeEnd, TreeEnd))
      )
    )
  }
}
