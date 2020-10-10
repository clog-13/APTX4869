#include <stdio.h>

int main() {
    int i1 = 0xdeadbeef;
    int i2 = 0xdeed0eef;
    int i3 = 0xdead0e0f;
    int i = i1;
    short s = i;
    char c = i;
    printf("%x\n", i);
    printf("%x\n", s);
    printf("%x\n", c);

    int lval = 0xfedcba98 >> 32;
    int rval = 0xfedcba98 >> 36;
    unsigned uval = 0xfedcba98 >> 40;
    printf("%x\n", lval);
    printf("%x\n", rval);
    printf("%x\n", uval);

    return 0;
}