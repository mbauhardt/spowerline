package com.github.mbauhardt

package object spowerline {

  case class Segment(content: String, fg: String = "default", bg: String = "default")

  case class Separator(content: String, fg: String = "default", bg: String = "default")

  val defaultSeparator: Separator = Separator("$(echo -e \"\\xE2\\xAE\\x80\")")
}
