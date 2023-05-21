#include <fcntl.h>
#include <stdarg.h>
#include <stddef.h>
#include <stdint.h>
#include <time.h>

#define TIMEOUT 5
#define MILLION 1000000

#if defined(__linux__)
# include <linux/limits.h>
#elif defined(__APPLE__)
# define PATH_MAX 1024
#else
# error "idk"
#endif

#include <semaphore.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <unistd.h>

#define LENGTH(X) (sizeof(X) / sizeof(X[0]))

__attribute__((__format__(printf, 1, 2))) static size_t
read_option(const char *restrict prompt, ...);

/* TODO: remove this later */
#include <assert.h>

#include "common.h"

typedef void (*board_func)(sharedboard *, sem_t[BOARD_ROWS][BOARD_COLS][CELL_SEM_LAST]);

struct menu_item {
    board_func func;
    const char *name;
};

static void *init_mem(const char *restrict name, size_t size);

#define INIT_DATABASE() init_mem(DATABASE, sizeof(boardlist))

static void add_text(sharedboard *board,
                     sem_t sems[BOARD_ROWS][BOARD_COLS][CELL_SEM_LAST]);
static void add_image(sharedboard *board,
                      sem_t sems[BOARD_ROWS][BOARD_COLS][CELL_SEM_LAST]);
static void swap_cells(sharedboard *board,
                       sem_t sems[BOARD_ROWS][BOARD_COLS][CELL_SEM_LAST]);

static void todo(sharedboard *board, sem_t sems[BOARD_ROWS][BOARD_COLS][CELL_SEM_LAST]);

void
todo(sharedboard *board, sem_t sems[BOARD_ROWS][BOARD_COLS][CELL_SEM_LAST]) // NOLINT
{
    puts("To be implemented!");
}

void
add_image(sharedboard *board, sem_t sems[BOARD_ROWS][BOARD_COLS][CELL_SEM_LAST])
{
    todo(board, sems);
}

void
add_text(sharedboard *board, sem_t sems[BOARD_ROWS][BOARD_COLS][CELL_SEM_LAST])
{
    size_t row, col;
    char buf[256];

    struct timespec spec = { .tv_sec = 0, .tv_nsec = TIMEOUT * MILLION };

    row = read_option("Choose the row (1-%d): ", BOARD_ROWS) - 1;
    col = read_option("Choose the column (1-%d): ", BOARD_COLS) - 1;

    if (row >= BOARD_ROWS)
        puts("bad value for row");
    else if (col >= BOARD_COLS)
        puts("bad value for column");

    fputs("Text to add: ", stdout);
    fgets(buf, sizeof(buf), stdin);

    sem_t *wrt = &sems[row][col][WRT];

    puts("Trying to get access to the cell");
    if (sem_timedwait(wrt, &spec) == -1) {
        puts("This cell is currently busy! Please try again later.");
    } else {
        board->cell[row][col].type = TEXT;
        strncpy(board->cell[row][col].content.text, buf, 256);

        puts("Cell content updated with success!");
    }
    sem_post(wrt);
}

void
swap_cells(sharedboard *board, sem_t sems[BOARD_ROWS][BOARD_COLS][CELL_SEM_LAST])
{
    size_t row1, col1;
    size_t row2, col2;

    struct timespec spec = { .tv_sec = 0, .tv_nsec = TIMEOUT * MILLION };

    puts("Cell A:");
    row1 = read_option("\tChoose the row (1-%d): ", BOARD_ROWS) - 1;
    col1 = read_option("\tChoose the column (1-%d): ", BOARD_COLS) - 1;
    putchar('\n');

    if (row1 >= BOARD_ROWS)
        puts("bad value for row");
    else if (col1 >= BOARD_COLS)
        puts("bad value for column");

    puts("Cell B:");
    row2 = read_option("\tChoose the row (1-%d): ", BOARD_ROWS) - 1;
    col2 = read_option("\tChoose the column (1-%d): ", BOARD_COLS) - 1;
    putchar('\n');

    if (row2 >= BOARD_ROWS)
        puts("bad value for row");
    else if (col2 >= BOARD_COLS)
        puts("bad value for column");

    sem_t *cell_a, *cell_b;
    cell_a = &sems[row1][col1][WRT];
    cell_b = &sems[row2][col2][WRT];

    if (sem_timedwait(cell_a, &spec) == -1) {
        printf("Cell at position (%zu, %zu) is currently busy!"
               "Please try again later.\n",
               row1,
               col1);
    } else if (sem_timedwait(cell_b, &spec) == -1) {
        printf("Cell at position (%zu, %zu) is currently busy!"
               "Please try again later.\n",
               row2,
               col2);
        sem_post(cell_a);
    } else {
        cell tmp = board->cell[row1][col1];
        board->cell[row1][col1] = board->cell[row2][col2];
        board->cell[row2][col2] = tmp;

        sem_post(cell_b);
        sem_post(cell_a);

        printf("Swapped cells (%zu, %zu) and (%zu, %zu) with success!",
               row1,
               col1,
               row2,
               col2);
    }
}

