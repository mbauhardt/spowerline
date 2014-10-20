package com.github.mbauhardt.spowerline


trait PowerlineElement {
  def inc(seg: Segment): PowerlineElement

  def element: (Segment, Separator)

  def isEmpty: Boolean
}

object Empty extends PowerlineElement {
  def inc(seg: Segment): PowerlineElement = NonEmpty(seg, DefaultSeparator(), Empty)

  def element: (Segment, Separator) = throw new NoSuchElementException("Empty element does not have any segment")

  def isEmpty = true
}

case class NonEmpty(segment: Segment, separator: Separator, next: PowerlineElement) extends PowerlineElement {
  override def inc(seg: Segment): PowerlineElement = NonEmpty(segment, separator, next.inc(seg))

  override def element: (Segment, Separator) = (segment, separator)

  override def isEmpty = false
}
