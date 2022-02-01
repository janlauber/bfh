// implement shell
// Author: Jan Lauber
// Date: 2021-12-25
//-----------------------------------------------------------------------------

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Global variables
int shell_exit_status = -1;
// shell env variables
typedef struct {
    char *name;
    char *value;
} env_var;
env_var *env_vars = NULL;
int env_vars_size = 0;


// function to set shell env variables
void set_env(char *name, char *value) {
    // Check if env variable already exists
    int i;
    for (i = 0; i < env_vars_size; i++) {
        if (strcmp(env_vars[i].name, name) == 0) {
            // Replace value
            free(env_vars[i].value);
            env_vars[i].value = malloc(sizeof(char) * (strlen(value) + 1));
            strcpy(env_vars[i].value, value);
            return;
        }
    }
    // Add new env variable
    env_vars = realloc(env_vars, (env_vars_size + 1) * sizeof(env_var));
    env_vars[env_vars_size].name = malloc(sizeof(char) * (strlen(name) + 1));
    strcpy(env_vars[env_vars_size].name, name);
    env_vars[env_vars_size].value = malloc(sizeof(char) * (strlen(value) + 1));
    strcpy(env_vars[env_vars_size].value, value);
    env_vars_size++;
}

// function to get shell env variable by name
char *get_env(char *name) {
    // Check if env variable exists
    int i;
    for (i = 0; i < env_vars_size; i++) {
        if (strcmp(env_vars[i].name, name) == 0) {
            return env_vars[i].value;
        }
    }
    return NULL;
}

