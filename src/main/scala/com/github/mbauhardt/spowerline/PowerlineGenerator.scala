package com.github.mbauhardt.spowerline


object PowerlineGenerator {

  val generateContent: (String, PowerlineElement) => String = (s, pe) => {
    var acc = s
    val group = pe.element._1.group.toUpperCase
    val id = pe.element._1.id.toUpperCase
    acc += (s"\nexport SPOWERLINE_$group" + "_" + s"$id=" + pe.element._1.content)
    acc
  }


}
