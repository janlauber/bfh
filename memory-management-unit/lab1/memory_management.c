#include <stdio.h>
#include <stdlib.h>
#include "memory_management.h"

/*============*/
/*= Globals =*/
/*============*/
/*
 * Memory pointer
 */
int *memory;

/* 
 * TLB structure
 */
struct TLB *tlb;
struct TLB {
	int page_number;
	int frame_number;
	int last_access;
};

/*
 * Usefull constants for the memory management
 */
int *page_table;
int g_page_size;
int g_tlb_size;
int g_free_frames;
int g_offset;




int memory_init_data(int number_processes,
		     int free_frames_number,
		     int length_VPN_in_bits,
		     int length_PFN_in_bits,
		     int length_offset_in_bits,
		     int tlb_size)                
{
	// Initialize global variables
	g_page_size = 1 << length_VPN_in_bits;
	g_tlb_size = tlb_size;
	g_free_frames = free_frames_number;
	g_offset = length_offset_in_bits;

	// Initialize frame list with calloc
	memory = calloc(free_frames_number, sizeof(int));
	if (memory == NULL) {
		return -1;
	}

	// Initialize TLB with calloc
	tlb = calloc(tlb_size, sizeof(struct TLB));
	if (tlb == NULL) {
		return -1;
	}

	// Initialize page table with calloc
	page_table = calloc(number_processes, sizeof(uint64_t));
	if (page_table == NULL) {
		return -1;
	}

	return 0;
}


int get_physical_address(uint64_t virtual_address,
			 int process_id,
			 uint64_t* physical_address,
			 int* tlb_hit)
{
	// Calculate VPN and set the offset
	int VPN = virtual_address >> g_offset;
	int offset = virtual_address & ((1 << g_offset) - 1);

	// Check if the VPN is too big
	if (VPN > g_page_size) {
		printf("Error: get_physical_address: VPN out of bounds\n");
		return -1;
	}

	// Check if VPN is in TLB
	int i;
	for (i = 0; i < g_tlb_size; i++) {
		if (tlb[i].page_number == VPN) {
			*tlb_hit = 1;
			*physical_address = tlb[i].frame_number << g_offset | offset;
			return 0;
		}
	}

	// TLB miss
	*tlb_hit = 0;

	// Check if VPN is in page table
	if (page_table[process_id] & (1 << VPN)) {
		// VPN is in page table
		// Find free frame
		for (int i = 0; i < g_free_frames; i++) {
			if (memory[i] == 0) {
				// Found free frame
				// Update TLB
				tlb[i].page_number = VPN;
				tlb[i].frame_number = i;
				tlb[i].last_access = 0;
				// Update page table
				page_table[process_id] |= (1 << VPN);
				// Update memory
				memory[i] = 1;
				// Update physical address
				*physical_address = i << g_offset | offset;
				return 0;
			}
		}
		// No free frame found
		printf("Error: get_physical_address: No free frame found\n");
		return -1;
	}

	// Create new page if memory is not full
	if (g_free_frames > 0) {
		// Find free frame
		for (int i = 0; i < g_free_frames; i++) {
			if (memory[i] == 0) {
				// Found free frame
				// Update TLB
				tlb[i].page_number = VPN;
				tlb[i].frame_number = i;
				tlb[i].last_access = 0;
				// Update page table
				page_table[process_id] |= (1 << VPN);
				// Update memory
				memory[i] = 1;
				// Update physical address
				*physical_address = i << g_offset | offset;
				return 0;
			}
		}
		// No free frame found
		printf("Error: get_physical_address: No free frame found\n");
		return -1;
	}

	printf("Error: get_physical_address: no free frames\n");
	return -1;
}
