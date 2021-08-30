package app

import scala.io.StdIn.readLine
import scala.collection.mutable.{Map => MMap}
sealed trait Triangle
final case class TriangleNode(value: Long, left: Triangle, right: Triangle) extends Triangle
final object TriangleEnd extends Triangle

object Triangle {

  def apply(input: Seq[Seq[Long]]): Triangle = {
    def loop(lin: Int, col: Int, leftValue: Option[Triangle]): Triangle =
      if (lin < input.length && col < input(lin).length) {
        val left = leftValue.getOrElse(loop(lin + 1, col, None))
        val right = left match {
          case TriangleNode(_, _, rightValue) => 
            loop(lin + 1, col + 1, Some(rightValue))
          case TriangleEnd => loop(lin + 1, col + 1, None)
        }
        TriangleNode(input(lin)(col), left, right)
      }
      else TriangleEnd

    loop(0, 0, None)
  }

  def findMinPath(root: TriangleNode): List[Long] = {
    // that mutable map does not break functionnal programming principles because the function is still pure
    val computed: MMap[TriangleNode, List[Long]] = MMap.empty
    
    def loop(triangle: TriangleNode, parents: List[Long], bestSoFar: Long = Long.MaxValue): List[Long] = {
      if (computed.contains(triangle)) parents ++ computed(triangle)
      else if (parents.sum + triangle.value > bestSoFar) parents :+ triangle.value
      else (triangle.left, triangle.right) match {
        case (left @ TriangleNode(_, _, _), right @ TriangleNode(_, _, _)) =>
          val l, lp = loop(left, parents :+ triangle.value)
          val r, rp = loop(right, parents :+ triangle.value, lp.sum)
          val res = if (lp.sum < rp.sum) lp else rp
          computed.addOne((triangle, res.drop(parents.length)))
          res
        case _ =>
          parents :+ triangle.value
      }
    }
    loop(root, List())
  }

}

object MinTrianglePath extends App {

  // 1- read from stdin - I made the assumption that the input is always correct
  val input: Seq[Seq[Long]] = Iterator.continually(readLine)
    .takeWhile(_ != null)
    .map(s => s.split(" ").map(_.toLong).toList)
    .toList

  // 2- parse as a triangle
  val triangle = Triangle(input)

  triangle match {
    case t @ TriangleNode(_, _, _) => 
      // 3- find the minimal path
      val path = Triangle.findMinPath(t)
      // 4- print the minimal path to stdout
      println(path.mkString(" + ") + s" = ${path.sum}")
    case _ =>
      println("Input error")
  }

}
