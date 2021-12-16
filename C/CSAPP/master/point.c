#include <stdio.h>
int main() {
    int *p, *q;
    int n = 3, m = 4;
    p = &n; q = &m;
    printf("%d,%d,%d\n", *p, p, &p);
    p = q;  
    printf("%d,%d,%d\n", *p, p, &p);
    printf("%d,%d\n", n, &n);
}