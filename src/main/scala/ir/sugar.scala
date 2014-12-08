package com.andrewmichaud.chiptune

/**
 * Andrew Michaud
 *
 * 11/24/14
 *
 * Some useful syntactic sugar for the parser.
 */

import scala.language.implicitConversions

package object ir {

  implicit class ExprBuilder(val label: String) {
    def is(value: String) = Let(label, value)
    def play = Play(label)
  }
}
