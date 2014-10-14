package com.github.mbauhardt.spowerline

trait Powerline {
  def head: PowerlineElement

  def tail: Powerline

  def isEmpty: Boolean

  def foldLeft[B](z: B)(f: (B, PowerlineElement) => B): B

  def foldRight[B](z: B)(f: (PowerlineElement, B) => B): B
}

object Nil extends Powerline {
  def head: PowerlineElement = throw new NoSuchElementException("Empty powerline has no head")

  def tail: Powerline = throw new NoSuchElementException("Empty powerline has no tail")

  def isEmpty: Boolean = true

  def foldLeft[B](z: B)(f: (B, PowerlineElement) => B): B = z

  def foldRight[B](z: B)(f: (PowerlineElement, B) => B): B = z
}

case class NonEmptyPowerline(val head: PowerlineElement, val tail: Powerline) extends Powerline {
  override def isEmpty: Boolean = false

  override def foldLeft[B](z: B)(f: (B, PowerlineElement) => B): B = {
    var acc = z
    var these: Powerline = this
    while (!these.isEmpty) {
      acc = f(acc, these.head)
      these = these.tail
    }
    acc
  }

  def foldRight[B](z: B)(f: (PowerlineElement, B) => B): B = f(head, tail.foldRight(z)(f))
}

object Powerline {
  def apply(pe: PowerlineElement): Powerline = {
    pe match {
      case Empty => Nil
      case i: NonEmpty => NonEmptyPowerline(i, apply(i.next))
    }
  }
}
