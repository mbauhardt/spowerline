---
layout: page
title: spowerline
tagline: a theme for oh-my-zsh
---
{% include JB/setup %}

[Spowerline](https://github.com/mbauhardt/spowerline) is a zsh theme for [oh-my-zsh](https://github.com/robbyrussell/oh-my-zsh/) project.
Inspired by the [Agnoster](https://gist.github.com/agnoster/3712874) zsh theme,
[Tmux Powerline](https://github.com/erikw/tmux-powerline),
the [Vim Powerline](https://github.com/Lokaltog/vim-powerline)
and the [Vim Status Plugin](https://github.com/Lokaltog/powerline)

<img src="assets/welcome.png" style="width:400px;">

### Features
[Supported Segments](features.html)

### Installation
[How to install spowerline](installation.html)

### News

<ul class="posts">
  {% for post in site.posts %}
    <li><span>{{ post.date | date_to_string }}</span> &raquo; <a href="{{ BASE_PATH }}{{ post.url }}">{{ post.title }}</a></li>
  {% endfor %}
</ul>
