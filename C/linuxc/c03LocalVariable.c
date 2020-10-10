#include <stdio.h>

void f(void) {
	int i;
	printf("%d\n", i);
	i = 777;
}

int main(void) {
	f();
    printf("hello\n");    // 没有这句， 下面 i 会输出 777， 有则会显示其他
	f();
	return 0;
}
