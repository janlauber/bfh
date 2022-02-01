# 2-base64: A Binary to Base64 Encoder (Assembly Language)

## Overview and Goal

The goal of this task is to create a program in assembly language, which can be
used as a converter for converting binary data into base64 encoding. Base64 is
widely used for transporting binary data in plain text files.

The basic idea of base64 is to define an alphabet (e.g. all lower- and upper-
case letters) and to map all possible binary values to this alphabet.
The normal base64 alphabet is {a-z, A-Z, 0-9, +, /}, which indeed consists of
64 characters.

As a byte has 256 possible values, it cannot be directly translated to the
base64 alphabet and a conversion must take place. For this, the binary input
data is split into groups of 6 bits (2^6 = 64). Each of these groups can then
be mapped to a character of the alphabet and used as output.

Using this approach, we may have a problem at the end of the input, if the
length of the input is not a multiple of 3 bytes (as we treat 3 bytes = 24 bits
at a time). There are three possibilities:

1. The input length is a multiple of 3: No problem, we can encode the final 3
   bytes as all the others before.

2. The last group consists of only 2 bytes (16 bits): In this case, we add two
   additional 0-bits at the end (=18 bits) and encode to three base64
   characters. Then, we add a "="-character at the end of the output as a
   marker to indicate this change.

3. The last group consists only of 1 byte (8 bits): The procedure is similar.
   We add four additional 0-bits at the end (=12 bits) and encode to two base64
   characters. We use "==" at the end as a marker in this case.

See [Base64 Wikipedia](http://en.wikipedia.org/wiki/Base64) for more details.

## Expected Output

The following is an example of the output your program is expected to generate:

```
$ ./base64encoder < inputfile
TmljZSwgdGhhbmtzIGZvciBkZWNvZGluZyBtZSEK
```

Your program is expected to read the binary input data from standard input and
print out the base64 representation on standard output. In the example above,
the shell's input redirection is used to read the input data from "inputfile".

## Conversion Example

Suppose, we have a file containing the following bytes:

> 01111100 10101010 01111000

For the conversion to base64, the bits are reorganized in groups of 6:

> 011111 001010 101001 111000

Every group represents a number from 0 to 63:

> 31 10 41 56

Using an encoding table containing our base64 alphabet, these numbers can be
converted to the corresponding base64 character.
See the [base64 encoding table](encoding-base64.png) for this conversion.

The final result thus will be

> fKp4

## Evaluation

Total points possible: 1

Minimum number of points required to pass: 1

You will obtain 1 point if your program converts any input data into valid
base64.

## Push the Result to GitLab

To test your code and finally submit your task, your changes must be committed
and pushed to GitLab:

```
$ git add base64encoder.asm
....
$ git commit -m "solution for task 2"
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
