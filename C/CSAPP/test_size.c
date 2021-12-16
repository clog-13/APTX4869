#include <stdio.h>

int main() {
    int a = 12;
    char c = 'a';
    size_t st = sizeof(a);
    size_t cs = sizeof(c);
    printf("this is %zd", cs);
    return 0;
}