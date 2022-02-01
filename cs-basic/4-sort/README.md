# 4-sort: Sort a List of Strings in C

## Overview and Goal

The goal of this task is to write a program in C, which takes several lines of
input from standard input and prints them out in sorted order to standard
output.

By default, lines are sorted in ascending order. If the program is invoked with
an optional parameter `-r`, the lines have to be sorted in descending order.

Hint: For testing, lines can be entered interactively in the terminal and
`<Control-D>` can be used to properly terminate the input. This is also used
in the examples below, thus `<Control-D>` is not part of the lines to be
sorted!

## Expected Output

### Default Output

The following shows two invocations of the program without any parameter
(sorting in *ascending* order):

```
$ ./sort
a
v
b
<Control-D>
a
b
v
```

```
$ ./sort
bonjour
hallo
gruesseuch
<Control-D>
bonjour
gruesseuch
hallo
```

### Output with Parameter `-r`

The following shows two invocations of the program with parameter `-r`
(sorting in *descending* order):

```
$ ./sort -r
a
v
b
<Control-D>
v
b
a
```

```
$ ./sort -r
bonjour
hallo
gruesseuch
<Control-D>
hallo
gruesseuch
bonjour
```

## Sorting Lines from a File

By redirecting standard input, lines from a file may also be sorted:

```
$ cat test.txt
zzzz
gggg
abcd
def
$ ./sort < test.txt
abcd
def
gggg
zzzz
$ ./sort -r < test.txt
zzzz
gggg
def
abcd
```

## Evaluation

Total points possible: 1

Minimum number of points required to pass: 1

You will obtain 1 point if your program correctly sorts any input data
in ascending and descending order.

## Push the Result to GitLab

To test your code and finally submit your task, your changes must be committed
and pushed to GitLab:

```
$ git add sort.c
....
$ git commit -m "solution for task 4"
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
