# 1-hello: Hello World in Assembly Language

## Overview and Goal

The goal of this task is to create a small program in assembly language. It
has to write the string `Hello world!`, with a line feed at the end, to
standard output. For this, the file [hello.asm](hello.asm) must be completed.

There is a [Makefile](Makefile) which can be used for assembling your program
by simply typing `make` in your shell/terminal.

## Expected Output

The following is an example of the output your program is expected to generate:

```
$ ./hello
Hello world!
$ ./hello
Hello world!
```

## Evaluation

Total points possible: 1

Minimum number of points required to pass: 1

You will obtain 1 point if your program prints the correct output according to
the specification given above.

## Push the Result to GitLab

To test your code and finally submit your task, your changes must be committed
and pushed to GitLab:

```
$ git add hello.asm
....
$ git commit -m "solution for task 1"
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
