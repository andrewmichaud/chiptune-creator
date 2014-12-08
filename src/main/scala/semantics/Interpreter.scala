package com.andrewmichaud.chiptune

import scala.collection.mutable.Map

import com.andrewmichaud.midi.note._
import com.andrewmichaud.midi.sugar._

import com.andrewmichaud.chiptune.ir._

package object semantics {

  // Tempo. Defaults to 120
  var tempo = 120

  // Table of things we know about.
  var table:Map[String,List[Element]] = Map()

  // Prefill all notes and other useful things.
  // We'll give the notes a value but it's not one that we'll end up using.
  table = table ++ (List(
    "D2", "D2S", "E2F", "E2", "F2", "F2S", "G2F", "G2", "G2S", "A2F", "A2", "A2S", "B2F", "B2",
    "C3", "C3S", "D3F", "D3", "D3S", "E3F", "E3", "F3", "F3S", "G3F", "G3", "G3S", "A3F", "A3", "A3S", "B3F",
    "B3", "C4", "C4S", "D4F", "D4", "D4S", "E4F", "E4", "F4", "F4S", "G4F", "G4", "G4S", "A4F", "A4", "A4S",
    "B4F", "B4", "C5", "C5S", "D5F", "D5", "E5F", "D5S", "E5", "F5"
  ) map ((x: String) => (x, List(Note(x)))) toMap)

  def eval(ast: AST):Any = ast match {
    case Let(label, value) => {

      // Create new array for the value.
      var newValue = scala.collection.mutable.ListBuffer[Element]()

      // Strip whitespace
      val tlabel = label.trim

      // Get tokens from value string.
      var tokens = value.trim.split("""\s""").toList
      println(s"Tokens: $tokens \n")

      if (tokens.length < 1) {
        return "Please provide a value to assign."
      }

      for (token <- tokens) {
          println(s"one token is $token")

        // Tokens must be previously defined to be valid OR a valid primitive.
        if (!(table contains token)) {

          // If it isn't in the table, it might be composed of primitives.  It could be a note or
          // chord.
          // Chords must be separated by |, so handle that first.
          val pieces = token.split("""[|]""")

          println("Notes in token:")
          for (piece <- pieces) {
            println(s" note: $piece")
          }

          // This is a single note.
          if (pieces.length < 2) {

            // Create note.
            val notepieces = token.split("""[\W_]""")

            if (table contains notepieces(0)) {
              newValue += Note(notepieces(0), notepieces(1))
            } else {
              return s"${notepieces(0)} is not in the table and $token is invalid."
            }

          // This is a chord.
          } else {

            // Create all notes in chord, if they are valid.
            var notes = scala.collection.mutable.ListBuffer[Note]()
            for (piece <- pieces) {
              val notepieces = piece.split("""[\W_]""")

              if (table contains notepieces(0)) {
                notes += Note(notepieces(0), notepieces(1))

              } else {
                return s"${notepieces(0)} is not in the table, so $piece is invalid and so is $token"
              }
            }

            // Stitch notes into chord.
            val chord = Chord(notes.toList)

            newValue += chord
          }

        } else {

          // If token is defined, prepend to list.  All values are defined backwards because prepending is fast.
          val tokenVal = table get token get

          for (element <- tokenVal) {
            newValue += element
          }
        }
      }

      // If we got here, all tokens were valid.  Add this new value into our map.
      table = table + (label -> newValue.toList)
      return s"Set $label to $newValue."
    }

    case Play(label) => {

      // TODO clean this up.
      // And actually let the user set tempo.
      println("MIDI playing begin")
      var gen = new Sugar

      try {
        // Check if label actually exists.
        if (table contains label) {

          // Open synthesizer and get channels.
          gen.synth.open()
          gen.setTempo(tempo)

          // Synthesize elements.
          val song = (table get label get)
          for (thing <- song) {
            gen.play(thing)
          }

          return "MIDI playing complete"

        } else {
          // TODO throw an error probably.
          return "Label doesn't exist!"
        }

      } catch {
        case e: Exception => println("Exception: " + e.printStackTrace())
        return "MIDI playing failed"
      }
    }

    case Tempo(value) => {

      // Set tempo.
      // TODO bounds check?
      try {
        tempo = value.toInt
        return s"Tempo set to $value"
      } catch {
        case e:Exception => {
          return s"Failed to set tempo, $value invalid tempo."
        }
      }
    }
  }
}