void *
init_mem(const char *restrict name, size_t size)
{
    int fd;
    void *block;

    fd = shm_open(name, O_RDWR, 0640);
    if (fd == -1) {
        perror("shm_open");
        return NULL;
    }

    block = mmap(NULL, size, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    close(fd);

    if (block == MAP_FAILED) {
        perror("mmap");
        shm_unlink(name);
        return NULL;
    }

    return block;
}

size_t
read_option(const char *restrict fmt, ...)
{
    char buf[BUFSIZ];
    va_list ap;

    fputc('\n', stdout);

    va_start(ap, fmt);
    vfprintf(stdout, fmt, ap);
    va_end(ap);

    fgets(buf, sizeof(buf), stdin);
    assert(buf);

    size_t idx;
    int ret = sscanf(buf, "%zu", &idx);
    assert(ret == 1);

    return idx;
}

char *
query_boards(const boardlist *list, sem_t *board_sem)
{
    size_t i, len;
    char *chosen = NULL;

    /* TODO: change this to reader/writer slide no. 70? */
    puts("Querying all boards... Please be patient :D\n");
    sem_wait(board_sem);
    len = list->len;

    puts("Choose which board you want to access:");
    for (i = 0; i < len; i++)
        printf("%zu. %s\n", i + 1, list->boards[i]);
    puts("\n0. Exit");

    size_t idx = read_option("Your choice: ");

    if (idx > 0 && idx <= len) {
        chosen = strdup(list->boards[idx - 1]);
        assert(chosen);
    } else {
        chosen = NULL;
    }
    sem_post(board_sem);

    return chosen;
}

static void
init_sems(const char *board_name,
          sem_t *sems[BOARD_ROWS][BOARD_COLS][CELL_SEM_LAST],
          const size_t rows,
          const size_t cols)
{
    char buf[PATH_MAX];

    sem_t *sem;
    size_t i, j;

    for (i = 0; i < rows; i++) {
        for (j = 0; j < cols; j++) {
            int ret = snprintf(buf, sizeof(buf), "%s.%zu.%zu.mutex", board_name, i, j);
            assert((unsigned) ret < sizeof(buf));

            sem = sem_open(buf, 0, 0640);
            assert(sem != SEM_FAILED);

            sems[i][j][MUTEX] = sem;

            ret = snprintf(buf, sizeof(buf), "%s.%zu.%zu.wrt", board_name, i, j);
            assert((unsigned) ret < sizeof(buf));

            sem = sem_open(buf, 0, 0640);
            assert(sem != SEM_FAILED);

            sems[i][j][WRT] = sem;
        }
    }
}

int
main(void)
{
    const struct menu_item menu[] = {
        {.name = "Add text to cell",   .func = &add_text  },
        { .name = "Add image to cell", .func = &add_image },
        { .name = "View cell",         .func = &todo      },
        { .name = "Swap two cells",    .func = &swap_cells},
    };

    sem_t *sems[BOARD_ROWS][BOARD_COLS][CELL_SEM_LAST];

    boardlist *list = INIT_DATABASE();
    if (!list) {
        fputs("Server unavailable. Please try again later.\n", stderr);
        return EXIT_FAILURE;
    }

    sem_t *db_sem = sem_open(DATABASE_SEM, 0, 0640);
    assert(db_sem != SEM_FAILED);

    char *boardname = query_boards(list, db_sem);
    assert(boardname);

    sharedboard *board = init_mem(boardname, sizeof(sharedboard));
    assert(board);

    init_sems(boardname, sems, BOARD_ROWS, BOARD_COLS);

    size_t i, opt;
    do {
        for (i = 0; i < LENGTH(menu); i++)
            printf("%zu. %s\n", i + 1, menu[i].name);
        puts("\n0. Exit");

        opt = read_option("Choose an option: ");

        if (opt > LENGTH(menu)) {
            puts("Invalid option\n");
        } else if (opt > 0) {
            printf("Entering menu \"%s\"\n", menu[opt - 1].name);
            menu[opt - 1].func(board, NULL);
        }
    } while (opt != 0);

    puts("Bye, bye!");

    free(boardname);
    return EXIT_SUCCESS;
}
