#include <stdio.h>

int main() {
	printf("hello world.\n");
	printf("%c in here.\n", '%');
	int x = 17, y = 16, N = 4;
	int xn = (x/N)+(x%N == 0 ? 0 : 1);
	int yn = (y/N)+(y%N == 0 ? 0 : 1);
	printf("%d, %d\n", xn, yn);

	return 0;
}
