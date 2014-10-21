package com.github.mbauhardt.spowerline


import org.scalatest.FunSuite


class PowerlineGeneratorSuite extends FunSuite {

  test("generateContent") {
    val pe = Empty.inc(Segment("common", "dir", "hello")).inc(Segment("vcs", "git", "world"))
    val pl = Powerline(pe)
    val s = pl.foldLeft("")(PowerlineGenerator.generateContent)
    assert(s == "\nexport SPOWERLINE_COMMON_DIR_CONTENT=hello\nexport SPOWERLINE_VCS_GIT_CONTENT=world")
  }

  test("generateFgColor") {
    val pe = Empty.inc(Segment("common", "dir", "hello", "red")).inc(Segment("vcs", "git", "world", "blue"))
    val pl = Powerline(pe)
    val s = pl.foldLeft("")(PowerlineGenerator.generateForeground)
    assert(s == "\nexport SPOWERLINE_COMMON_DIR_FG=%{$fg[red]%}\nexport SPOWERLINE_VCS_GIT_FG=%{$fg[blue]%}")
  }

  test("generateBgColor") {
    val pe = Empty.inc(Segment("common", "dir", "hello", bg = "cyan")).inc(Segment("vcs", "git", "world", bg = "blue"))
    val pl = Powerline(pe)
    val s = pl.foldLeft("")(PowerlineGenerator.generateBackground)
    assert(s == "\nexport SPOWERLINE_COMMON_DIR_BG=%{$bg[cyan]%}\nexport SPOWERLINE_VCS_GIT_BG=%{$bg[blue]%}")
  }
}