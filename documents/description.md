# Project description and plan

## Motivation
This project is attempting to address the problem of
[chiptune](https://en.wikipedia.org/wiki/Chiptune) music generation on
computers.  For someone unfamiliar with the tools used to create music on
computers, or the sound systems used by computers to create sound, writing
chiptune music is hard.  I hope that a DSL with clean syntax can make it easy
for someone to create chiptune music.

## Language domain
The domain here is chiptune music creation.  This domain is useful because
there are many people writing music of this kind, and there are people (myself
included) who would like to also write chiptune music.  If there was an easy way
to write chiptune music, more people could do it, which would be a good thing.
There are no DSLs that I am aware of in this domain, but my knowledge of this
domain is slim.

## Language design
A simple interface for creating computer-generated music.  A program in my
language would contain notes (with some text representation), the key of the
music, durations for notes, and any other information governing how the music
should be played.  All information would be manually entered by the user
(perhaps with some defaults known to the language).  When a program is run, it
would either be turned into a visual representation, or into music.  The program
would not really take any input.  A program would be entirely composed of notes
and other musical elements.  There wouldn't need to be control structures,
though it might be nice to give labels to sections of music so they could be
repeated more easily.  I would need to add errors when the file could not parse
correctly, which would be either syntax or compile-time errors.  I don't think
there would be runtime errors.  I would tell the user what error occurred and
attempt to identify where parsing failed for the user.

## Example computations
A user might want to make her computer play the "Happy Birthday" song.  They
would open a new text file, and type all of the notes in the song.  If they
weren't sure if the notes were right, they could build the visual representation
of the song, and see if it was right.  They could also play the song while they
were working on it, and tune it to make it sound better.  Somewhere else in the
text file, they would specify the time signature or beats per minute of the
song.  When they were satisfied, they could build the visual representation
again, or build the actual music file, and play it.
