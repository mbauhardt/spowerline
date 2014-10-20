package com.github.mbauhardt.spowerline


case class Segment(group: String, id: String, content: String, fg: String = "default", bg: String = "default") {
  override def toString() = s"$group.$id"
}

case class Separator(content: String, fg: String = "default", bg: String = "default")

object DefaultSeparator {
  def apply(): Separator = {
    Separator("$(echo -e \"\\xE2\\xAE\\x80\")")
  }
}




