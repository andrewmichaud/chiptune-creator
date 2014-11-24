package com.andrewmichaud.chiptune

/**
 * Andrew Michaud
 *
 * 11/24/14
 *
 * Main eval loop, where all the magic happens.
 *
 */

import scala.tools.nsc.EvalLoop
import com.andrewmichaud.chiptune.parser.ChipParser
import com.andrewmichaud.chiptune.semantics.eval

object ChiptuneCreator extends EvalLoop with App {
  override def prompt = "> "

  loop { line =>
    ChipParser(line) match {
      case ChipParser.Success(t,_) => println(eval(t))
      case e: ChipParser.NoSuccess => println(e)
    }
  }
}
