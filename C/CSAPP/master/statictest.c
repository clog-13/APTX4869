#include <stdio.h>
#define N 10
char *pointer;
static char *attach[N];
int main()
{
     static int number[N];
     char *localptr[N];
     static int i;
     static char ch;
     static int *ip;
     static char *cp;
     printf("%d\n", i);
     printf("%d\n", ch);  // %d
     printf("%d\n", ' ');
     printf("->>%c<<-\n", ch);  // %c
     printf("%p\n", ip);
     printf("%p\n", cp);
    //  for( ; i < N;i++) {
    //     printf("default global attach = %p\n",attach[i]); 
    //     printf("default static number = %d\n",number[i]);
    //     printf("default localptr =%p\n",localptr[i]);
    //  }
}