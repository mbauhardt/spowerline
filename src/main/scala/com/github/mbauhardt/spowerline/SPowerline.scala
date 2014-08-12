package com.github.powerline


import com.github.mbauhardt.spowerline._

import scala.sys.process._


object Util {
  def combineCommands(cs: Seq[Command]): Command = cs.map(c => c).mkString(" && ")

  def and(es: Seq[Executable]): Executable = Executable("{ " + es.map(e => "{ " + e.command + " }").mkString(" && ") + " }")

  def or(es: Seq[Executable]): Executable = Executable("{ " + es.map(e => "{ " + e.command + " }").mkString(" || ") + " }")

  lazy val source: Command = "source ~/.zshrc"

  def execute(commands: Seq[Command]): String = Process(Seq("zsh", "-c", combineCommands(commands.+:(source)))).!!.replace("\n", "")

  val segmentSeparatorContent: Executable = Executable("echo -e \"\\xE2\\xAE\\x80\"")
}


object Common {
  val lastExitStatusSegment: Segment = Segment(Left("%(?..%?)"), "red", "blue")
  val timeSegment: Segment = Segment(Left("%D{%a %d-%b}%@"), "black", "blue")
  val hostSegment: Segment = Segment(Left("%n@%M"), "black", "white")
  val pwdSegment: Segment = Segment(Left("%~"), "black", "blue")
}

object Vcs {
  def isInsideGitDirectory: String = "git rev-parse --is-inside-work-tree &> /dev/null"

  val gitSegment: Segment = Segment(Right(Executable("git_prompt_info")), "black", "green", Some(Executable(isInsideGitDirectory)))
}

object SPowerline extends App {


  def createSeparators(segments: List[Segment]): List[(Segment, SegmentSeparator)] = {
    val z: List[(Segment, SegmentSeparator)] = Nil
    segments.foldRight(z) {
      (segment, acc) =>
        val bgColor: String = acc match {
          case Nil => "default"
          case a :: rest => a._1.bgColor
        }
        val separator: SegmentSeparator = SegmentSeparator(Right(Util.segmentSeparatorContent), segment.bgColor, bgColor)
        (segment, separator) :: acc
    }
  }

  def renderContent(c: Either[String, Executable], precondition: Executable) = {
    c match {
      case Left(l) => precondition.commanWithBackticks + " " + l
      case Right(r) => Util.and(Seq(precondition, r)).commanWithBackticks
    }
  }

  def renderSegment(s: Segment): String = {
    val prec = s.precondition.getOrElse(Executable("true"))
    renderContent(s.content, prec)
  }

  def renderSeparator(seg: Segment, sep: SegmentSeparator): String = {
    val prec = seg.precondition.getOrElse(Executable("true"))
    renderContent(sep.content, prec)
  }

  def zshString(segment: Segment): String = "%{$bg[" + segment.bgColor + "]%}" + "%{$fg_bold[" + segment.fgColor + "]%}" + renderSegment(segment) + "%{$reset_color%}"

  def zshString(segment: Segment, separator: SegmentSeparator): String = "%{$bg[" + separator.bgColor + "]%}" + "%{$fg[" + separator.fgColor + "]%}" + renderSeparator(segment, separator) + "%{$reset_color%}"

  def renderPowerline(powerline: Powerline) = {

    val segmentsWithSeparators = createSeparators(powerline.segments.toList)

    println("\r\n" + segmentsWithSeparators.map(s => zshString(s._1) + zshString(s._1, s._2)).mkString + "\r\n%% ")
  }

  renderPowerline(Powerline(Seq(Common.lastExitStatusSegment, Common.pwdSegment, Vcs.gitSegment)))
}


//PROMPT="`{ { { false } && { echo '%{$fg[blue]%}hello' } } || { { true } && { echo '%{$fg[red]%}hello' } } }`"