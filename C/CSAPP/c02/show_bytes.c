#include <stdio.h>

typedef unsigned char *byte_pointer;

void show_bytes(byte_pointer start, size_t len) {
    size_t i;
    for (i = 0; i < len; i++) 
        printf("%.2x", start[i]);
    printf("\n");  
}

void show_short(short x) {
    show_bytes((byte_pointer) &x, sizeof(short));
}

void show_int(int n) {
    show_bytes((byte_pointer) &n, sizeof(int));
}

void show_long(long x) {
    show_bytes((byte_pointer) &x, sizeof(long));
}

void show_float(float n) {
    show_bytes((byte_pointer) &n, sizeof(float));
}

void show_double(double x) {
    show_bytes((byte_pointer) &x, sizeof(double));
}

void show_pointer(void *n) {
    show_bytes((byte_pointer) &n, sizeof(void *));
}

void test_show_bytes(int val) {
    float fval = (float) val;
    int* pval = &val;

    show_int(val);
    show_float(fval);
    show_pointer(pval);
}

// int main () {
//     int test_num = 12345;
//     test_show_bytes(test_num);
// }