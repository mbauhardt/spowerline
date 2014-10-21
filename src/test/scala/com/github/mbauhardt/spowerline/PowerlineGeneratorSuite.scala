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

  test("generateSeparatorContent") {
    val pe = Empty.inc(Segment("common", "dir", "hello")).inc(Segment("vcs", "git", "world"))
    val pl = Powerline(pe)
    val s = pl.foldLeft("")(PowerlineGenerator.generateSeparatorContent)
    assert(s == "\nexport SPOWERLINE_SEPARATOR_COMMON_DIR_CONTENT=$(echo -e \"\\xE2\\xAE\\x80\")\nexport SPOWERLINE_SEPARATOR_VCS_GIT_CONTENT=$(echo -e \"\\xE2\\xAE\\x80\")")
  }

  test("generateSeparatorFgColor") {
    val pe = Empty.inc(Segment("common", "dir", "hello", "red", "black")).inc(Segment("vcs", "git", "world", "blue", "white"))
    val pl = Powerline(pe)
    val s = pl.foldLeft("")(PowerlineGenerator.generateSeparatorForegroundColor)
    assert(s == "\nexport SPOWERLINE_SEPARATOR_COMMON_DIR_FG=%{$fg[black]%}\nexport SPOWERLINE_SEPARATOR_VCS_GIT_FG=%{$fg[white]%}")
  }

  test("generateSeparatorBgColor") {
    val pe = Empty.inc(Segment("common", "dir", "hello", bg = "cyan")).inc(Segment("vcs", "git", "world", bg = "blue"))
    val pl = Powerline(pe)
    val s = pl.foldLeft("")(PowerlineGenerator.generateSeparatorBackgroundColor)
    assert(s == "\nexport SPOWERLINE_SEPARATOR_COMMON_DIR_BG=%{$bg[blue]%}\nexport SPOWERLINE_SEPARATOR_VCS_GIT_BG=%{$bg[default]%}")
  }

  test("generatePowerline") {
    val pe = Empty.inc(Segment("common", "dir", "hello", bg = "cyan")).inc(Segment("vcs", "git", "world", bg = "blue"))
    val pl = Powerline(pe)
    val s = pl.foldLeft("")(PowerlineGenerator.generatePowerline)
    println(s)
    assert(s == "" +
      "$SPOWERLINE_SEGMENT_COMMON_DIR_FG" +
      "$SPOWERLINE_SEGMENT_COMMON_DIR_BG" +
      "$SPOWERLINE_SEGMENT_COMMON_DIR_CONTENT" +
      "$SPOWERLINE_SEPARATOR_COMMON_DIR_FG" +
      "$SPOWERLINE_SEPARATOR_COMMON_DIR_BG" +
      "$SPOWERLINE_SEPARATOR_COMMON_DIR_CONTENT" +
      "$SPOWERLINE_SEGMENT_VCS_GIT_FG" +
      "$SPOWERLINE_SEGMENT_VCS_GIT_BG" +
      "$SPOWERLINE_SEGMENT_VCS_GIT_CONTENT" +
      "$SPOWERLINE_SEPARATOR_VCS_GIT_FG" +
      "$SPOWERLINE_SEPARATOR_VCS_GIT_BG" +
      "$SPOWERLINE_SEPARATOR_VCS_GIT_CONTENT")
  }

}