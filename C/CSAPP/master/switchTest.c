#include <stdio.h>
#define PI 3.14
#define S(r) PI*r*r

typedef struct {
    char ch[9];  // 8 + 8(1)
    double d;  // 8
}TY;
struct student {
    char name[20];
    int age;
}stud[5], *p;

// #define PR(ar) printf("%d\n", ar)



int main() {



    scanf("%d", &(p->age));

    int a[2][2] = {{11,22},{33,44}};
    printf("%d\n", (a+1)[1]);
    // int *p = a+1;
    // printf("%d\n", *(p++));
    // printf("%d\n", *(p++));


    // int a[] = {1,2,3,4}, *p[] = {a,a+1,a+2,a+3}, **pp = p+1;
    // printf("%d", *(*pp+1));



    // printf("%d\n", sizeof(TY));


    // char ccc;
    // ccc = 'A' + '6' - '4';
    // printf("%c\n", ccc);


    // union data
    // {
    //     int i;
    //     char ch[4];
    //     float f;
    // }data;
    // data.i = 0xbadcfe;  // 7: 0111 8:1000
    // printf("%d\n", data.i);
    // printf("%x\n", data.ch[0]);
    // printf("%x\n", data.ch[1]);
    // printf("%x\n", data.ch[2]);
    // printf("%x\n", data.ch[3]);
    // printf("%f\n", data.f);

    // double dd = 1;
    // printf("%f\n", S(dd+1));

    // int a[] = {1,2,3,4,5};
    // int *p = &a[1];
    // int **pp = &p;
    // printf("%d\n", *(++(*pp)));
    // printf("%d", *(++(*pp)));


    // char c[10] = "abcd", *t;
    // t = c+2;
    // printf("%s\n", t);


    // int a[2][2] = {{1,2},{3,4}};
    // printf("%d\n", **a);  // 1
    // printf("%d\n", **a + 5);  // 6

    // printf("%d\n", *(*a + 1));  // 2
    // printf("%d\n", *(*a + 2));  // 3
    // printf("%d\n", *(*a + 3));  // 4
    // printf("%d\n", *(*a + 4));  // other
    
    // printf("%d\n", *(*(a+1)));  // 3
    // printf("%d\n", *(*(a+1)+1)); // 4
    // printf("%d\n", *(*(a+1)+2)); // other
}


