package com.andrewmichaud.chiptune

import com.andrewmichaud.chiptune.ir._

import com.andrewmichaud.midi.note._
import com.andrewmichaud.midi.sugar._

package object semantics {
  def eval(ast: AST): () = ast match {
    case Let(label, value) => {

    }

    case play(label) => {
      // TODO clean this up.
      // And actually let the user set tempo.
      println("MIDI playing begin")
      var gen = new Sugar
      try {
        // Open synthesizer and get channels.
        gen.synth.open()
        gen.setTempo(102)

        gen.play(SongOfHealing.Song)

        println("MIDI playing complete")

      } catch {
        case e: Exception => println("Exception: " + e.printStackTrace())
        println("MIDI playing failed")
      }
    }
  }
