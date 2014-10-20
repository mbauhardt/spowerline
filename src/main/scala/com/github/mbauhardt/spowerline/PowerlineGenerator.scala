package com.github.mbauhardt.spowerline


object PowerlineGenerator {

  val generateContent: (String, PowerlineElement) => String = (s, pe) => {
    var acc = s
    acc += ("export A=" + pe.element._1.content)
    acc
  }


}
