#include <stdio.h>

int main() {
	printf("hello world.\n");
	printf("%c in here.\n", '%');
	int x = 17, y = 16, n = 4;
	int xn = (x/n)+(x%n == 0 ? 0 : 1);
	int yn = (y/n)+(y%n == 0 ? 0 : 1);
	printf("%d, %d\n", xn, yn);

	return 0;
}
