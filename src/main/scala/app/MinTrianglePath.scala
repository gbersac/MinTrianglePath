package app

import scala.io.StdIn.readLine
import scala.collection.mutable.{Map => MMap}
sealed trait Triangle
final case class TriangleNode(value: Long, left: Triangle, right: Triangle) extends Triangle
final object TriangleEnd extends Triangle

object Triangle {

  def apply(input: Seq[Seq[Long]]): Triangle = {
    /** @param leftValue Optim0 leftValue is to make sure that no node will be created twice
      * (once by parent left and once by parent right)
      * this optimization is required fot Optim1 to work
      */
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
    // those mutable variables do not break functionnal programming principles because the function is still pure

    /** Optim1: remember the min path when it has been computed so that the next time the algo fall on that node,
      * it won't have to compute it again.
      * Without this optimization, the min path would have been computed twice for almost each node.
      *
      * For the file `testData/25.txt`:
      * -> Before this optimization, the JVM would have thrown an error after a few minute of computation.
      * -> After this optimization, the algo find the solution in 5.5 seconds
      */
    val minPathByNode: MMap[TriangleNode, List[Long]] = MMap.empty

    /** Optim2: save the sum of the elements of the best solution found so far.
     * If the algo test a new path and the sum of all those nodes exceed the best solution found so far,
     * it is not worth carrying on. The algo will stop testing this path earlier.
     *
     * For the file `testData/28.txt`:
     * -> With the optimization Optim1, but not Optim2, the algo would find the solution in 45s
     * -> With the optimizations Optim1 and Optim2, the algo would find the solution in 24s
      */
    var bestSoFar: Long = Long.MaxValue
    val nonMinPath = List(Long.MaxValue)

    def loop(triangle: TriangleNode, parents: List[Long]): List[Long] = {
      // Optim1: retrieve saved solution instead of computing it again
      if (minPathByNode.contains(triangle)) parents ++ minPathByNode(triangle)
      else if (parents.sum + triangle.value >= bestSoFar) nonMinPath // Optim2: abort if tested path is not interesting
      else (triangle.left, triangle.right) match {
        case (left @ TriangleNode(_, _, _), right @ TriangleNode(_, _, _)) =>
          val lp = loop(left, parents :+ triangle.value)
          val rp = loop(right, parents :+ triangle.value)
          val res = if (lp.sum < rp.sum) lp else rp
          if (res != nonMinPath)
            minPathByNode.addOne((triangle, res.drop(parents.length))) // Optim1: save new solution for this node
          res
        case _ =>
          if (parents.sum + triangle.value < bestSoFar) {
            bestSoFar = parents.sum + triangle.value // Optim2: save the best solution so far
            parents :+ triangle.value
          }
          else nonMinPath
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
      println("Minimal path is: " + path.mkString(" + ") + s" = ${path.sum}")
    case _ =>
      println("Input error")
  }

}
