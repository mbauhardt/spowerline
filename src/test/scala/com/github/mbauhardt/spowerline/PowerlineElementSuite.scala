package com.github.mbauhardt.spowerline


import org.scalatest.FunSuite


class PowerlineElementSuite extends FunSuite {

  test("powerline element is empty ") {
    val pe: PowerlineElement = new Empty()
    assert(pe.isEmpty == true)
  }

  test("powerline element is not empty after add") {
    val pe: PowerlineElement = new Empty().add("hello")
    assert(pe.isEmpty == false)
  }

  test("empty powerline element throws exc when calling element") {
    val pe: PowerlineElement = new Empty()
    intercept[NoSuchElementException] {
      pe.element
    }
  }

  test("retrieve element from the powerline element ") {
    val pe: PowerlineElement = new Empty().add("hello")
    assert(pe.element._1 == "hello")
    assert(pe.element._2 == defaultSeparator)
  }

  test("toPowerline is empty on an empty powerline element") {
    val pe: PowerlineElement = new Empty()
    assert(pe.toPowerline.isEmpty)
  }

  test("retrieve a non empty powerline from a non empty powerline element ") {
    val pe: PowerlineElement = new Empty().add("hello world")
    assert(!pe.toPowerline.isEmpty)
  }
}
