package com.github.mbauhardt.spowerline


import org.scalatest.FunSuite


class PowerlineElementSuite extends FunSuite {

  test("powerline element is empty ") {
    val pe: PowerlineElement = Empty
    assert(pe.isEmpty == true)
  }

  test("powerline element is not empty after add") {
    val pe: PowerlineElement = Empty.inc(Segment("hello"))
    assert(pe.isEmpty == false)
  }

  test("empty powerline element throws exc when calling element") {
    val pe: PowerlineElement = Empty
    intercept[NoSuchElementException] {
      pe.element
    }
  }

  test("retrieve element from the powerline element ") {
    val pe: PowerlineElement = Empty.inc(Segment("hello"))
    assert(pe.element._1.equals(Segment("hello")))
    assert(pe.element._2 == DefaultSeparator())
  }
}
