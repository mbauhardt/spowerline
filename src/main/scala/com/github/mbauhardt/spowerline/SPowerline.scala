package com.github.powerline

import java.io.File
import java.net.InetAddress


import com.github.mbauhardt.spowerline.{Powerline, Command, Segment}

import scala.sys.process._
import java.io.File

object Util {
  def combineCommands(commands: Seq[Command]): Command = commands.map(c => c).mkString(" && ")

  lazy val source: Command = "source ~/.zshrc"

  def execute(commands: Seq[Command]): String = Process(Seq("zsh", "-c", combineCommands(commands.+:(source)))).!!.replace("\n", "")
}

object Common {
  lazy val emptySegment: Segment = Segment("")
  lazy val lastExitStatusSegment: Segment = Segment("%?")
  lazy val timeSegment: Segment = Segment("%D{%a %d-%b}%@")
  lazy val userSegment: Segment = Segment("%n@%M")
  lazy val pwdSegment: Segment = Segment("%~")
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
    println("\r\n" + powerline.segments.map(s => s.content).mkString(powerline.segmentSeparator) + "\r\n%% ")
  }

  renderPowerline(Powerline(Seq(Common.lastExitStatusSegment, Common.timeSegment, Common.userSegment, Common.pwdSegment), " > "))
}


