package com.github.mbauhardt.spowerline

import org.scalatest.FunSuite


class PowerlineSuite extends FunSuite {

  test("head of empty powerline throws exc") {
    intercept[NoSuchElementException] {
      Empty().toPowerline.head
    }
  }

  test("tail of empty powerline throws exc") {
    intercept[NoSuchElementException] {
      Empty().toPowerline.tail
    }
  }

  test("head of non empty powerline ") {
    assert(Empty().add(Segment("hello")).toPowerline.head.element._1.content == "hello")
    assert(Empty().add(Segment("hello")).toPowerline.head.element._2 == defaultSeparator)
  }

  test("head and tail combination of non empty powerline") {
    assert(Empty().add(Segment("hello")).add(Segment("world")).toPowerline.tail.head.element._1.content == "world")
    assert(Empty().add(Segment("hello")).add(Segment("world")).toPowerline.tail.head.element._2 == defaultSeparator)
  }

  test("foldLeft") {
    val p = Empty().add(Segment("hello")).add(Segment("world")).toPowerline
    val s = p.foldLeft("") {
      (acc, pe) => acc + pe.element._1.content
    }
    assert(s.equals("helloworld"))
  }

  test("foldRight") {
    val p = Empty().add(Segment("hello")).add(Segment("world")).toPowerline
    val s = p.foldRight("") {
      (acc, pe) => acc + pe.element._1.content
    }
    assert(s.equals("worldhello"))
  }
}
