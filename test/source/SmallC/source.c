#include <stdio.h>

int count = 0;

/* a^b */
int myPow(int a, int b) {
	int x;
	int result = 1;
	
	if (b <= 0) {
		return 1;
	}
	
	for (x = 1; x <= b; x = x + 1) {
		result = result * a;
	}
	
	return result;
}

/* a^2 */
int mySquare(int a) {
	return a * a;
}

/* square root of a. */
int mySqrt(int a) {
	int i = 1;
	while (mySquare(i) <= a) {
		i = i + 1;
	}
	
	return i - 1;
}

/* b-th root of a. */
int myRoot(int a, int b) {
	int i = 1;
	while (myPow(i, b) <= a) {
		i = i + 1;
	}
	
	return i - 1;
}

/* log_b(a) */
int myLog(int a, int b) {
	int i = 1;
	while (myPow(b, i) <= a) {
		i = i + 1;
	}
	
	return i - 1;
}

void main () {
	int a = 2;
	int b = 10;
	
	int x = myPow(a, b);
	count = count + 1;
	x = mySquare(a);
	count = count + 1;
	x = mySqrt(a);
	count = count + 1;
	x = mySqrt(25);
	count = count + 1;
	x = myRoot(1024, 2);
	count = count + 1;
	x = myLog(1024, 2);
	count = count + 1;
	x = mySquare(count);
}
