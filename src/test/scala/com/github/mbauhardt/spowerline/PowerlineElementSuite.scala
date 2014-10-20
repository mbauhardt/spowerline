package com.github.mbauhardt.spowerline

import org.scalatest.FunSuite


class PowerlineElementSuite extends FunSuite {

  test("powerline element is empty ") {
    val pe: PowerlineElement = Empty
    assert(pe.isEmpty == true)
  }

  test("powerline element is not empty after add") {
    val pe: PowerlineElement = Empty.inc(Segment("group", "id", "hello"))
    assert(pe.isEmpty == false)
  }

  test("empty powerline element throws exc when calling element") {
    val pe: PowerlineElement = Empty
    intercept[NoSuchElementException] {
      pe.element
    }
  }

  test("retrieve element from the powerline element ") {
    val pe: PowerlineElement = Empty.inc(Segment("group", "id", "hello"))
    assert(pe.element._1.equals(Segment("group", "id", "hello")))
    assert(pe.element._2 == DefaultSeparator())
  }

  test("duplicate segment: same group but different id ") {
    val s1 = Segment("group", "id", "content")
    val s2 = Segment("group", "id2", "content")
    Empty.inc(s1).inc(s2)
  }

  test("duplicate segment: different group but same id ") {
    val s1 = Segment("group1", "id", "content")
    val s2 = Segment("group2", "id", "content")
    Empty.inc(s1).inc(s2)
  }

  test("duplicate segment: same group and same id") {
    val s1 = Segment("group", "id", "content")
    val s2 = Segment("group", "id", "content")
    val e = intercept[IllegalArgumentException] {
      Empty.inc(s1).inc(s2)
    }
    assert(e.getMessage == "Duplicate segment detected: group.id")
  }

  test("duplicate segment again: same group and same id") {
    val s1 = Segment("group", "id", "content")
    val s2 = Segment("group2", "id", "content")
    val s3 = Segment("group", "id", "content")
    val e = intercept[IllegalArgumentException] {
      Empty.inc(s1).inc(s2).inc(s3)
    }
    assert(e.getMessage == "Duplicate segment detected: group.id")
  }
}
