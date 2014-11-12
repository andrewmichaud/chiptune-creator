/**
 *
 * Test file to play various midi songs.
 *
 * author Andrew Michaud
 * date 11/12/14
 */

import com.andrewmichaud.midisugar._
import com.andrewmichaud.midi.note._

object MIDITester {
  def main(args: Array[String]) {
    println("MIDI playing begin")
    var gen = new MIDISugar
    try {
      // Open synthesizer and get channels.
      gen.synth.open()

      // gen.play notes.
      // Song of storms?
      gen.playNote(D2, factor=2)
      gen.playChord(Array(A3, F3))
      gen.playChord(Array(A3, F3))

      gen.playNote(E2, factor=2)
      gen.playNote(E3)
      gen.playChord(Array(G3, B3), factor=1.5)

      gen.playNote(F2, factor=2)
      gen.playChord(Array(A3, C4))
      gen.playChord(Array(A3, C4))

      gen.playNote(E2, factor=2)
      gen.playNote(E3)
      gen.playChord(Array(F3, B3), factor=1.5)

      //gen.playChord(

      println("MIDI playing complete")

    } catch {
      case e: Exception => println("Exception: " + e.printStackTrace())
      println("MIDI playing failed")
    }
  }
}
