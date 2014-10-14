package com.github.mbauhardt.spowerline

import org.scalatest.FunSuite


class PowerlineSuite extends FunSuite {

  test("Powerline is empty on an empty powerline element") {
    val pe: PowerlineElement = Empty()
    assert(Powerline(pe).isEmpty)
  }

  test("Powerline is non empty on an empty powerline element") {
    val pe: PowerlineElement = Empty().add(Segment("hello world"))
    assert(!Powerline(pe).isEmpty)
  }

  test("head of empty powerline throws exc") {
    intercept[NoSuchElementException] {
      Powerline(Empty()).head
    }
  }

  test("tail of empty powerline throws exc") {
    intercept[NoSuchElementException] {
      Powerline(Empty()).tail
    }
  }

  test("head of non empty powerline ") {
    assert(Powerline(Empty().add(Segment("hello"))).head.element._1.content == "hello")
    assert(Powerline(Empty().add(Segment("hello"))).head.element._2 == defaultSeparator)
  }

  test("head and tail combination of non empty powerline") {
    assert(Powerline(Empty().add(Segment("hello")).add(Segment("world"))).tail.head.element._1.content == "world")
    assert(Powerline(Empty().add(Segment("hello")).add(Segment("world"))).tail.head.element._2 == defaultSeparator)
  }

  test("foldLeft") {
    val p = Powerline(Empty().add(Segment("hello")).add(Segment("world")))
    val s = p.foldLeft("") {
      (acc, pe) => acc + pe.element._1.content
    }
    assert(s.equals("helloworld"))
  }

  test("foldRight") {
    val p = Powerline(Empty().add(Segment("hello")).add(Segment("world")))
    val s = p.foldRight("") {
      (acc, pe) => acc + pe.element._1.content
    }
    assert(s.equals("worldhello"))
  }
}
