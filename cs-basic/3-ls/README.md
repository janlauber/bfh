# 3-ls: Implement `ls` in C

## Overview and Goal

The goal of this task is to write your own implementation of the GNU/Linux `ls`
command in the C programming language.

Of course, not all the functionality of the original `ls` has to be
implemented: Your program must only behave like the original, invoked with `-1`
and `-F`. It must also support a single, optional parameter specifying the
file *or* directory to be listed. If this parameter is not given, the contents
of the current directory are listed. Otherwise, either the single file, or the
contents of the given directory are listed.

## Expected Output

The program must have the exact same behavior as the GNU/Linux `ls` command
with the options `-1` and  `-F`.

Important: Directories are listed with `/` at the end, executable files with
`*` and symlinks with `@`! See `man ls` for more details.

The following is an example output of GNU/Linux `ls`:

```
$ ls -1 -F
ls*
ls.c
Makefile
README.md
symlink@
```

### Invocation without Parameter

If invoked without any parameter, the contents of the current directory are
listed:

```
$ ./ls
ls*
ls.c
Makefile
README.md
symlink@
```

### Invocation with Optional Directory

If a directory is given as parameter, its contents are listed:

```
$ ./ls ..
1-hello/
2-base64/
3-ls/
4-sort/
5-shell/
README.md
VM-Setup.md
```

### Invocation with Optional File

If a file is given as parameter, only the file is listed:

```
$ ./ls /etc/hostname
/etc/hostname
```

## Evaluation

Total points possible: 5

Minimum number of points required to pass: 4

Points can be obtained as follows:

* 1 Point: Invocation without any parameter in any directory
* 1 Point: Invocation with any directory as parameter
* 1 Point: Invocation with any file as parameter
* 1 Point: Correct identification of directories ("/") and executables ("*")
* 1 Point: Correct identification of symlinks ("@")

## Push the Result to GitLab

To test your code and finally submit your task, your changes must be committed
and pushed to GitLab:

```
$ git add ls.c
....
$ git commit -m "solution for task 3"
....
$ git push origin master
```

You may make as many commits as you want; when grading the task, the latest
commit at that point in time will be used.

## Get Feedback

You can inspect the results of the automated tests run by the GitLab CI
([https://gitlab.ti.bfh.ch](https://gitlab.ti.bfh.ch)). For this, go to
"CI/CD > Jobs" in your project. In the output of the test job, the amount of
points given will also be indicated.
