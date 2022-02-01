# Introduction

To be able to work on the different tasks, a (virtual) machine running some
GNU/Linux system is required. We recommend "Debian GNU/Linux (stable)", but
basically every other distribution should work as well.

*Important: Different tool versions (e.g. compiler or assembler) may behave
differently! For grading your solutions, a virtual machine running Debian
GNU/Linux (stable) is used. If you use another distribution for development,
it is up to you to ensure that your solutions compile and work correctly in
the grading environment!*

If you already have a running GNU/Linux system, you do not need to set up a new
one. You may also use a different virtualizing solution like VMWare to set up
the machine.

# Installing Debian GNU/Linux

We will now install Debian GNU/Linux in VirtualBox. First, download the
required files:

* [VirtualBox](https://www.virtualbox.org/wiki/Downloads) for running your VM
* [Debian netinst](https://www.debian.org/distrib/netinst) to install Debian
  (choose small CD version for "amd64"!)

After downloading, first install VirtualBox and start it. Then, configure a new
virtual machine for the Debian installation:

1. **Create a new virtual machine**

2. **Configure the following settings:**
  * Name: "bti1021" (or some other name of your choice)
  * Type: "Linux"
  * Version: "Debian (64 bits)".
    Note: If you can only select the 32 bit version, you might have to enable
    virtualization (VT) in the BIOS/UEFI settings of your machine.
  * Memory: At least 2 GB (2048 MB) recommended - or more
  * Virtual Hard Disk: Recommended are 15 GB -- or more as well
    (use the default type "VDI", with dynamically allocated space)

3. **Start the new virtual machine and select the Debian ISO you have
   downloaded as boot medium**

4. **Follow the steps in the Debian installer to install the system**
  * We recommend to use English as language and "United States (en_US.UTF-8)"
    as locale.
  * Choose your keyboard layout *correctly!* This is very important as a wrong
    layout may for instance cause problems entering passwords etc.
  * Let the installer erase the whole disk (this will NOT erase your real disk,
    just the new virtual one)
  Refer to the [Debian documentation](https://www.debian.org/doc/) for further
  details.

5. **Add your user to the "sudo" group.**
  You should normally not work as user "root" (the "root" user is the system
  administrator account in UNIX-like systems), but use the utility "sudo"
  when you need to perform administrative tasks. For this, your normal user
  must be in the sudo group:
  * Login to the system and open a shell/terminal
  * Switch to user "root" using command "su -"
  * Add your user to the group with "adduser USER sudo" (where "USER" is
    the login name of your normal user)
  * Completely log out of the system and login again

6. **Optional: install the VirtualBox guest additions.**
  * In the VirtualBox menu, select "Install Guest Addition CD" and refer to
    VirtualBox documentation for further instructions. With the additions
    installed, you can use copy and paste between your host and the virtual
    machine, mount shared folders etc.

## Install the Required Software Packages

The shell command used to install software packages in Debian is:

```
sudo apt install <package1> <package2> ...
```

Use it to install the following packages:

* Packages required for the course: "git", "build-essential", "nasm",
  "python3-minimal", "manpages-dev", "xxd", "bless", "clang-format",
  "clang-tidy", "autoconf", "automake"
* A text editor of your choice, e.g. "vim", "emacs", "gedit" etc.

## System Upgrades

From now on, you should regularly update your system:

```
sudo apt update && sudo apt full-upgrade
```

# git Configuration

In order to properly annotate your commits, git needs to be set up correctly.
As your normal user, use the following commands to configure your identity
(be sure to use your own name!):

```
git config --global user.name "Lastname Firstname"
git config --global user.email "firstname.lastname@students.bfh.ch"
```

*Attention: if you are already using git on the same machine (and user),
this overrides your global configuration! In this case, it might be better to
use the "--local" option and set the configuration per-repository.
Type "git help config" to find out more.*
