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
  lazy val segmentSeparatorContent = Util.execute(Seq("echo -e \"\\xE2\\xAE\\x80\""))
  lazy val emptySegment: Segment = Segment("")
  lazy val lastExitStatusSegment: Segment = Segment("%?", "black", "green", SegmentSeparator(segmentSeparatorContent, "green", "blue"))
  lazy val timeSegment: Segment = Segment("%D{%a %d-%b}%@", "black", "blue", SegmentSeparator(segmentSeparatorContent, "blue", "white"))
  lazy val hostSegment: Segment = Segment("%n@%M", "black", "white", SegmentSeparator(segmentSeparatorContent, "white", "cyan"))
  lazy val pwdSegment: Segment = Segment("%~", "black", "blue", SegmentSeparator(segmentSeparatorContent, "blue", "black"))
}

object Git {
  //  val insideWorkingTree: Command = "git rev-parse --is-inside-work-tree"
  //  val gitInfo: Command = "git_prompt_info"
  //  val gitInfoProvidedByOhMyZsh: Segment = () =>
  //  def branchFunction: Segment = {

  //def isGitRepository: Boolean = Process(Seq("zsh", "-c", Util.combineCommands(Seq(Util.source, insideWorkingTree)).apply())).! == 0

  //    if (isGitRepository) {
  //      () => Process(Seq("zsh", "-c", Util.combineCommands(Seq(Util.source, gitInfoProvidedByOhMyZsh)).apply())).!!
  //    } else {
  //      Common.empty
  //    }
  //  }
}

object SPowerline extends App {
  def renderPowerline(powerline: Powerline) = {
    println("\r\n" + powerline.segments.map(s =>
      //
      "%{$bg[" + s.bgColor + "]%}"
        + "%{$fg[" + s.fgColor + "]%}"
        + s.content
        + "%{$reset_color%}"
        + "%{$bg[" + s.separator.bgColor + "]%}"
        + "%{$fg[" + s.separator.fgColor + "]%}"
        + s.separator.content
        + "%{$reset_color%}")
      .mkString("") + "\r\n%% ")
  }

  renderPowerline(Powerline(Seq(Common.lastExitStatusSegment, Common.pwdSegment)))
}


