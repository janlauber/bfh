

# Operating systems -- Fall 2021
#  Project 1: Memory allocation simulation}
Emmanuel Benoist emmanuel.benoist@bfh.ch

Lukas Ith lukas.ith@bfh.ch

**Due date: Friday 17th of December 23:59:59
  inside git repository.**

## Description

The goal of this project is to write a program which simulates a
memory allocation system using a pure demand paging mechanism.

A rough description of the system may be:

1. a process requires a memory access by generating a virtual
  address reference.
1. the system takes this reference and split it in *page
    number* and *offset*.
1. it first looks into the *Translation Lookaside Buffer
    (TLB)* to see if this page is already referenced there. If the
  page may be found, the TLB returns the *frame number* where the
  page is stored. If the page cannot be found, the TLB returns a
  *TLB miss* value.
  
1. If the page may not be found in the TLB, then the system asks the
  page table if the page number generated above is already loaded in
  memory. If yes, then the program adds the required page (and the
  corresponding frame) to the TLB and returns the frame number
  corresponding to the page.
  
1. If the page number may not be found, neither in the TLB nor in
  the page table, then it should be loaded in memory. For that goal
  the system should look in the *free frame list* if there still
  is a free frame. This frame should then be marked as used, and its
  reference should be added to the page table as to the TLB.

1. If the system cannot find any free frame, it should retrurn an error.


## Parameters of the system

The system is defined by different parameters. These parameters can be set in the test program. A function is used to initialize the values inside your part of the code.

```C
int memory_init_data(int number_processes,
		     int free_frames_number,
		     int length_VPN_in_bits,
		     int length_PFN_in_bits,
		     int length_offset_in_bits,
		     int tlb_size);
```
The test program(s) will initialize only once the data. 

* _number_processes_ in this first part, the number of processes is always 1
* _free_frames_number_ the size of the list or array of frames. In this function you have to initialize this structure accordingly to its number of elements.
* _length_VPN_in_bits_ is the length of the Virtual Page Number in number of bits. 10 means, that we have VPN between 0 and 1023 (i.e. (2 power 10)-1).
* _length_PFN_in_bits_ is the length of Physical Frames Numbers in number of bits.
* _length_offset_in_bits_ is the same for the offset part of the address.
* _tlb_size_ you need to initialize a Translation Lookaside Buffer of that size.

### One more word about TLB

Normally replacing an existing entry is based on a *least
  recently used* scheme, that is if an entry must be replaced, the
victim to be evicted is the entry that wasn't used for the longest
time. For our project, we implement FIFO strategy. It may be sufficient to have an idea 
of the efficiency of the system.

## Some though about data structure

Your simulation program must probably implement at least the following
data structures:

* _free frame list_ the list of frame in physical memory not yet
  used. 
* _page table_ the table used to translate the page number into a
  frame number. 
* _TLB_ the table used to translate quickly a page number into a
  frame number. Physically this part is normally realised by an
  *associative memory* which can be seen as a hardware hash
  table. The size of the TLB must be very small compared to the page
  table. You will implement for this part the FIFO strategy. 


Many implementation are possible for these structures. In the
following subsection, you may find some suggestion. 

### The free frame list

The free frame list contains the list of all free frames of the
system. That is, in the beginning of the program, the free frame list
contain the list of all the frames of the system.

When the process needs to access to "new page" (a page not yet
in memory) then the memory allocation program gets a free frame for the
list, marks this frame as being used (or get it out of the list) and stores
the newly loaded page into it.

There are many ways to implement this free frame list.

One way
is to use an array of boolean. Free frames are designed by the value
*true* and used frames by the value *false*.  The
complexity of finding a free frame (if there is m frames in the
physical memory) is *O(m)*.

Another way to implement this list would be to use a linked list. At
the beginning the list is once again initialised whith all frames of
he system. When a process need to get a free frame, it just pickup the
head of the list (complexity *O(1)*) and when a process free
a frame, it can be added in the front of the list (*O(1)* too).

### The page table

The most natural way to implement a page table is to use a simple
array of integer. For this simulation we are using a one-level page
table and thus it should be easy to write a simple array of integer,
each cell containing the frame number where the page number
(represented by the offset in the array) is stored.

One should remember to initialise the page table with a special
value for the "*invalid entry*" that is a page that has not already been
loaded  into memory.

## The TLB

The TLB is probably the most complicated structure to
simulates. Normally it is a special hardware which may be seen as a
kind of hash table or dictionary.

The TLB is implement with a FIFO structure. It can be implemented using a linked list or a circular array.

## Some additional notes

For the project 1, one should have only one process asking for
memory. The requested virtual address are not randomly generated but
given explicitely (for example by calling a function
*request_memory()* which would allow you to
test the different scenarii explained above).

The function has the following prototype:
```C
int get_physical_address(uint64_t virtual_address,
			 int process_id,
			 uint64_t* physical_address,
			 int* tlb_hit);
```
It takes as arguments the virtual address and the process id. The value returned is 0 if the function could find a physical address, another value if it is not possible (for instance if the virtual address is out of the address space or if there is no free frame available).

Two arguments are passed by reference. They are used to get the answers to the function: the *physical_address* that is obtained by the calculation and *tlb_hit* that is true if the value was read out of TLB and false if not.


## Deliverables


* the source code (in C) with a make file
* Source must be commited and pushed inside your repository before the deadline.


## Tests
Your program can be tested with the file *test_lab1.c*. Your program should run without errors with this test. We will run also some other (random generated) tests that correspond to the description of the functions.


## Interface
Your progam must provide an implementation of the two folowing functions. Your program must be linkable and linked (using our Makefile) to the test program.

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