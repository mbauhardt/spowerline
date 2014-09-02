package com.github.powerline


import com.github.mbauhardt.spowerline._

import scala.sys.process._


object Util {
  def combineCommands(cs: Seq[Command]): Command = cs.map(c => c).mkString(" && ")

  def and(es: Seq[Executable]): Executable = Executable(es.map(e => e.command).mkString(" && "))

  lazy val source: Command = "source ~/.zshrc"

  def execute(commands: Seq[Command]): String = Process(Seq("zsh", "-c", combineCommands(commands.+:(source)))).!!.replace("\n", "")

  val segmentSeparatorContent: Executable = Executable("echo -e \"\\xE2\\xAE\\x80\"")
}


object Common {
  val lastExitStatusSegment: Segment = Segment(Seq(Left("%(?..%?)")), "red", "blue")
  val timeSegment: Segment = Segment(Seq(Left("%D{%a %d-%b}%@")), "black", "blue")
  val hostSegment: Segment = Segment(Seq(Left("%n@%M")), "black", "white")
  val pwdSegment: Segment = Segment(Seq(Left("%~")), "black", "blue")
}

object Vcs {
  def isInsideGitDirectory: String = "git rev-parse --is-inside-work-tree &> /dev/null"

  val gitSegment: Segment = {
    val gitPrefix = Executable("ZSH_THEME_GIT_PROMPT_PREFIX='\\xe2\\xad\\xa0 '")
    val gitSuffix = Executable("ZSH_THEME_GIT_PROMPT_SUFFIX=''")
    val gitDirty = Executable("ZSH_THEME_GIT_PROMPT_DIRTY=' \\xe2\\x9c\\x97 '")
    val gitClean = Executable("ZSH_THEME_GIT_PROMPT_CLEAN=''")
    val gitAhead = Executable("ZSH_THEME_GIT_PROMPT_AHEAD_REMOTE=' \\xe2\\x86\\x91 '")
    val gitBehind = Executable("ZSH_THEME_GIT_PROMPT_BEHIND_REMOTE=' \\xe2\\x86\\x93 '")
    val gitDiverged = Executable("ZSH_THEME_GIT_PROMPT_DIVERGED_REMOTE=' \\xe2\\x87\\x85 '")
    val gitPrompt: Executable = Executable("git_prompt_info")
    val gitRemoteStatus: Executable = Executable("git_remote_status")
    Segment(Seq(Right(Util.and(Seq(gitPrefix, gitSuffix, gitDirty, gitClean, gitPrompt))), Right(Util.and(Seq(gitAhead, gitBehind, gitDiverged, gitRemoteStatus)))), "black", "green", Some(Executable(isInsideGitDirectory)))
  }

}

object SPowerline extends App {


  def segmentWithSeparators(segments: List[Segment]): List[(Segment, SegmentSeparator, Option[Segment])] = {
    val z: List[(Segment, SegmentSeparator, Option[Segment])] = Nil
    segments.foldRight(z) {
      (segment, acc) =>
        val next: Option[Segment] = acc match {
          case Nil => None
          case a :: rest => Some(a._1)
        }
        val separator: SegmentSeparator = SegmentSeparator(Right(Util.segmentSeparatorContent), segment.bgColor, "default")
        (segment, separator, next) :: acc
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
    var ret = ""
    for (x <- s.content) {
      ret = ret + "" + renderContent(x, prec)
    }
    ret
  }

  def renderSeparator(seg: Segment, sep: SegmentSeparator): String = {
    val prec = seg.precondition.getOrElse(Executable("true"))
    renderContent(sep.content, prec)
  }


  def zshString(segment: Segment, separator: SegmentSeparator, o: Option[Segment]): String = {
    val segmentContent = "%{$bg[" + segment.bgColor + "]%}" + "%{$fg_bold[" + segment.fgColor + "]%}" + renderSegment(segment) + "%{$reset_color%}"
    val separatorBgColor = {
      val bgColor = o match {
        case None => "echo default"
        case Some(s) => s.precondition match {
          case None => "echo " + s.bgColor
          case Some(p) => "{" + p.command + " && echo " + s.bgColor + "}" + "||" + "{!" + p.command + " && echo default}"
        }
      }
      Executable(bgColor).commanWithBackticks
    }
    val separatorContent = "%{$bg[" + separatorBgColor + "]%}" + "%{$fg[" + separator.fgColor + "]%}" + renderSeparator(segment, separator) + "%{$reset_color%}"
    segmentContent + separatorContent
  }

  def renderPowerline(powerline: Powerline) = {

    val segmentsWithSeparators: List[(Segment, SegmentSeparator, Option[Segment])] = segmentWithSeparators(powerline.segments.toList)

    println("\r\n" + segmentsWithSeparators.map(s => zshString(s._1, s._2, s._3)).mkString + "\r\n%% ")
  }

  renderPowerline(Powerline(Seq(Common.lastExitStatusSegment, Common.pwdSegment, Vcs.gitSegment)))
}
