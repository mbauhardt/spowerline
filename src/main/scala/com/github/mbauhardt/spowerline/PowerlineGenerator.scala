package com.github.mbauhardt.spowerline


object PowerlineGenerator {


  //segment stuff

  val generateSegmentContent: (String, PowerlineElement) => String = (s, pe) => {
    var acc = s
    val group = pe.element._1.group.toUpperCase
    val id = pe.element._1.id.toUpperCase
    val content = pe.element._1.content
    acc += (s"\nexport SPOWERLINE_SEGMENT_$group" + "_" + s"$id" + s"_CONTENT=$content")
    acc
  }

  val generateSegmentForegroundColor: (String, PowerlineElement) => String = (s, pe) => {
    var acc = s
    val group = pe.element._1.group.toUpperCase
    val id = pe.element._1.id.toUpperCase
    val fgcolor = pe.element._1.fg
    acc += (s"\nexport SPOWERLINE_SEGMENT_$group" + "_" + s"$id" + "_FG=" + s"%{$$fg[$fgcolor]%}")
    acc
  }

  val generateSegmentBackgroundColor: (String, PowerlineElement) => String = (s, pe) => {
    var acc = s
    val group = pe.element._1.group.toUpperCase
    val id = pe.element._1.id.toUpperCase
    val bgcolor = pe.element._1.bg
    acc += (s"\nexport SPOWERLINE_SEGMENT_$group" + "_" + s"$id" + "_BG=" + s"%{$$bg[$bgcolor]%}")
    acc
  }

  //separator stuff

  val generateSeparatorContent: (String, PowerlineElement) => String = (s, pe) => {
    var acc = s
    val group = pe.element._1.group.toUpperCase
    val id = pe.element._1.id.toUpperCase
    val content = pe.element._2.content
    acc += (s"\nexport SPOWERLINE_SEPARATOR_$group" + "_" + s"$id" + s"_CONTENT=$content")
    acc
  }

  val generateSeparatorForegroundColor: (String, PowerlineElement) => String = (s, pe) => {
    var acc = s
    val group = pe.element._1.group.toUpperCase
    val id = pe.element._1.id.toUpperCase
    val fgcolor = pe.element._2.fg
    acc += (s"\nexport SPOWERLINE_SEPARATOR_$group" + "_" + s"$id" + "_FG=" + s"%{$$fg[$fgcolor]%}")
    acc
  }

  val generateSeparatorBackgroundColor: (String, PowerlineElement) => String = (s, pe) => {
    var acc = s
    val group = pe.element._1.group.toUpperCase
    val id = pe.element._1.id.toUpperCase
    val bgcolor = pe.element._2.bg
    acc += (s"\nexport SPOWERLINE_SEPARATOR_$group" + "_" + s"$id" + "_BG=" + s"%{$$bg[$bgcolor]%}")
    acc
  }

  //powerline stuff
  val generatePowerline: (String, PowerlineElement) => String = (s, pe) => {
    var acc = s

    val group = pe.element._1.group.toUpperCase
    val id = pe.element._1.id.toUpperCase
    val segPrefix = s"$$SPOWERLINE_SEGMENT_$group" + "_" + s"$id"
    val sepPrefix = s"$$SPOWERLINE_SEPARATOR_$group" + "_" + s"$id"

    val segFg = s"$segPrefix"  + "_FG"
    val segBg = s"$segPrefix"  + "_BG"
    val segContent = s"$segPrefix"  + "_CONTENT"

    val sepFg = s"$sepPrefix"  + "_FG"
    val sepBg = s"$sepPrefix"  + "_BG"
    val sepContent = s"$sepPrefix"  + "_CONTENT"

    acc += (s"[[ ! -z $segContent ]] && print $segFg$segBg$segContent$sepFg$sepBg$sepContent;")
    acc
  }
}
