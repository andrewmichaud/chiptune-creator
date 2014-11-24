package com.andrewmichaud.chiptune.parser

/**
 * Andrew Michaud
 *
 * 11/24/14
 *
 * Parser for chiptune creator.
 */

import scala.util.parsing.combinator._
import com.andrewmichaud.chiptune.ir._

object ChipParser extends JavaTokenParsers with PackratParsers {

  // Parsing interface
  def apply(s: String): ParseResult[AST] = parseAll(expr, s)

  // Expressions
  lazy val expr: PackratParser[Expr] =
    (   stringLiteral~"="~stringLiteral ^^ {case label~"="~value => label is value} )
}
