#include <stdio.h>

#define ABS(a) (a > 0 ? a : -a)

int getNegRes() {
    int res = 3;
    return -res;
}

int main() {
    int i = 4;
    if (i) 
        printf("if you big than zero\n");
    else 
        printf("fff\n");

    printf("%d\n", getNegRes());
    printf("%d\n", ABS(-4));
    return 0;
}
