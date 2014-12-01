# Preliminary evaluation
**What works well? What are you particularly pleased with?**

The backend works very well.  I can pretty easily make music directly in Scala
by defining Notes, Chords, and Sections, and the Java MIDI library plays
everything I create correctly.  I particularly like how well nesting worked out.
I defined Sections to include any musical element, including Sections.  With a
little fiddling, it worked out, and you can very quickly build up from notes to
whole sections of music.  I'm really happy with it.


**What could be improved? For example, how could the user's experience be
better? How might your implementation be simpler or more cohesive?**

As my reviewers suggested, I could use more building blocks.  Major and minor
scales would be useful.  Right now you have to build from bare notes, and -
though it isn't incredibly difficult to build up to larger pieces - it could be
made easier.

I still need to work on the parser.  It's almost working, but I need to figure
out how to make the parser not require quotations around your variables.  Right
now it's necessary and it's breaking the parser.  I think if I can figure that
out everything will come together and I'll have a working parser for the syntax
I originally wanted.


**Re-visit your evaluation plan from the beginning of the project. Which tools
have you used to evaluate the quality of your design? What have you learned from
these evaluations? Have you made any significant changes as a result of these
tools, the critiques, or user tests?**

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


**Where did you run into trouble and why? For example, did you come up with some
syntax that you found difficult to implement, given your host language choice?
Did you want to support multiple features, but you had trouble getting them to
play well together?**

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

I'm currently having some trouble with the parser, like I mentioned.  I haven't
found anything incredibly difficult due to host language choice.  Parsing
problems aside, my syntax is pretty simple and easy to parse.  There are a lot
of edge cases and my implementation is going to be imperfect, but it will work.
I'll be able to parse music like I said I would, though I don't think my error
handling and checks for valid syntax will work as well as I'd hoped.


**What's left to accomplish before the end of the project?**

I want to get the parser working, and try making some music with it.  I'll be
mostly done, then.  I said I would have a visualizer working as well, but I
think that might slip away, depending on how long straightening out the parser
will take.  It's definitely something I'll want to look at if I continue this
project after the semester.
