package com.github.mbauhardt.spowerline


trait PowerlineElement {
  def add(seg: Segment): PowerlineElement

  def element: (Segment, Separator)

  def isEmpty: Boolean

  def toPowerline: Powerline
}

class Empty extends PowerlineElement {
  override def add(seg: Segment): PowerlineElement = new NonEmpty(seg, defaultSeparator, new Empty)

  override def element: (Segment, Separator) = throw new NoSuchElementException("Empty element does not have any segment")

  override def isEmpty = true

  override def toPowerline: Powerline = Nil
}

class NonEmpty(segment: Segment, separator: Separator, next: PowerlineElement) extends PowerlineElement {
  override def add(seg: Segment): PowerlineElement = new NonEmpty(segment, separator, next.add(seg))

  override def element: (Segment, Separator) = (segment, separator)

  override def isEmpty = false

  override def toPowerline: Powerline = new NonEmptyPowerline(this, next.toPowerline)
}