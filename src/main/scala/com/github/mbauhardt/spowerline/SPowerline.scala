package com.github.powerline

import java.net.InetAddress
import scala.sys.process._

object SPowerline extends App {
  def userFunction: String = System.getProperties().get("user.name").asInstanceOf[String]
  def hostFunction: String = "@" + InetAddress.getLocalHost().getHostName()
  def pwdFunction: String = "pwd".!!.replace("\n", "")
  //def branchFunction: String = "${$(git symbolic-ref HEAD 2> /dev/null || git rev-parse --short HEAD 2> /dev/null)#refs/heads/}".!!.replace("\n", "")
  def branchFunction: String = {
    val branchName = "git symbolic-ref HEAD".!!.replace("\n", "")
    val commitId  = "git rev-parse --short HEAD".!!.replace("\n", "")
    s"$branchName($commitId)"
  }

  val powerline = new Powerline(Seq(new Segment(userFunction), new Segment(hostFunction), new Segment(pwdFunction), new Segment(branchFunction)))
  render(powerline)

  def render(powerline: Powerline) {
    println(powerline.segments.map(segment => segment.value).mkString(" > "))
  }
}

class Powerline(val segments: Seq[Segment])

class Segment(f: => String) {
  def value: String = f
}

