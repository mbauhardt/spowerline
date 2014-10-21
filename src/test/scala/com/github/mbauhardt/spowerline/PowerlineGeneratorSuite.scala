package com.github.mbauhardt.spowerline


import org.scalatest.FunSuite


class PowerlineGeneratorSuite extends FunSuite {

  test("generateSegmentContent") {
    val pe = Empty.inc(Segment("common", "dir", "hello")).inc(Segment("vcs", "git", "world"))
    val pl = Powerline(pe)
    val s = pl.foldLeft("")(PowerlineGenerator.generateSegmentContent)
    assert(s == "\nexport SPOWERLINE_SEGMENT_COMMON_DIR_CONTENT=hello\nexport SPOWERLINE_SEGMENT_VCS_GIT_CONTENT=world")
  }

  test("generateSegmentFgColor") {
    val pe = Empty.inc(Segment("common", "dir", "hello", "red")).inc(Segment("vcs", "git", "world", "blue"))
    val pl = Powerline(pe)
    val s = pl.foldLeft("")(PowerlineGenerator.generateSegmentForegroundColor)
    assert(s == "\nexport SPOWERLINE_SEGMENT_COMMON_DIR_FG=%{$fg[red]%}\nexport SPOWERLINE_SEGMENT_VCS_GIT_FG=%{$fg[blue]%}")
  }

  test("generateSegmentBgColor") {
    val pe = Empty.inc(Segment("common", "dir", "hello", bg = "cyan")).inc(Segment("vcs", "git", "world", bg = "blue"))
    val pl = Powerline(pe)
    val s = pl.foldLeft("")(PowerlineGenerator.generateSegmentBackgroundColor)
    assert(s == "\nexport SPOWERLINE_SEGMENT_COMMON_DIR_BG=%{$bg[cyan]%}\nexport SPOWERLINE_SEGMENT_VCS_GIT_BG=%{$bg[blue]%}")
  }
}