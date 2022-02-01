

# Operating systems -- Fall 2021
#  Project 2: Multi-threading
Emmanuel Benoist emmanuel.benoist@bfh.ch

Lukas Ith lukas.ith@bfh.ch

**Due date: Friday 14th of January 2022 23:59:59
  inside git repository.**

## Description
You need to adapt your program developped in the first part of the lab to be thread safe.

We provide a second test file. You are also responsible to write tests by yourself to check if your program works well. Passing our small test does not mean that your program is thread safe (it should pass even if you change nothing in your program).

We will test your program with the given test file and also with another secret test program that generates random inputs.



## Interface
Your progam must provide an implementation of the two folowing functions. Your program must be linkable and linked (using our Makefile) to the test program.

This time, the number of processes is not 1 anymore, and different processes are represented by the different threads. Each thread gets a different number (pid), that is used to call the _get_physical_address_ function.

```Clanguage
int memory_init_data(int number_processes,
		     int free_frames_number,
		     int length_VPN_in_bits,
		     int length_PFN_in_bits,
		     int length_offset_in_bits,
		     int tlb_size);

int get_physical_address(uint64_t virtual_address,
			 int process_id,
			 uint64_t* physical_address,
			 int* tlb_hit);
```