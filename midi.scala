/**
 *
 * Generating MIDI with Scala
 *
 * author Andrew Michaud
 * date 11/05/14
 */

import java.io._
import java.util._
import javax.sound.midi._

import sys.process._

object MIDIGen {
  // Data members
  var synth = MidiSystem.getSynthesizer()
  var channels = synth.getChannels()

  // Play a given note with given velocity and on the given channel.
  def playNote(note: Note = C3, velocity: Int = 60, channel: Int = 0, duration: Int = 200) {
    // TODO check range on inputs.

    channels(channel).noteOn(note.name, velocity)
    Thread.sleep(duration)
    channels(channel).noteOff(note.name)

  }

  // Incomplete note chart, for transcribing music.
  sealed trait Note { def name: Int }
  case object G2 extends Note { val name = 43 }
  case object G2S extends Note { val name = 44 }
  case object A2F extends Note { val name = 44 }
  case object A2 extends Note { val name = 45 }
  case object A2S extends Note { val name = 46 }
  case object B2F extends Note { val name = 46 }
  case object B2 extends Note { val name = 47 }
  case object C3 extends Note { val name = 48 }
  case object C3S extends Note { val name = 49 }
  case object D3F extends Note { val name = 49 }
  case object D3 extends Note { val name = 50 }
  case object D3S extends Note { val name = 51 }
  case object E3F extends Note { val name = 51 }
  case object E3 extends Note { val name = 52 }
  case object F3 extends Note { val name = 53 }
  case object F3S extends Note { val name = 54 }
  case object G3F extends Note { val name = 54 }
  case object G3 extends Note { val name = 55 }
  case object G3S extends Note { val name = 56 }
  case object A3F extends Note { val name = 56 }
  case object A3 extends Note { val name = 57 }
  case object A3S extends Note { val name = 58 }
  case object B3F extends Note { val name = 58 }
  case object B3 extends Note { val name = 59 }
  case object C4 extends Note { val name = 60 }
  case object C4S extends Note { val name = 61 }
  case object D4F extends Note { val name = 61 }
  case object D4 extends Note { val name = 62 }
  case object D4S extends Note { val name = 63 }
  case object E4F extends Note { val name = 63 }
  case object E4 extends Note { val name = 64 }
  case object F4 extends Note { val name = 65 }
  case object F4S extends Note { val name = 66 }
  case object G4F extends Note { val name = 66 }
  case object G4 extends Note { val name = 67 }
  case object G4S extends Note { val name = 68 }
  case object A4F extends Note { val name = 68 }
  case object A4 extends Note { val name = 69 }
  case object A4S extends Note { val name = 70 }
  case object B4F extends Note { val name = 70 }
  case object B4 extends Note { val name = 71 }
  case object C5 extends Note { val name = 72 }
  case object C5S extends Note { val name = 73 }
  case object D5F extends Note { val name = 73 }
  case object D5 extends Note { val name = 74 }
  case object E5F extends Note { val name = 75 }
  case object D5S extends Note { val name = 75 }
  case object E5 extends Note { val name = 76 }
  case object F5 extends Note { val name = 77 }

  def main(args: Array[String]) {
    println("midifile begin ")

    try {
      // Open synthesizer and get channels.
      synth.open()

      // Play notes.
      playNote(note = D3)
      playNote(note = C3)
      playNote(note = B2)
      playNote(note = A2)

    } catch {
      case e: Exception => println("Exception: " + e.printStackTrace())
    }
  }
}

