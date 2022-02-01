# 5-shell: Implement a Small UNIX Shell

## Overview and Goal

The goal of this task is to write a simple but functional UNIX shell in C. It
must:

* Print a shell prompt (`$`)
* Read multiple commands from standard input and execute them
* Support shell variables and expansion
* Support the variable `$?` to print out the return code of the last program
* Run programs in the background if desired
* Support redirection of standard input, output and error
* Support the pipeline operator (`|`)

See expected output below for details.

### Important Notes

* Executing any real shell (e.g. `/bin/bash`) through `exec()`, `popen()` or
  any other means is *not* a valid solution. All of the functionality must be
  implemented by yourself in `shell.c`!
* Do *not* use `isatty()` to check if your shell is running in a terminal (e.g.
  to display a prompt only in this case). The CI environment is not using a
  terminal.
* It is recommended to use `fflush()` to flush the output streams (otherwise,
  e.g. CI tests may not work properly)

## Expected Output

### Print a shell prompt (`$`) when ready

```
...
$
```

### Read multiple commands from standard input and execute them

```
$ echo Hello
Hello
$ expr 1 + 3
4
```

### Support shell variables and expansion

```
$ A=5
$ echo $A
5
$ echo ${A}BC
5BC
$ A=$A$A
echo $A
55
```

### Support checking the return code of the last program using `$?`

```
$ true
$ echo $?
0
$ false
$ echo $?
1
```

### Support running programs in the background with `&`

```
$ firefox &
$
```

After this, Firefox should be running and the shell accepting commands again.

### Support redirection of stdin, stdout and stderr with `>`, `<` and `2>`

```
$ echo Hello > test.txt
$ cat - < test.txt
Hello
```

### Support the pipeline operator `|`

```
$ echo Hello | tr l L | tr o '?'
HeLL?
```

## Evaluation

Total points possible: 7

Minimum number of points required to pass: 5

Points can be obtained as follows:

* 1 Point: Correct display of shell prompt
* 1 Point: Read and execute multiple commands
* 1 Point: Support shell variables and expansion
* 1 Point: Support `$?` for displaying the return code of the last program
* 1 Point: Support `&` for running programs in the background
* 1 Point: Support redirection of stdin, stdout and stderr
* 1 Point: Support pipes with `|`

## Push the Result to GitLab

To test your code and finally submit your task, your changes must be committed
and pushed to GitLab:

```
$ git add shell.c
....
$ git commit -m "solution for task 5"
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
