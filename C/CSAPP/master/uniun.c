#include <stdio.h>
void main() {
    union {
        char c;
        int i;
        
    }u;
    u.c = 'A';
    u.i = 0x1210;
    printf("%d,%d\n", u.c, u.i);
}