package com.github.powerline


import java.util.Date

import com.github.mbauhardt.spowerline.{SegmentSeparator, Powerline, Segment, Command}

import scala.sys.process._


object Util {
  def combineCommands(commands: Seq[Command]): Command = commands.map(c => c).mkString(" && ")

  lazy val source: Command = "source ~/.zshrc"

  def execute(commands: Seq[Command]): String = Process(Seq("zsh", "-c", combineCommands(commands.+:(source)))).!!.replace("\n", "")

  def toExecutable(command: String): String = "`" + command + "`"

  val segmentSeparatorContent = "echo -e \"\\xE2\\xAE\\x80\""
}


object Common {
  val emptySegment: Segment = Segment("")
  val lastExitStatusSegment: Segment = Segment("%(?..%?)", "red", "blue", SegmentSeparator(Util.toExecutable(Util.segmentSeparatorContent), "blue", "blue"))
  val timeSegment: Segment = Segment("%D{%a %d-%b}%@", "black", "blue", SegmentSeparator(Util.toExecutable(Util.segmentSeparatorContent), "blue", "white"))
  val hostSegment: Segment = Segment("%n@%M", "black", "white", SegmentSeparator(Util.toExecutable(Util.segmentSeparatorContent), "white", "cyan"))
  val pwdSegment: Segment = Segment("%~", "black", "blue", SegmentSeparator(Util.toExecutable(Util.segmentSeparatorContent), "blue", "green"))
}

object Vcs {
  def isInsideGitDirectory: String = "git rev-parse --is-inside-work-tree &> /dev/null";
  val gitSegment: Segment = Segment("`git_prompt_info`", "black", "green", SegmentSeparator(Util.toExecutable(Util.combineCommands(Seq(isInsideGitDirectory, Util.segmentSeparatorContent))), "green", "black"))
}

object SPowerline extends App {

  def zshString(segment: Segment): String = "%{$bg[" + segment.bgColor + "]%}" + "%{$fg_bold[" + segment.fgColor + "]%}" + segment.content + "%{$reset_color%}";

  def zshString(separator: SegmentSeparator): String = "%{$bg[" + separator.bgColor + "]%}" + "%{$fg[" + separator.fgColor + "]%}" + separator.content + "%{$reset_color%}";

  def renderPowerline(powerline: Powerline) = {
    println("\r\n" + powerline.segments.map(s => zshString(s) + zshString(s.separator)).mkString + "\r\n%% ")
  }

  renderPowerline(Powerline(Seq(Common.lastExitStatusSegment, Common.pwdSegment, Vcs.gitSegment)))
}


