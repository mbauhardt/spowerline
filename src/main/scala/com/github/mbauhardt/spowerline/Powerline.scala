package com.github.mbauhardt.spowerline

trait Powerline {
  def head: PowerlineElement

  def tail: Powerline

  def isEmpty: Boolean

  def foldLeft[B](z: B)(f: (B, PowerlineElement) => B): B

  def foldRight[B](z: B)(f: (B, PowerlineElement) => B): B


}

object Nil extends Powerline {
  override def head: PowerlineElement = throw new NoSuchElementException("Empty powerline has no head")

  override def tail: Powerline = throw new NoSuchElementException("Empty powerline has no tail")

  override def isEmpty: Boolean = true

  override def foldLeft[B](z: B)(f: (B, PowerlineElement) => B): B = z

  def foldRight[B](z: B)(f: (B, PowerlineElement) => B): B = z
}

class NonEmptyPowerline(val head: PowerlineElement, val tail: Powerline) extends Powerline {
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

  def foldRight[B](z: B)(f: (B, PowerlineElement) => B): B = f(tail.foldRight(z)(f), head)

}