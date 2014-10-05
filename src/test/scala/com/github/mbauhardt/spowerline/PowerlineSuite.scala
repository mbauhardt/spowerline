package com.github.mbauhardt.spowerline

import org.scalatest.FunSuite


class PowerlineSuite extends FunSuite {

  test("head of empty powerline throws exc") {
    intercept[NoSuchElementException] {
      new Empty().toPowerline.head
    }
  }

  test("tail of empty powerline throws exc") {
    intercept[NoSuchElementException] {
      new Empty().toPowerline.tail
    }
  }

  test("head of non empty powerline ") {
    assert(new Empty().add("hello").toPowerline.head.element._1 == "hello")
    assert(new Empty().add("hello").toPowerline.head.element._2 == defaultSeparator)
  }

  test("head and tail combination of non empty powerline") {
    assert(new Empty().add("hello").add("world").toPowerline.tail.head.element._1 == "world")
    assert(new Empty().add("hello").add("world").toPowerline.tail.head.element._2 == defaultSeparator)
  }

  test("foldLeft") {
    val p = new Empty().add("hello").add("world").toPowerline
    val s = p.foldLeft("") {
      (acc, pe) => acc + pe.element._1
    }
    assert(s.equals("helloworld"))
  }

  test("foldRight") {
    val p = new Empty().add("hello").add("world").toPowerline
    val s = p.foldRight("") {
      (acc, pe) => acc + pe.element._1
    }
    assert(s.equals("worldhello"))
  }
}
