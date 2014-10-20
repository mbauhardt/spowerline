package com.github.mbauhardt.spowerline


import org.scalatest.FunSuite


class PowerlineGeneratorSuite extends FunSuite {

  test("generateContent") {
    val pe = Empty.inc(Segment("common", "dir", "hello")).inc(Segment("vcs", "git", "world"))
    val pl = Powerline(pe)
    val s = pl.foldLeft("")(PowerlineGenerator.generateContent)
    assert(s == "\nexport SPOWERLINE_COMMON_DIR=hello\nexport SPOWERLINE_VCS_GIT=world")
  }
}