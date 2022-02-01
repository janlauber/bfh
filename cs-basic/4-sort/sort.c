// implement function sort
// Author: Jan Lauber
// Date: 2021-12-25
//-----------------------------------------------------------------------------


#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// function sort which sorts an input array
void sort(char *array[], int size) {
    int i, j;
    char *temp;
    for (i = 0; i < size; i++) {
        for (j = i + 1; j < size; j++) {
            if (strcmp(array[i], array[j]) > 0) {
                temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
    }
}

// function to sort array backwards (descending)
void sort_desc(char *array[], int size) {
    int i, j;
    char *temp;
    for (i = 0; i < size; i++) {
        for (j = i + 1; j < size; j++) {
            if (strcmp(array[i], array[j]) < 0) {
                temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
    }
}

int main(int argc, char **argv) {
  // Check if argument -r is given
  int reverse = 0;
  if (argc > 1 && strcmp(argv[1], "-r") == 0) {
    reverse = 1;
  }

  // Read input line by line
  char *line = NULL;
  size_t len = 0;
  ssize_t read;
  char **array = NULL;
  int size = 0;
  while ((read = getline(&line, &len, stdin)) != -1) {
    // Allocate memory for new array
    array = realloc(array, (size + 1) * sizeof(char *));
    // Allocate memory for new string
    array[size] = malloc(sizeof(char) * (read + 1));
    // Copy string from line to array
    strcpy(array[size], line);
    // Remove trailing newline
    array[size][read - 1] = '\0';
    // Increment size
    size++;
  }

  // Sort array
  if (reverse) {
    sort_desc(array, size);
  } else {
    sort(array, size);
  }

  // Print array
  for (int i = 0; i < size; i++) {
    printf("%s\n", array[i]);
  }

  // Free memory
  for (int i = 0; i < size; i++) {
    free(array[i]);
  }
  free(array);
  free(line);

  return 0;
}
