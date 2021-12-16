#include <stdio.h>
void main() {
    char c[] = "\\456\123abc\t";
    printf("%d\n", strlen(c));
    for (int i = 0; i < 9; i++) {
        printf("%c,", c[i]);
    }
    int n = 1, *p = &n;
    *p++;
    printf("\n%d\n", n);
}