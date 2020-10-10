#include <stdio.h>

double getThree();

int main(int argc, char *argv[]) {
	double tmp = getThree();
	printf("%f\n", tmp);
	int temp = getTwo();
	printf("%d\n", temp);
	return 13;
}

double getThree() {
	return 3.0;
}

int getTwo() {
	return 2;
}
