---
layout: page
title: "Installation"
description: "Install spowerline"
---
{% include JB/setup %}

### Recommendation
You will achieve the best color fidelity when using

* [Solarized dark theme](https://github.com/altercation/solarized/)
* In addition, when working with OSX please use [iTerm2](http://iterm2.com/)
* the powerline patched font [Menlo](https://gist.github.com/qrush/1595572/raw/417a3fa36e35ca91d6d23ac961071094c26e5fad/Menlo-Powerline.otf)

<p/>

### Installation

Open your terminal and execute the commands below

    mkdir -p ~/.oh-my-zsh/custom/themes
    cd ~/.oh-my-zsh/custom/themes
    curl -L -o spowerline.zsh-theme http://bit.ly/1BvwFPL

Open the file <code>~/.zshrc</code> and update the value of property <code>ZSH_THEME</code>

    ZSH_THEME="spowerline"

Thats it.
