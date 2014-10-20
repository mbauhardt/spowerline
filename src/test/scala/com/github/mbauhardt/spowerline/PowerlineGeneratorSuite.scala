package com.github.mbauhardt.spowerline


import org.scalatest.FunSuite


class PowerlineGeneratorSuite extends FunSuite {

  test("generateContent") {
    val pe = Empty.inc(Segment("group", "id", "hello")).inc(Segment("group", "id", "world"))
    val pl = Powerline(pe)
    val s = pl.foldLeft("")(PowerlineGenerator.generateContent)

    println(s)
  }
}