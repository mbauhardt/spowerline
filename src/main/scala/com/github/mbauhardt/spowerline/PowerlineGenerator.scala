package com.github.mbauhardt.spowerline


object PowerlineGenerator {

  val generateContent: (String, PowerlineElement) => String = (s, pe) => {
    var acc = s
    val group = pe.element._1.group.toUpperCase
    val id = pe.element._1.id.toUpperCase
    acc += (s"\nexport SPOWERLINE_$group" + "_" + s"$id" + "_CONTENT=" + pe.element._1.content)
    acc
  }

  val generateForeground: (String, PowerlineElement) => String = (s, pe) => {
    var acc = s
    val group = pe.element._1.group.toUpperCase
    val id = pe.element._1.id.toUpperCase
    val fgcolor = pe.element._1.fg
    acc += (s"\nexport SPOWERLINE_$group" + "_" + s"$id" + "_FG=" + s"%{$$fg[$fgcolor]%}")
    acc
  }

  val generateBackground: (String, PowerlineElement) => String = (s, pe) => {
    var acc = s
    val group = pe.element._1.group.toUpperCase
    val id = pe.element._1.id.toUpperCase
    val bgcolor = pe.element._1.bg
    acc += (s"\nexport SPOWERLINE_$group" + "_" + s"$id" + "_BG=" + s"%{$$bg[$bgcolor]%}")
    acc
  }

}
