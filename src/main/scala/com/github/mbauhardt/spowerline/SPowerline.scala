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
  val lastExitStatusSegment: Segment = Segment("%(?..%?)", "red", "blue")
  val timeSegment: Segment = Segment("%D{%a %d-%b}%@", "black", "blue")
  val hostSegment: Segment = Segment("%n@%M", "black", "white")
  val pwdSegment: Segment = Segment("%~", "black", "blue")
}

object Vcs {
  def isInsideGitDirectory: String = "git rev-parse --is-inside-work-tree &> /dev/null";
  val gitSegment: Segment = Segment(Util.toExecutable(Util.combineCommands(Seq(isInsideGitDirectory, "git_prompt_info"))), "black", "green")
}

object SPowerline extends App {


  def createSeparators(segments: List[Segment]): List[(Segment, SegmentSeparator)] = {
    val z: List[(Segment, SegmentSeparator)] = Nil
    segments.foldRight(z) {
      (segment, acc) =>
        val bgColor = acc match {
          case Nil => "default"
          case a :: rest => a._1.bgColor
        }
        val separator: SegmentSeparator = SegmentSeparator(Util.toExecutable(Util.segmentSeparatorContent), segment.bgColor, bgColor)
        (segment, separator) :: acc
    }
  }


  def zshString(segment: Segment): String = "%{$bg[" + segment.bgColor + "]%}" + "%{$fg_bold[" + segment.fgColor + "]%}" + segment.content + "%{$reset_color%}";


  def zshString(separator: SegmentSeparator): String = "%{$bg[" + separator.bgColor + "]%}" + "%{$fg[" + separator.fgColor + "]%}" + separator.content + "%{$reset_color%}";

  def renderPowerline(powerline: Powerline) = {

    val segmentsWithSeparators = createSeparators(powerline.segments.toList)

    println("\r\n" + segmentsWithSeparators.map(s => zshString(s._1) + zshString(s._2)).mkString + "\r\n%% ")
  }

  renderPowerline(Powerline(Seq(Common.lastExitStatusSegment, Common.pwdSegment, Vcs.gitSegment)))
}


