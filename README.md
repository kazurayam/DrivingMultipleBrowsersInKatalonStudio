# Driving Multiple Browsers in Katalon Studio

## Starting up HTTP server at <http://127.0.0.1/>

I installed `docker` command into my Mac Book Air

    $ brew install docker
    Running `brew update --preinstall`...
    ==> Auto-updated Homebrew!
    Updated 1 tap (homebrew/cask).
    ==> Updated Casks
    Updated 3 casks.

    Warning: Treating docker as a formula. For the cask, use homebrew/cask/docker
    ==> Downloading https://ghcr.io/v2/homebrew/core/docker/manifests/20.10.12
    ######################################################################## 100.0%
    ==> Downloading https://ghcr.io/v2/homebrew/core/docker/blobs/sha256:487260ddefb
    ==> Downloading from https://pkg-containers.githubusercontent.com/ghcr1/blobs/sh
    ######################################################################## 100.0%
    ==> Pouring docker--20.10.12.monterey.bottle.tar.gz
    Error: The `brew link` step did not complete successfully
    The formula built, but is not symlinked into /usr/local
    Could not symlink bin/docker
    Target /usr/local/bin/docker
    already exists. You may want to remove it:
      rm '/usr/local/bin/docker'

    To force the link and overwrite all conflicting files:
      brew link --overwrite docker

    To list all files that would be deleted:
      brew link --overwrite --dry-run docker

    Possible conflicting files are:
    /usr/local/bin/docker -> /Applications/Docker.app/Contents/Resources/bin/docker
    ==> Caveats
    Bash completion has been installed to:
      /usr/local/etc/bash_completion.d
    ==> Summary
    🍺  /usr/local/Cellar/docker/20.10.12: 12 files, 56.8MB
    ==> Running `brew cleanup docker`...
    Disable this behaviour by setting HOMEBREW_NO_INSTALL_CLEANUP.
    Hide these hints with HOMEBREW_NO_ENV_HINTS (see `man brew`).
    :~
    $

I checked if the `docker` command is running.

    $ docker --version
    Docker version 20.10.2, build 2291f61
    :~
    $

I made a temporary directory named `flaskr` where I launch a web app.

    $ cd ~
    $ mkdir flaskr

I started a web app using a docker image.

    $ cd ~/flaskr
    $ docker run -it -p 80:8080 --rm kazurayam/flaskr-kazurayam:1.0.3
    Serving on http://0.0.0.0:8080

Now I can open a browser and visit

-   <http://127.0.0.1:80/>

then a page like this would be visible.

![flaskr just started](docs/images/flaskr_just_started.png)

This is the sample web app of ["Flask Tutorial"](https://flask.palletsprojects.com/en/2.0.x/tutorial/). It is *a basic blog application called Flaskr. Users will be able to register, log in, create posts, and edit or delete their own posts.* I justed typed in the sample codes as published.

I made a docker image which is publicly available at Docker Hub :

-   <https://hub.docker.com/repository/docker/kazurayam/flaskr-kazurayam>

## Scenario

1.  I will use **Flaskr** at `http://127.0.0.1` as a partner for me to develop a set of test scripts in Katalon Studio.

2.  I will open 2 Chrome browsers. On each, I will visit the Flaskr site and interact with it. I will keep 2 browsers open and operate on them simultaneously.

3.  On one browser, I will register a user **Alice** and make some posts.

4.  On another browser, I will register another user **Bob** and make some posts.

5.  Alice should be able to read the posts made by Bob. Bob should be able to read the posts made by Alice. My web ui test in Katalon Studio will check this conversation.
