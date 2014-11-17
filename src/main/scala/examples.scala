/*
 * Andrew Michaud
 * Examples of songs in this DSL, in Scala code.
 *
 *
 */

package com.andrewmichaud.midi.examples

import com.andrewmichaud.midisugar._
import com.andrewmichaud.midi.note._

// Song of healing
abstract class Songs

case object SongOfHealing {
  val FirstTwo = Section(Array(Note("B3"), Note("A3")))
  val Afirst = Section(Array(FirstTwo, Note("F3")))
  val Aend = Section(Array(FirstTwo, Note("E3", "Eighth"), Note("D3", "Eighth"),
                       Note("E3", "DotHalf")))

  val A = Section(Array(Afirst, Afirst, Aend))

  val NextThree = Section(Array(Note("F3"), Note("C3"), Note("B2")))
  val NextFour = Section(Array(Note("F3"), Note("E3"), Note("B2", "Eighth"), Note("A2", "Eighth"),
                              Note("B2", "DotHalf")))

  val Mid = Section(Array(NextThree, NextThree, NextFour))

  val Transition = Section(Array(Note("F3"), Note("E3"), Chord(Array(Note("B3"), Note("E3"))),
                               Chord(Array(Note("E3", "DotHalf"), Note("G3", "DotHalf")))))

  val B = Section(Array(NextThree, NextThree, Transition))

  val CfirstChord = Chord(Array(Note("A3"), Note("F3")))
  val CsecondChord = Chord(Array(Note("D4"), Note("A3")))
  val CthirdChord = Chord(Array(Note("G3"), Note("E3")))

  val Cfirst = Section(Array.fill(3)(CfirstChord))
  val Csecond = Section(Array.fill(3)(CsecondChord))
  val Cthird = Section(Array.fill(3)(CthirdChord))

  val Cend = Section(Array(Chord(Array(Note("C4"), Note("E3"))),
                         Chord(Array(Note("G3", "Half"), Note("E3", "Half")))))

  val C = Section(Array(Cfirst, Csecond, Cthird, Cend))

  val DfirstChord = Chord(Array(Note("F3"), Note("D3")))
  val DsecondChord = Chord(Array(Note("B3F"), Note("F3")))
  val Dfirst = Section(Array.fill(3)(DfirstChord))
  val Dsecond = Section(Array.fill(3)(DsecondChord))

  val Dend = Section(Array(Chord(Array(Note("E3"), Note("C3"))), Chord(Array(Note("C3"), Note("D3"))),
                         Chord(Array(Note("A3"), Note("C3"))),
                         Chord(Array(Note("E3", "DotHalf"), Note("C3", "DotHalf")))))

  val D = Section(Array(Dfirst, Dsecond, Dend))

  val Efirst = Section(Array(Chord(Array(Note("F3"), Note("D3"))),
                           Chord(Array(Note("G3"), Note("D3"))),
                           Chord(Array(Note("A3"), Note("D3")))
                          )
                    )

  val Esecond = Section(Array(Chord(Array(Note("B3F"), Note("F3"))),
                            Chord(Array(Note("C4"), Note("F3"))),
                            Chord(Array(Note("D4"), Note("F3")))
                           )
                     )

  val Ethird = Section(Array(Chord(Array(Note("A3"), Note("D3"))),
                           Chord(Array(Note("B3"), Note("D3"))),
                           Chord(Array(Note("D4"), Note("D3")))
                          )
                    )

  val Eend = Chord(Array(Note("E3", "DotHalf"), Note("E4", "DotHalf")))

  val E = Section(Array(Efirst, Esecond, Ethird, Eend))

  val Song = Section(Array(A, A,  B, C, D, C, E, A, A, B, C, D, C, E, A))
}
