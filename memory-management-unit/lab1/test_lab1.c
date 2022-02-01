#include <stdio.h>
#include <assert.h>
#include "memory_management.h"

#define NUMBER_PROCESSES 1
#define FREE_FRAMES_NUMBER 9
#define VPN_LENGTH 6
#define PFN_LENGTH 4
#define OFFSET_LENGTH 8
#define TLB_SIZE 5

int
main()
{
  printf("Tests for the first lab part\n");
  printf("Tests \n");
  assert(1 == 1);

  printf("Initialisation of data structures\n");
  int r=memory_init_data(NUMBER_PROCESSES,   // number_processes,	
		   FREE_FRAMES_NUMBER,	// free_frames_number,	
		   VPN_LENGTH,	// length_VPN_in_bits,	
		   PFN_LENGTH,	// length_PFN_in_bits,	
		   OFFSET_LENGTH,	// length_offset_in_bits,	
		   TLB_SIZE);	// tlb_size
  assert(0==r);
  
  uint64_t virtual[] = {0x2345,0x23FF, 0x11FF,0xFFFFF, 0x11FF, 0x18FF,0x1908,0x11FF,0x2345,0x00FF,0x0100,0x0200,0x0300,0x0400,0x0500};
  size_t nb_cases = sizeof(virtual)/sizeof(uint64_t);
  uint64_t physical[nb_cases];
  printf("Size of the test = %ld\n",nb_cases);
  int pid =0;
  int tlb_hit = 0;
  int res = get_physical_address(virtual[0], pid,&physical[0],&tlb_hit);
  assert(0 == res);
  assert(0 == tlb_hit);
  
  printf("physical = %#lx, hit = %d\n",physical[0],tlb_hit);
  
  res = get_physical_address(virtual[1], pid,&physical[1],&tlb_hit);
  assert(0 == res);
  assert(1 == tlb_hit);
  assert((physical[0]>>OFFSET_LENGTH) == (physical[1]>>OFFSET_LENGTH));
  printf("physical = %#lx, hit = %d\n",physical[1],tlb_hit);


  res = get_physical_address(virtual[2], pid,&physical[2],&tlb_hit);
  assert(0 == res);
  assert(0 == tlb_hit);

  printf("Testing that the Frame is the same (for 2 different addresses)\n");
  assert((physical[2]>>OFFSET_LENGTH) != (physical[1]>>OFFSET_LENGTH));

  printf("Testing if the address is not too large\n");
  res = get_physical_address(virtual[3], pid,&physical[3],&tlb_hit);
  assert(0 != res);

  for(int i = 4;i<nb_cases-1;i++){
    res = get_physical_address(virtual[i], pid,&physical[i],&tlb_hit);
    assert(0 == res);
    if(8==i){
      printf("Test if item %d made a TLB hit\n",i);
      assert(1 == tlb_hit);
    }
  }
  

  printf("Test if the same addresses remain the same\n");
  assert(physical[0] == physical[8]);
  assert(physical[7] == physical[2]);
  printf("Test if different addresses remain the same\n");
  assert(physical[4] != physical[5]);
  res = get_physical_address(virtual[nb_cases-1], pid,&physical[nb_cases-1],&tlb_hit);
  printf("Test if an error is returned for empty free frame list\n");
  assert(1 != res);
  return 0;
}
