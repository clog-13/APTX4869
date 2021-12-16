#include <stdio.h>
void input(int number[], int n) {
    int i = 0;
    for(i = 0; i < n; i++) {
        scanf("%d", &number[i]);
    }
}

void output(int number[], int n) {
    int i=0;
    for(i=0;i<n;i++) {
        printf("%d", number[i]);  // !!!!! &number[i]
    }
}
void max_min_change(int number[],int n) {
    int min_index=0, max_index=0;
    int i=0;
    int temp=0;
    for(i=0;i<n;i++) {
        if (number[i] < number[min_index]) {
            min_index=i;
        }
        if (number[i] > number[max_index]) {
            max_index=i;
        }
    }
    temp = number[0];
    number[0] = number[max_index];
    number[max_index] = temp;
    temp = number[n-1];
    number[n-1] = number[min_index];
    number[min_index] = temp;
}
void main() {
    // int x = 0, z = 2;
    // z = (x=1) ? 13:8;
    // printf("%d\n", z);
    // int nn = '\n';
    // printf("%d", nn);

    // int number[10];
    // input(number, 10);
    // max_min_change(number, 10);
    // output(number, 10);

    int a[3] = {1,2,3};
    int *p, i;
    char *q, ch;
    p = &i;
    ch = 'A';
    q = &ch;
    *p = 40;
    *p = *q;
    printf("%d", p);
}

