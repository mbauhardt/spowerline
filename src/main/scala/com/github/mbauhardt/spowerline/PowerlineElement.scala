package com.github.mbauhardt.spowerline


trait PowerlineElement {
  def inc(seg: Segment): PowerlineElement

  def element: (Segment, Separator)

  def isEmpty: Boolean
}

object Empty extends PowerlineElement {
  def inc(seg: Segment): PowerlineElement = NonEmpty(seg, DefaultSeparator().copy(fg = seg.bg), Empty)

  def element: (Segment, Separator) = throw new NoSuchElementException("Empty element does not have any segment")

  def isEmpty = true
}

case class NonEmpty(segment: Segment, separator: Separator, next: PowerlineElement) extends PowerlineElement {
  override def inc(seg: Segment): PowerlineElement = {
    //`inc` is walking through the whole tree until `Empty` is there. So we can validate seg.id easily.
    if (segment.group == seg.group && segment.id == seg.id) throw new IllegalArgumentException("Duplicate segment detected: " + seg)
    NonEmpty(segment, separator.copy(bg = seg.bg), next.inc(seg))
  }

  override def element: (Segment, Separator) = (segment, separator)

  override def isEmpty = false
}
