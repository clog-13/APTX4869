#include <stdio.h>

int main() {
	printf("hello world.\n");
	printf("%c in here.\n", '%');
	int x = 17, y = 16, maxN = 4;
	int xn = (x/maxN)+(x%maxN == 0 ? 0 : 1);
	int yn = (y/maxN)+(y%maxN == 0 ? 0 : 1);
	printf("%d, %d\n", xn, yn);

	return 0;
}
