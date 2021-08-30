package app

import scala.io.StdIn.readLine

sealed trait Triangle
final case class TriangleNode(value: Int, left: Triangle, right: Triangle) extends Triangle
final object TriangleEnd extends Triangle

object Triangle {
  /**
    * TODO optimization: each node is instantiated twice (once by parent left and once by parent right)
    * @return the head of the corresponding triangle
    */
  def apply(input: Seq[Seq[Int]]): Triangle = {
    def loop(lin: Int, col: Int): Triangle =
      if (lin < input.length && col < input(lin).length)
        TriangleNode(input(lin)(col),
          loop(lin + 1, col), 
          loop(lin + 1, col + 1)
        )
      else TriangleEnd

    loop(0, 0)
  }

  def findMinPath(triangle: TriangleNode): List[Int] = 
    (triangle.left, triangle.right) match {
      case (left @ TriangleNode(_, _, _), right @ TriangleNode(_, _, _)) =>
        val l, lp = findMinPath(left)
        val r, rp = findMinPath(right)
        if (lp.sum < rp.sum) triangle.value :: lp
        else triangle.value :: rp
      case _ =>
        List(triangle.value)
    }

}

object MinTrianglePath extends App {

  // 1- read from stdin - I made the assumption that the input is always correct
  val input: Seq[Seq[Int]] = Iterator.continually(readLine)
    .takeWhile(_ != null)
    .mkString("\n")
    .split("\n")
    .map(s => s.split(" ").map(_.toInt).toList)
    .toList

  // 2- parse as a binary Triangle
  val triangle = Triangle(input)

  triangle match {
    case t @ TriangleNode(_, _, _) => 
      // 3- find the minimal path
      val path = Triangle.findMinPath(t)
      // 4- print the minimal path to stdout
      path.mkString(" + ") + s" = ${path.sum}"
    case _ =>
      println("Input error")
  }

}
