CC=gcc
CFLAGS= -g
EXEC=test_lab1

all: $(EXEC)

test_lab1: test_lab1.c memory_management.h memory_management.c
	$(CC) $(CFLAGS) -o test_lab1 test_lab1.c memory_management.c

clean:
	rm -f $(EXEC)
	rm -f *~
