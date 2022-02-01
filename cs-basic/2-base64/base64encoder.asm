SECTION .data           ; Section containing initialised data
    Base64Characters:  db      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
    OutputStr:          db      "    "
    OutputStrLen:       equ     $-OutputStr

SECTION .bss            ; Section containing uninitialized data
    BufLen:             equ     3
    Buf:                resb    BufLen

SECTION .text           ; Section containing code

global _start           ; Linker needs this to find the entry point!

_start:

    call read           ; call read proc
    
    cmp rax, 0          ; when input = 0 -> quit
    je exit
    
    cmp rax, 1          ; when input 1 byte -> jump encoding with 1 byte
    je enc_chr1

    cmp rax, 2          ; when input 2 byte -> jump encoding with 2 byte
    je enc_chr2

    cmp rax, 3          ; when input 3 byte -> jump encoding with 3 byte
    je enc_chr3

enc_chr1:
    xor rax, rax            
    mov al, byte [Buf]      

    call first_chr

    and al, 0x03
    shl al, 4
    mov al, byte [Base64Characters+rax]
    mov byte [OutputStr+1], al

    mov al, 0x3d
    mov byte [OutputStr+2], al

    mov byte [OutputStr+3], al

    call printEnc
    jmp _start

enc_chr2:
    xor rax, rax
    xor rbx, rbx
    mov al, byte [Buf]
    mov bl, byte [Buf+1]

    call first_chr

    call second_chr

    and bl, 0x0f
    shl bl, 2
    mov bl, byte [Base64Characters+rbx]
    mov byte [OutputStr+2], bl

    mov bl, 0x3d
    mov byte [OutputStr+3], bl

    call printEnc
    jmp _start

enc_chr3:
    xor rax, rax
    xor rbx, rbx
    xor rdx, rdx
    mov al, byte [Buf]
    mov bl, byte [Buf+1]
    mov dl, byte [Buf+2]

    call first_chr

    call second_chr

    call third_chr

    and dl, 0x3f
    mov dl, byte [Base64Characters+rdx]
    mov byte [OutputStr+3], dl

    call printEnc
    jmp _start

first_chr:
    push rax
    and al, 0xfc
    shr al, 2
    mov al, byte [Base64Characters+rax]
    mov byte [OutputStr], al
    pop rax
    ret

second_chr:
    and al, 0x03
    shl al, 4

    push rbx
    and bl, 0xf0
    shr bl, 4
    or al, bl
    mov al, byte [Base64Characters+rax]
    mov byte [OutputStr+1], al
    pop rbx
    ret

third_chr:
    and bl, 0x0f
    shl bl, 2

    push rdx                       
    and dl, 0xc0                   
    shr dl, 6                      
    or bl, dl                      
    mov bl, byte [Base64Characters+rbx]     
    mov byte [OutputStr+2], bl          
    pop rdx
    ret

exit:
    mov rax, 60             ; exit
    mov rdi, 0              ; on
    syscall                 ; success

read:
    mov rax, 0
    mov rbx, 0
    mov rdi, 0
    mov rsi, Buf
    mov rdx, BufLen
    syscall
    ret

printEnc:
    mov rax, 1              ; sys_write
    mov rdi, 1              ; stdout
    mov rsi, OutputStr      ; source buffer
    mov rdx, OutputStrLen   ; number of bytes to write
    syscall
    ret