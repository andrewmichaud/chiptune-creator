package com.andrewmichaud.chiptune

import scala.collection.mutable.Map

import com.andrewmichaud.midi.note._
import com.andrewmichaud.midi.sugar._

import com.andrewmichaud.chiptune.ir._

package object semantics {

  // Tempo. Defaults to 120
  var tempo = 120

  // Table of things we know about.
  var table: Map[String, List[Element]] = Map()

  // Prefill all notes and other useful things.
  // We'll give the notes a value but it's not one that we'll end up using.
  table = table ++ {
    val letters = List("A", "B", "C", "D", "E", "F", "G")
    val octaves = 0 to 9
    val accidentals = List("F", "\u266D", "\uD834\uDD2B", "S", "♯", "𝄪", "N", "♮", "")
    var notes = "rest" :: (for (l <- letters; o <- octaves; a <- accidentals) yield l + o + a)

    for (combo <- notes) {
      println(s"a combo is $combo")
    }
    notes map ((x: String) => (x, List(Note(x)))) toMap
  }

  def eval(ast: AST): Any = ast match {
    case Let(label, value) => {
      println(s"Value in Let is $value")

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

          // This is a single note or rest, or something previously defined.
          if (pieces.length < 2) {

            // Check if this thing exists.
            if (table contains token) {
              // Add existing token to list.
              val tokenVal = table get token get

              for (element <- tokenVal) {
                newValue += element
              }

            } else {
              // Otherwise, freshly create it.
              val notepieces = token.split("""[\W_^\.]""")
              println("notepieces in token:")
              for (piece <- notepieces) {
                println(s"notepiecs: $piece")
              }

              if (table contains notepieces(0)) {

                // Differentiate between notes with time specified and those without.
                if (notepieces.length <= 1) {
                  newValue += Note(notepieces(0))
                } else {
                  newValue += Note(notepieces(0), notepieces(1))
                }
              } else {
                return s"${notepieces(0)} is not in the table and $token is invalid."
              }
            }

          } else {

            // This is a chord.
            // Create all notes in chord, if they are valid.
            var notes = scala.collection.mutable.ListBuffer[Note]()
            for (piece <- pieces) {
              val notepieces = piece.split("""[\W_^\.]""")

              if (table contains notepieces(0)) {
                // Differentiate between notes with time specified and those without.
                if (notepieces.length <= 1) {
                  newValue += Note(notepieces(0))
                } else {
                  newValue += Note(notepieces(0), notepieces(1))
                }

              } else {
                return s"${notepieces(0)} is not in the table, so $piece is invalid and so is $token"
              }
            }

            // Stitch notes into chord.
            val chord = Chord(notes.toList)

            newValue += chord
          }

        } else {

          // Add exsting token to list.
          val tokenVal = table get token get

          for (element <- tokenVal) {
            newValue += element
          }
        }
      }

      // If we got here, all tokens were valid.  Add this new value into our map.
      table = table + (label -> newValue.toList)
      s"Set $label to $newValue."
    }

    case Play(label) => {

      // TODO clean this up.
      // And actually let the user set tempo.
      println("MIDI playing begin")
      var gen = new Sugar

      try {
        // Check if label actually exists.
        if (!(table contains label)) {
          return s"Label $label doesn't exist!"
        }

        // Open synthesizer and get channels.
        gen.synth.open()
        gen.setTempo(tempo)

        gen.play(Section(table get label get), 0)

        return "MIDI playing complete"

      } catch {
        case e: Exception =>
          println("Exception: " + e.printStackTrace())
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
        case e: Exception => {
          return s"Failed to set tempo, $value invalid tempo."
        }
      }
    }
  }
}