// implement the execute function which reads a line from stdin and handles the command execution
void executeHandler(char *line) {
  // remove '\n' from end of line
  line[strcspn(line, "\n")] = 0;
  // get command and arguments
  char *command = strtok(line, " ");

  // allocate dynamic memory for arguments
  int arguments_size = 0;
  char **arguments = malloc(sizeof(char *) * 10);
  int i = 0;
  while (1) {
    char *argument = strtok(NULL, " ");
    if (argument == NULL) {
      break;
    }
    arguments[i] = argument;
    i++;
    arguments_size++;
  }
  arguments[i] = NULL;

  // Check if command is exit
  if (strcmp(command, "exit") == 0) {
    shell_exit_status = 0;
    return;
  }

  // Check if command contains '=' and set env variable
  if (strchr(command, '=') != NULL) {
    // char before '=' is the key
    char *key = strtok(command, "=");
    // char after '=' is the value
    char *value = strtok(NULL, "=");

    // TODO: check if value contains env variables and replace them with their values

    // set env variable
    set_env(key, value);
    set_env("?", "0");
    return;
  }

  // Check if command is printenv
  if (strcmp(command, "printenv") == 0) {
    // get key value pairs of env_variables and print them
    for (int i = 0; i < env_vars_size; i++) {
      printf("%s=%s\n", env_vars[i].name, env_vars[i].value);
    }
    set_env("?", "0");
    return;
  }

  // Check if command is cat
  if (strcmp(command, "cat") == 0) {
    // if argument[0] is '-' and argument[1] starts with '<'
    if (strcmp(arguments[0], "-") == 0 && strncmp(arguments[1], "<", 1) == 0) {
      // open file
      FILE *file = fopen(arguments[1] + 1, "r");
      if (file == NULL) {
        printf("cat: %s: No such file or directory\n", arguments[1] + 1);
        set_env("?", "1");
        return;
      }
      // read file
      char *line = NULL;
      size_t len = 0;
      while (getline(&line, &len, file) != -1) {
        printf("%s\n", line);
      }
      fclose(file);
      set_env("?", "0");
      return;
    }

    // get file name
    char *file_name = arguments[0];
    // open file
    FILE *file = fopen(file_name, "r");
    if (file == NULL) {
      printf("cat: %s: No such file or directory\n", file_name);
      set_env("?", "1");
      return;
    }
    // read file
    char line[256];
    while (fgets(line, sizeof(line), file) != NULL) {
      printf("%s\n", line);
    }
    fclose(file);
    set_env("?", "0");
    return;
  }

  // Check if command is rm
  if (strcmp(command, "rm") == 0) {
    // get file name
    char *file_name = arguments[0];
    // remove file
    if (remove(file_name) != 0) {
      printf("rm: %s: No such file or directory\n", file_name);
      set_env("?", "1");
      return;
    }
    set_env("?", "0");
    return;
  }

  // Check if command is echo
  if (strcmp(command, "echo") == 0) {

    // check if 2nd argument is a '>'
    if (arguments_size > 1 && strcmp(arguments[1], ">") == 0) {
      // get file name
      char *file_name = arguments[2];
      // get file content
      char *file_content = arguments[0];
      // open file
      FILE *file = fopen(file_name, "w");
      // write file content to file
      fprintf(file, "%s", file_content);
      // close file
      fclose(file);
      set_env("?", "0");
      return;
    } else {

      // check if argument contains '$'
      if (strchr(arguments[0], '$') != NULL) {
        // check every character of argument
        for (int i = 0; i < strlen(arguments[0]); i++) {
          // if character is '$'
          if (arguments[0][i] == '$') {
            // get env variable name until arguments[0][strlen(arguments[0]) - 1]
            char *env_variable_name = malloc(sizeof(char) * (strlen(arguments[0]) - i));
            strncpy(env_variable_name, arguments[0] + i + 1, strlen(arguments[0]) - i - 1);
            // get env variable value
            char *env_variable_value = get_env(env_variable_name);
            // replace $env_variable_name with env_variable_value
            strcpy(arguments[0] + i, env_variable_value);
            // free env_variable_name
            free(env_variable_name);
          }
        }
      }

      // first check any arguments if some start with '$'
      for (int i = 0; i < arguments_size; i++) {
          // get argument value size
          int value_size = strlen(arguments[i]);

          int vartype = 0;

          int split_start = 0;
          int split_end = 0;
          char *var_value = NULL;
          
          // variable with ${varname}
          for (int j = 0; j < value_size; j++) {
            // print argument value
            if (arguments[i][j] == '$') {
              if (arguments[i][j + 1] == '{') {
                vartype = 1;
                // get variable name
                char *var_name = strtok(&arguments[i][j + 2], "}");
                // get variable value
                var_value = get_env(var_name);

                if (var_value == NULL) {
                  var_value = "";
                }              
                split_start = j;
                split_end = j + strlen(var_name) + 3;
              }
            }
          }

          if (vartype == 1) {
            // split argument[i] into two parts
            // part 1: before ${varname}
            // part 2: after ${varname}
            char *part1 = malloc(sizeof(char) * (split_start + 1));
            char *part2 = malloc(sizeof(char) * (value_size - split_end + 1));
            strncpy(part1, arguments[i], split_start);
            strncpy(part2, &arguments[i][split_end], value_size - split_end);
            part1[split_start] = '\0';
            part2[value_size - split_end] = '\0';

            // set argument[i] to part1 + var_value + part2
            char *new_argument = malloc(sizeof(char) * (strlen(part1) + strlen(var_value) + strlen(part2) + 1));
            strcpy(new_argument, part1);
            strcat(new_argument, var_value);
            strcat(new_argument, part2);
            arguments[i] = new_argument;
          }

          
          // variable with starting $
          if (arguments[i][0] == '$') {
            // TODO: don't delete other arguments after this and remove spaces

            // get env variable name
            char *env_var_name = strtok(arguments[i], "$");
            // get env variable value
            char *env_var_value = get_env(env_var_name);
            // if env variable value is NULL, set it to empty string
            if (env_var_value == NULL) {
              env_var_value = "";
            }
            // replace from '$' to end with env variable value to ' '
            char *new_argument = malloc(sizeof(char) * (strlen(env_var_value) + 1));
            strcpy(new_argument, env_var_value);
            arguments[i] = new_argument;
          }
      }
      // print arguments and last no whitespace
      for (int i = 0; i < arguments_size; i++) {
        printf("%s", arguments[i]);
        if (i != arguments_size - 1) {
          printf(" ");
        }
      }
      printf("\n");
      set_env("?", "0");
      return;
    }

  }
}



int main(int argc, char **argv) {
  // print $ befor stdin
  printf("$ ");
  // read line from stdin
  char *line = NULL;
  size_t len = 0;
  ssize_t read;
  while ((read = getline(&line, &len, stdin)) != -1) {
    if (shell_exit_status != -1) {
        // Exit shell
        exit(shell_exit_status);
    }
    // If line is empty, print $ and continue
    if (strlen(line) == 1) {
      printf("$ ");
      continue;
    }
    // execute line
    executeHandler(line);

    if (shell_exit_status != -1) {
        // Exit shell
        exit(shell_exit_status);
    }

    // print $ before stdin
    printf("$ ");
  }
  return 0;
}
