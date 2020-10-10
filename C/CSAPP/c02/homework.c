#include <stdio.h>

int exer_59(int a, int b) {
    int mask = 0xff;
    int res = (a & mask) | (b & ~mask);
    return res;
}

unsigned replace(unsigned x, int i, char b) {
    if (i < 0) {
        printf("error: i is negetive\n");
        return x;
    }
    if (i > sizeof(unsigned)-1) {
        printf("error: too big i");
        return x;
    }

    unsigned mask = ((unsigned) 0xFF) << (i << 3);
    unsigned pos_byte = ((unsigned) b) << (i << 3);

    return (x & ~mask) | pos_byte;
}