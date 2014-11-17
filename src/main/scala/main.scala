/**
 *
 * Test file to play various midi songs.
 *
 * author Andrew Michaud
 * date 11/12/14
 */

import com.andrewmichaud.midisugar._
import com.andrewmichaud.midi.note._
import com.andrewmichaud.midi.examples._

object MIDITester {
  def main(args: Array[String]) {
    println("MIDI playing begin")
    var gen = new MIDISugar
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
