#include <stdio.h>
#include <string.h> 
#include <unistd.h>
#include <dirent.h>
#include <stdlib.h>

// implement sort function for strings
void sort_strings(char **strings, int num_strings) {
    int i, j;
    char *temp;
    for (i = 0; i < num_strings - 1; i++) {
        for (j = i + 1; j < num_strings; j++) {
            if (strcmp(strings[i], strings[j]) > 0) {
                temp = strings[i];
                strings[i] = strings[j];
                strings[j] = temp;
            }
        }
    }
}

// return if a file is executable
// return 1 if executable, 0 if not
// use access() function
int is_executable(char *filename) {
    if (access(filename, X_OK|F_OK) == 0) {
        return 1;
    }
    return 0;
}

// return file names and directory names in given directory as an array of strings without hidden files 
// also add "*" to the end of each file name
// also add "/" to the end of each directory name
// also add "@" to the end of each symlink name
char **get_files_in_dir(char *dir_name, int *num_files) {
    DIR *dir;
    struct dirent *entry;
    char **file_names;
    int num_entries = 0;
    int i;
    dir = opendir(dir_name);
    if (dir == NULL) {
        printf("Error: could not open directory %s\n", dir_name);
        exit(1);
    }
    while ((entry = readdir(dir)) != NULL) {
        if (entry->d_name[0] != '.') {
            num_entries++;
        }
    }
    file_names = (char **) malloc(num_entries * sizeof(char *));
    rewinddir(dir);
    i = 0;
    while ((entry = readdir(dir)) != NULL) {
        if (entry->d_name[0] != '.') {
            file_names[i] = (char *) malloc(strlen(entry->d_name) + 2);
            strcpy(file_names[i], entry->d_name);
            if (entry->d_type == DT_REG) {
                char* whole_file_path;
                whole_file_path = malloc(strlen(dir_name)+1+4);
                strcpy(whole_file_path, dir_name);
                strcat(whole_file_path, "/");
                strcat(whole_file_path, file_names[i]);
                // printf("%s\n", whole_file_path);
                // add "*" to the end of each file name when it is a regular file and not a symlink and not a directory and executable
                if (is_executable(whole_file_path) == 1) {
                    strcat(file_names[i], "*");
                }
            } else if (entry->d_type == DT_DIR) {
                strcat(file_names[i], "/");
            } else if (entry->d_type == DT_LNK) {
                strcat(file_names[i], "@");
            }
            i++;
        }
    }
    closedir(dir);
    *num_files = num_entries;
    sort_strings(file_names, i);
    return file_names;
}


// print directory and files in given directory to stdout not recursively
void print_dir(char *dir_name) {
    int num_files;
    char **files = get_files_in_dir(dir_name, &num_files);
    if (!files) {
        printf("Error getting files in directory\n");
        return;
    }
    for (int i = 0; i < num_files; i++) {
        printf("%s\n", files[i]);
    }
    for (int i = 0; i < num_files; i++) {
        free(files[i]);
    }
    free(files);
}

// Function: main
// Language: c
// Path: gitlab.ti.bfh.ch/laubj5/3-ls/ls.c
int main(int argc, char **argv) {
  // if no arguments are provided print current directory
  if (argc == 1) {
    print_dir(".");
  }
  // if only one argument is provided print directory and files in given directory
  else if (argc == 2) {
    print_dir(argv[1]);
  }
  // if more than one argument is provided print error
  else {
    printf("Error: too many arguments\n");
  }

}
