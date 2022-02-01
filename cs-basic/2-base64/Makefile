base64encoder: base64encoder.o
	ld -o base64encoder base64encoder.o

base64encoder.o: base64encoder.asm
	nasm -f elf64 -g -F dwarf base64encoder.asm

clean:
	rm -f base64encoder base64encoder.o
