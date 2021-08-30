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

  /*
  def findMinPath(triangle: TriangleNode): List[Int] = {
    if (Triangle.left != TriangleEnd && Triangle.right != TriangleEnd) {
      val l, lp = findMinPath()

    }
    else List(Triangle.value)
  }*/

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

  // 3- find the minimal path

  // 4- print the minimal path to stdout

}
