#include <stdio.h>

struct data
{
    int a;
}d, *p;

void main() {
    char chs[5] = "hello";
    printf("%s", chs);
    // printfs("???");

    p = &d;
    //  = (* data)malloc(sizeof(d));
    (*p).a = 1;
    printf("???%d", p->a);
}