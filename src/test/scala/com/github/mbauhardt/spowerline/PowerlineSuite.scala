package com.github.mbauhardt.spowerline

import org.scalatest.FunSuite


class PowerlineSuite extends FunSuite {

  test("Powerline is empty on an empty powerline element") {
    val pe: PowerlineElement = Empty
    assert(Powerline(pe).isEmpty)
  }

  test("Powerline is non empty on an empty powerline element") {
    val pe: PowerlineElement = Empty.inc(Segment("group", "id", "hello world"))
    assert(!Powerline(pe).isEmpty)
  }

  test("head of empty powerline throws exc") {
    intercept[NoSuchElementException] {
      Powerline(Empty).head
    }
  }

  test("tail of empty powerline throws exc") {
    intercept[NoSuchElementException] {
      Powerline(Empty).tail
    }
  }

  test("head of non empty powerline ") {
    assert(Powerline(Empty.inc(Segment("group", "id", "hello"))).head.element._1.content == "hello")
    assert(Powerline(Empty.inc(Segment("group", "id", "hello"))).head.element._2 == DefaultSeparator())
  }

  test("head and tail combination of non empty powerline") {
    assert(Powerline(Empty.inc(Segment("group", "id", "hello")).inc(Segment("group", "id2", "world"))).tail.head.element._1.content == "world")
    assert(Powerline(Empty.inc(Segment("group", "id", "hello")).inc(Segment("group", "id2", "world"))).tail.head.element._2 == DefaultSeparator())
  }

  test("foldLeft") {
    val p = Powerline(Empty.inc(Segment("group", "id", "hello")).inc(Segment("group", "id2", "world")))
    val s = p.foldLeft("") {
      (acc, pe) => acc + pe.element._1.content
    }
    assert(s.equals("helloworld"))
  }

  test("foldRight") {
    val p = Powerline(Empty.inc(Segment("group", "id", "hello")).inc(Segment("group", "id2", "world")))
    val s = p.foldRight("") {
      (pe, acc) => acc + pe.element._1.content
    }
    assert(s.equals("worldhello"))
  }
}
