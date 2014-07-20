package com.github.powerline


import apple.laf.JRSUIConstants.SegmentLeadingSeparator
import com.github.mbauhardt.spowerline.{SegmentSeparator, Powerline, Segment, Command}

import scala.sys.process._


object Util {
  def combineCommands(commands: Seq[Command]): Command = commands.map(c => c).mkString(" && ")

  lazy val source: Command = "source ~/.zshrc"

  def execute(commands: Seq[Command]): String = Process(Seq("zsh", "-c", combineCommands(commands.+:(source)))).!!.replace("\n", "")
}


object Common {
  val segmentSeparatorContent = Util.execute(Seq("echo -e \"\\xE2\\xAE\\x80\""))
  val emptySegment: Segment = Segment("")
  val lastExitStatusSegment: Segment = Segment("%(?..%?)", "red", "blue", SegmentSeparator(segmentSeparatorContent, "blue", "blue"))
  val timeSegment: Segment = Segment("%D{%a %d-%b}%@", "black", "blue", SegmentSeparator(segmentSeparatorContent, "blue", "white"))
  val hostSegment: Segment = Segment("%n@%M", "black", "white", SegmentSeparator(segmentSeparatorContent, "white", "cyan"))
  val pwdSegment: Segment = Segment("%~", "black", "blue", SegmentSeparator(segmentSeparatorContent, "blue", "black"))
}

object SPowerline extends App {


  def renderPowerline(powerline: Powerline) = {
    println("\r\n" + powerline.segments.map(s =>
      //
      "%{$bg[" + s.bgColor + "]%}" + "%{$fg_bold[" + s.fgColor + "]%}" + s.content + "%{$reset_color%}"
        + "%{$bg[" + s.separator.bgColor + "]%}" + "%{$fg[" + s.separator.fgColor + "]%}" + s.separator.content + "%{$reset_color%}")
      .mkString("") + "\r\n%% ")
  }

  renderPowerline(Powerline(Seq(Common.lastExitStatusSegment, Common.pwdSegment)))
}


