package com.github.mbauhardt.spowerline


trait PowerlineElement {
  def add(seg: Segment): PowerlineElement

  def element: (Segment, Separator)

  def isEmpty: Boolean
}

case class Empty() extends PowerlineElement {
  override def add(seg: Segment): PowerlineElement = NonEmpty(seg, defaultSeparator, Empty())

  override def element: (Segment, Separator) = throw new NoSuchElementException("Empty element does not have any segment")

  override def isEmpty = true
}

case class NonEmpty(segment: Segment, separator: Separator, next: PowerlineElement) extends PowerlineElement {
  override def add(seg: Segment): PowerlineElement = NonEmpty(segment, separator, next.add(seg))

  override def element: (Segment, Separator) = (segment, separator)

  override def isEmpty = false
}
