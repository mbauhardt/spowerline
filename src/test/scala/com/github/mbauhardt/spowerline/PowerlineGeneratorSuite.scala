package com.github.mbauhardt.spowerline


import org.scalatest.FunSuite


class PowerlineGeneratorSuite extends FunSuite {

  test("generateContent") {
    val pe = Empty.inc(Segment("hello")).inc(Segment("world"))
    val pl = Powerline(pe)
    val s = pl.foldLeft("")(PowerlineGenerator.generateContent)

    println(s)
  }
}