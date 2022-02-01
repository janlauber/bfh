#include <stdint.h>

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
