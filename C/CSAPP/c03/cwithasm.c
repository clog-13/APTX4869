#include <stdio.h>
//#include <stdlib.h>

int main() {
	int a=1, b=2, c=3;
	__asm (
			
		"mov %eax, a\n\t"
		"add %eax, b\n\t"
		"add %eax, c\n\t"
		"mov a, %eax\n\t"
	
	    );
	
	printf("%d", a);
	//system("pause");
	return 0;
}
