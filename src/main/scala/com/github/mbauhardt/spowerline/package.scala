package com.github.mbauhardt

package object spowerline {
  case class Powerline(segments: Seq[Segment], segmentSeparator: String)
  case class Segment (content: String, fgColor: String=null, bgColor: String=null)
  type Command = String
}
