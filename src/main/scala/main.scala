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
      gen.setTempo(240)

      // gen.play notes.
      // Song of storms?
      gen.playNote(D2, kind=DotHalf)
      gen.playChord(Array(A3, F3), kind=Quarter)
      gen.playChord(Array(A3, F3), kind=Quarter)

      gen.playNote(E2, kind=DotHalf)
      gen.playNote(E3, kind=Eighth)
      gen.playChord(Array(G3, B3), kind=Half)

      gen.playNote(F2, kind=DotHalf)
      gen.playChord(Array(A3, C4), kind=Quarter)
      gen.playChord(Array(A3, C4), kind=Quarter)

      gen.playNote(E2, kind=DotHalf)
      gen.playNote(E3, kind=Eighth)
      gen.playChord(Array(F3, B3), kind=Half)

      //gen.playChord(

      println("MIDI playing complete")

    } catch {
      case e: Exception => println("Exception: " + e.printStackTrace())
      println("MIDI playing failed")
    }
  }
}
