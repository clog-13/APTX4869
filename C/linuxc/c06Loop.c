#include <stdio.h>

int main() {
    for (int i = 0; i < 10; i++) {
        printf("%d\n", i);
        if (i == 6)
            goto outer;
    }
    goto ret;
    outer:
        printf("out!\n");
    ret:
    return 0;
}
