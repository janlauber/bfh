CC=gcc
CFLAGS= -g
EXEC=test_lab2
LDFLAGS= -lpthread

all: $(EXEC)

test_lab2: test_lab2.c memory_management.h memory_management.c
	$(CC) $(CFLAGS) -o test_lab2 test_lab2.c memory_management.c $(LDFLAGS)

clean:
	rm -f $(EXEC)
	rm -f *~
