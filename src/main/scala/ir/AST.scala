package com.andrewmichaud.chiptune.ir

/**
 * Andrew Michaud
 *
 * 11/24/14
 */

sealed abstract class AST
sealed abstract class Expr extends AST

case class Let(label: String, value: String) extends Expr
case class Play(label: String) extends Expr
case class Tempo(value: String) extends Expr
