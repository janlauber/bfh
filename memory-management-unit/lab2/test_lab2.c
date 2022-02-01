#include <stdio.h>
#include <assert.h>
#include "memory_management.h"
#include <pthread.h>
#include <stdlib.h>

#define NUMBER_PROCESSES 10
#define FREE_FRAMES_NUMBER 96
#define VPN_LENGTH 6
#define PFN_LENGTH 4
#define OFFSET_LENGTH 8
#define TLB_SIZE 5



void make_tests(int pid){
  uint64_t virtual[] = {0x2345,0x23FF, 0x11FF,0xFFFFF, 0x11FF, 0x18FF,0x1908,0x11FF,0x2345};
  size_t nb_cases = sizeof(virtual)/sizeof(uint64_t);
  uint64_t physical[nb_cases];
  int tlb_hit = 0;
  int res = get_physical_address(virtual[0], pid,&physical[0],&tlb_hit);
  assert(0 == res);
  //assert(0 == tlb_hit);
  
  printf("physical = %#lx, hit = %d\n",physical[0],tlb_hit);
  
  res = get_physical_address(virtual[1], pid,&physical[1],&tlb_hit);
  assert(0 == res);
   assert((physical[0]>>OFFSET_LENGTH) == (physical[1]>>OFFSET_LENGTH));
  printf("physical = %#lx, hit = %d\n",physical[1],tlb_hit);


  res = get_physical_address(virtual[2], pid,&physical[2],&tlb_hit);
  assert(0 == res);
 
  printf("Testing that the Frame is the same (for 2 different addresses)\n");
  assert((physical[2]>>OFFSET_LENGTH) != (physical[1]>>OFFSET_LENGTH));

  printf("Testing if the address is not too large\n");
  res = get_physical_address(virtual[3], pid,&physical[3],&tlb_hit);
  assert(0 != res);

  for(int i = 4;i<nb_cases;i++){
    res = get_physical_address(virtual[i], pid,&physical[i],&tlb_hit);
    assert(0 == res);
  }
  printf("Test if the same addresses remain the same\n");
  assert(physical[0] == physical[8]);
  assert(physical[7] == physical[2]);
  printf("Test if different addresses remain different\n");
  assert(physical[4] != physical[5]);


}

void*
run_test(void * in)
{
  int pid = ((int*)in)[0];
  printf("pid = %d\n",pid);

  make_tests(pid);

}


int
main()
{
  printf("Tests for the first lab part\n");
  printf("Tests \n");
  assert(1 == 1);

  memory_init_data(NUMBER_PROCESSES,   // number_processes,	
		   FREE_FRAMES_NUMBER,	// free_frames_number,	
		   VPN_LENGTH,	// length_VPN_in_bits,	
		   PFN_LENGTH,	// length_PFN_in_bits,	
		   OFFSET_LENGTH,	// length_offset_in_bits,	
		   TLB_SIZE);	// tlb_size

  printf("First we test if the program still works with one single process\n");

  make_tests(0);
  
  int nb=NUMBER_PROCESSES;
  printf("Starts %d independent threads\n",nb);
  pthread_t arr_threads[NUMBER_PROCESSES];

  int thread_args[NUMBER_PROCESSES];
  for(int i = 0; i < NUMBER_PROCESSES; i++) {
    thread_args[i] = i;
    pthread_create(arr_threads + i, NULL, run_test, (void*)(thread_args + i));
  }

  for(int i=0;i<NUMBER_PROCESSES;i++)
    {
      pthread_join (arr_threads[i], NULL);
    }

  memory_free_data();
  
  return 0;
}
