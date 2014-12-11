/**
 *
 * Synatx-sugary interface to the Scala MIDI library designed to make composition
 * easier.
 *
 * author Andrew Michaud
 * date 11/05/14
 */

import java.io._
import java.util._
import javax.sound.midi._

import sys.process._

import com.andrewmichaud.midi.note._

package com.andrewmichaud.midi.sugar {

  class Sugar {

    // Data members
    var synth = MidiSystem.getSynthesizer()
    var channels = synth.getChannels()
    var tempo: Double = (120.0 / 60.0 / 1000.0)

    def setTempo(t: Int) {
      tempo = (((1.0 * t) / 60.0) / 1000.0)
    }

    // Play something.
    def playmult(es: scala.collection.immutable.List[Element]) {
      // TODO check range on inputs.
      // TODO allow setable velocity?
      var velocity = 200
      var channel = 0

      for (e <- es) {
        e match {

          // Play a single note.
          case e:Note => play(e, channel)

          // Play a single chord.
          case e:Chord => play(e, channel)

          // Play a section, which is composed of other elements.
          case e:Section => play(e, channel)
        }

        channel += 1
      }
    }

    def play(e: Element, channel: Int) {
      var velocity = 200
        e match {
          // Play a single note.
          case e:Note => {
            channels(channel).noteOn(e.tone, velocity)
            Thread.sleep((e.time / tempo).toLong)
            channels(channel).noteOff(e.tone)
          }

          // Play a single chord.
          case e:Chord => {

            // Blatantly ignores multiple durations, should fix that.
            for (note <- e.notes) {
              channels(channel).noteOn(note.tone, velocity)
            }

            Thread.sleep((e.notes(0).time / tempo).toLong)

            for (note <- e.notes) {
              channels(channel).noteOff(note.tone)
            }
          }

          // Play a section, which is composed of other elements.
          case e:Section => {
            for (element <- e.elements) {
              element match {
                case element:Note => play(element, channel)
                case element:Chord => play(element, channel)
                case element:Section => play(element, channel)
                case _ => {}
              }
            }
          }
        }
      }
  }
}
