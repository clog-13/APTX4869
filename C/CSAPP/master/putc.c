#include <stdio.h>
void main() {
    char c;
    while((c=getchar())!='!') {
        putchar(++c);
    }
}