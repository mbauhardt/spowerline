package com.github.mbauhardt.spowerline

import java.util.NoSuchElementException

object powerline extends App {


  type Segment = String
  type Sep = String



  trait PowerlineElement {
    def add(seg: Segment): PowerlineElement

    def element: (Segment, Sep)

    def toPowerline: Powerline

  }

  class Empty extends PowerlineElement {
    val defaultSeparator: Sep = "$(echo -e \"\\xE2\\xAE\\x80\")"
    override def add(seg: Segment): PowerlineElement = new NonEmpty(seg, defaultSeparator, new Empty)

    override def element: (Segment, Sep )= throw new NoSuchElementException("Empty element does not have any segment")

    override def toPowerline: Powerline = Nil
  }

  class NonEmpty(segment: Segment, separator: Sep, next: PowerlineElement) extends PowerlineElement {
    override def add(seg: Segment): PowerlineElement = new NonEmpty(segment, separator, next.add(seg))

    override def element: (Segment, Sep )= (segment, separator)

    override def toPowerline: Powerline = new NonEmptyPowerline(this, next.toPowerline)
  }

  trait Powerline {
    def head: PowerlineElement

    def tail: Powerline

    def isEmpty: Boolean

    def toString(f: PowerlineElement => String): String

  }

  object Nil extends Powerline {
    override def head: PowerlineElement = throw new NoSuchElementException("Empty powerline has no head")

    override def tail: Powerline = throw new NoSuchElementException("Empty powerline has no tail")

    override def isEmpty: Boolean = true

    override def toString(f: (PowerlineElement) => String): String = throw new NoSuchElementException("Empty powerline has no head or tail")
  }

  class NonEmptyPowerline(val head: PowerlineElement, val tail: Powerline) extends Powerline {
    override def isEmpty: Boolean = false

    override def toString(f: (PowerlineElement) => String): String = {
      if (!tail.isEmpty) f(head) + tail.toString(f)
      else f(head)
    }
  }


  def main() = {
    val pwd = "%~"

    val lastExitStatus = "%(?..%?)"

    val e = new Empty().add(lastExitStatus).add(pwd)
    val p = e.toPowerline
    println(p.toString(pe => pe.element._1 + pe.element._2))
  }

  main

}
