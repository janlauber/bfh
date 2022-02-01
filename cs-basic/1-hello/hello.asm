SECTION .data           ; Section containing initialised data

    msg: db "Hello world!", 10
    msgLength: equ $-msg

SECTION .bss            ; Section containing uninitialized data

SECTION .text           ; Section containing code

global _start           ; Linker needs this to find the entry point!

_start:

    ; Print the 'Hello world!' message with a newline
    mov rax, 1
    mov rdi, 1
    mov rsi, msg
    mov rdx, msgLength
    syscall

    mov rax, 60         ; Code for exit
    mov rdi, 0          ; Return a code of zero
    syscall             ; Make kernel call
