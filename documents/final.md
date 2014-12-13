## Introduction
This project is attempting to address the problem of
[chiptune](https://en.wikipedia.org/wiki/Chiptune) music generation on
computers.  For someone unfamiliar with the tools used to create music on
computers, or the sound systems used by computers to create sound, writing
chiptune music is hard.  I hope that a DSL with clean syntax can make it easy
for someone to create chiptune music.

The domain here is chiptune music creation.  This domain is useful because
there are many people writing music of this kind, and there are people (myself
included) who would like to also write chiptune music.  If there was an easy way
to write chiptune music, more people could do it, which would be a good thing.
There are no DSLs that I am aware of in this domain, but my knowledge of this
domain is slim.

My language should be a simple way to create music.  A program in my language
contains definitions of notes, chords, and sections of notes and chords.  Notes
contain their tone and duration.  A user may also provide a tempo.  My language
is good for this domain because it is simple and yet allows a lot of
functionality.  In a few definitions a user may define a series of notes, a
section of a song, and a whole song.  Repetition is easy and simple to indicate.
Chords of any number of notes may be created.  A user can pick up the syntax of
my language quickly and be creating music within minutes.

# Language Design Details
A user writes programs by writing the notes they want to play in a text file. In
my DSL, all you can do is define things, use things you've defined in other
definitions, and play things.  This limited syntax means a new user can pick up
the entire language very quickly and began creating music immediately.  With a
general-purpose language they might have to learn a lot more before being able
to write music.  The basic computation my language performs is defining notes,
chords, and sections of notes and chords, and playing and reusing these
definitions on demand.

The only data structures in my DSL are variables.  You can define sections of
musical elements with variable names, and these can nest.  That's it.  There are
no control structures.  In the future I might want to add basic repeating
structures to indicate sections of a song that repeat, but currently there is
nothing.

The input to a DSL is just the list of commands to be run. They may be provided
to an interpreter or run from a file.  The output is playing the music directly.
Future versions of the language will hopefully allow pictorial output (like
sheet music) and production of music files as well.

Programs can go wrong if things that are not defined are referenced in a
command.  This will be detected at runtime.  Other errors that should exist but
do not are out-of-range notes and invalid definitions.  Most of these are
silently rejected by either my DSL or the MIDI backend, and this will be fixed
in future to be more vocal.  There is no tool support besides the error
checking.  Everything must be run through sbt.  In future, I would like to build
stand-alone executables.  I would like to add syntax highlighting support for
major editors for convenience, but there will be no other tool support.  This
DSL is intended to be used with editors or existing IDEs and nothing else.

I didn't invest much time in finding DSLs for this domain.  There is graphical
software for creating music, but it tends to be complicated and expensive from
what I've seen.  I don't think there's anything taking the same text-based
simple approach that I have here.

## Example computations
A user might want to play "Twinkle, Twinkle, Little Star".  They could run the
following commands in sequence or put them into a file and run that.
NOTE: If no time is provided, notes are considered to be quarter notes.

tempo 100
twinkle1 = C3 C3
twinkle2 = G3 G3
little = A3 A3
star  = G3-Half
howI = F3 F3
wonder = E3 E3
whatyou = D3 D3
are = C3-Half
all = twinkle1 twinkle2 little star howI wonder whatyou are
play all

## Language Implementation
My host language is Scala.  I chose this because I found a decent-looking MIDI
library for it (well, for Java) which was a requirement for any language I
chose.  I also had experience writing DSLs in this language.  Mine is mostly an
external DSL.  I figured this would make it easier to parse the syntax I wanted
to use without a lot of work to get it working.  I have a small internal DSL
that is just a bunch of convenience functions for the MIDI library.  The
external DSL parser uses these functions to simplify its code.

My back-end is the midi code in src/main/scala/midi.  This talks to the Scala
MIDI library and makes my life simpler.  It also allows design changes to the
MIDI library or what sound library I use without affecting anything else.  The
middle-end is the semantics functions, which take what was asked for in the
front-end and talk to the back-end code.  The front-end (and also the
middle-end, they're basically the same here) takes what the user says and parses
it, and either puts stuff in the dictionary of things the user has defined or
tells the back-end to play something.  This is all implemented in Scala.  I use
the MIDI library on the back-end and packrat parsers on the front-end.

Parsing works as follows.  The user inputs commands.  They are parsed.  The user
either defined something, set the tempo, or asked to play something.  If the
user asked to define something, any previously-defined elements of their request
are unpacked.  A new item is put in the dictionary of things that the code
maintains indicating that new item.  If something the user mentioned is not
already defined, an error is thrown.  If the user specifies raw notes or chords,
those are created and added to the dictionary definition.  If the user set the
tempo, the tempo used by the MIDI generator is changed directly.  If the user
asks to play something, the value of what they asked for is looked up in the
dictionary and is played by my back-end.  If it was not defined, an error is
thrown.

A dictionary is my only intermediate representation.  Because all my DSL has is
key-value pairs, a dictionary is the logical choice for an internal
representation and is the only thing I need.  My execution was described above
in the parsing section, as they're basically one and the same.  Playing uses the
back-end I defined and ultimately calls the MIDI library to play specific notes
in specific sequences.  Setting tempo changes the tempo used to determine how
long notes should be played - it's an artificial construct I added for
convenience and not something MIDI cares about.  Assignments just interface with
the dictionary and handle interpreting what the user asked for.

# Evaluation
My DSL is very DSL-y, I think.  There are only three things you can do.  You can
only create music with it.  There is nothing else you can do.  Even if I allow
some more complicated syntax (I'd like to be able to play sections at the same
time, like two hands on a piano, and I'd like to be able to repeat sections
easily), it would only allow you to play more music.  I don't see this
accidentally becoming a general-purpose language any time soon.

I'm very pleased with the back-end.  I can pretty easily make music directly in
Scala by defining Notes, Chords, and Sections, and the Java MIDI library plays
everything I create correctly.  I particularly like how well nesting worked out.
I defined Sections to include any musical element, including Sections.  With a
little fiddling, it worked out, and you can very quickly build up from notes to
whole sections of music.  The separation of back-end from front-end is also
useful.  If I suddenly wanted to produce music some other way, I could modify
all code in the back-end and the front-end wouldn't care, as long as I left the
functions the same.  I've considered dropping a level down and playing tones
directly in Java (using some sound library) instead of MIDI, and this would be
very easy to achieve.

As my reviewers suggested, I could use more building blocks.  Major and minor
scales would be useful.  Right now you have to build from bare notes, and -
though it isn't incredibly difficult to build up to larger pieces - it could be
made easier.  I'd like to add repeats, and I'd like to be able to play multiple
things at the same time.  Also, for some reason you're unable to define chords
that contain defined names for chords or notes.  I'm not sure why.  I'd like to
chase down this and other bugs like it to improve my language.

I've used some of the tools I originally claimed I would use.  I played with the
Java MIDI library while I was building the backend, and was able to recreate the
Song of Healing, from Majora's Mask.  I was able to transcribe some sheet music
and it was perfect.  I was also able to demo this backend and have a classmate
test it out.  They were mostly happy with the interface, but suggested making
the syntax simpler - which I intended to do with my external parsed DSL.

From my evaluations, I'm really happy with the backend, and fairly happy with
where I want to go with the syntax and parser.  I'll try to add some additional
elements to the parser (mostly more primitives to work with), but I think I'm
sticking fairly close to the original design.  Most of my feedback was in the
way of improvements that didn't require a lot of change to the original design.

If I continue to work on this in future, I'd like to do some more user testing.
I did a bit during the class, but not as much as I probably should have.  I'd
like to see if this DSL actually is as easy to use as it seems to be to me.  I
want to see how quickly someone can pick it up and what they can create with it.
I'd like them to help me find weak spots in the language.

I ran into some trouble trying to get music generation to work at first, because
the first example I found for working with the Java library was unnecessarily
complex.  I found a simpler example and based my work off of that instead.  I
tried to abstract away into lots of classes to make my work easier.

I had some trouble with how to define notes at first.  I wanted to use sealed
traits or classes for Tones (C4, C3, A3Flat, etc.) and Times (Quarter, Half,
Whole), and use case classes to define all values they could take, but that made
it hard to write functions that could play various types of musical elements.  I
eventually settled on making constructors for Notes and Chords that took
strings, and parsed those strings to turn them into the appropriate Notes and
Chords.

I had some trouble with the parser.  I couldn't get it to work without quotes at
first, and even after that it was a bit weak.  It's still really messy and
misses a bunch of edge cases.  I didn't really have to drop any features, but
there were some I didn't have time to get to.  I'd definitely like to work on
this again in the future.
