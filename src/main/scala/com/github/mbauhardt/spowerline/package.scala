package com.github.mbauhardt

package object spowerline {
  type Segment = String
  type Separator = String
  val defaultSeparator: Separator = "$(echo -e \"\\xE2\\xAE\\x80\")"
}
