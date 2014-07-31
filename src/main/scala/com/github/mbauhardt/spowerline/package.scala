package com.github.mbauhardt

package object spowerline {

  case class Powerline(segments: Seq[Segment])

  case class Segment(content: String, fgColor: String = null, bgColor: String = null)

  case class SegmentSeparator(content: String, fgColor: String = null, bgColor: String = null)

  type Command = String
}
