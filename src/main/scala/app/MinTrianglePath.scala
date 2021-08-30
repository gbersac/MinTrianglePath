package app

import scala.io.StdIn.readLine

sealed trait Tree
final case class TreeNode(value: Int, left: Tree, right: Tree) extends Tree
final object TreeEnd extends Tree

object Tree {
  // I made the assumption that the input is always correct and there is always at least one line
  // TODO optimization: each node is instantiated twice (once by parent left and once by parent right)
  def apply(input: Seq[Seq[Int]]): Tree = {
    def loop(lin: Int, col: Int): Tree =
      if (lin < input.length && col < input(lin).length)
        TreeNode(input(lin)(col),
          loop(lin + 1, col), 
          loop(lin + 1, col + 1)
        )
      else TreeEnd

    loop(0, 0)
  }

}

object MinTrianglePath extends App {

  // 1- read from stdin
  val input = Iterator.continually(readLine)
    .takeWhile(_ != null)
    .mkString("\n")
    .split("\n")
    .map(s => s.split(" ").map(_.toInt))
    .toList

  // 2- parse as a binary tree
  // val tree = Tree(input)

  // 3- find the minimal path
  // 4- print the minimal path to stdout

}
