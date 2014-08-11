package com.github.mbauhardt

package object spowerline {

  case class Powerline(segments: Seq[Segment])

  case class Segment(content: Either[String, Executable], fgColor: String = "black", bgColor: String = "white", precondition: Option[Executable] = None)

  case class SegmentSeparator(content: Either[String, Executable], fgColor: String = "black", bgColor: String = "white")

  case class Executable(command: Command) {
    def commanWithBackticks = "`" + command + "`"
  }

  type Command = String
}
