package com.github.powerline

import java.net.InetAddress
import scala.sys.process._
import java.io.File

object SPowerline extends App {

  def source = "source ~/.zshrc"

  def insideWorkingTree = "git rev-parse --is-inside-work-tree"

  def gitInfoProvidedByOhMyZsh = "git_prompt_info"

  def combineProcessArguments(arguments: Seq[String]): String = arguments.mkString(" && ")

  def userFunction: String = System.getProperties().get("user.name").asInstanceOf[String]

  def hostFunction: String = "@" + InetAddress.getLocalHost().getHostName()

  def pwdFunction: String = "pwd".!!.replace("\n", "")

  def branchFunction: String = {

    def isGitRepository: Boolean = Process(Seq("zsh", "-c", combineProcessArguments(Seq(source, insideWorkingTree)))).! == 0

    if (isGitRepository) {
      Process(Seq("zsh", "-c", combineProcessArguments(Seq(source, gitInfoProvidedByOhMyZsh)))).!!
    } else {
      ""
    }
  }

  def render(powerline: Powerline) {
    println(powerline.segments.map(segment => segment.value).mkString(" > "))
  }

  val powerline = new Powerline(Seq(new Segment(userFunction), new Segment(hostFunction), new Segment(pwdFunction), new Segment(branchFunction)))
  render(powerline)

}

class Powerline(val segments: Seq[Segment])

class Segment(f: => String) {
  def value: String = f
}

