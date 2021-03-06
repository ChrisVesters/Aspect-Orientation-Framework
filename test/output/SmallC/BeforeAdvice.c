#include <stdio.h>

int count = 0;
int myPow(int a, int b) {
	int x = 0;
	int result = 1;
	if (b <= 0) {
		return 1;
	}
	
	for (x = 1; x <= b; x = x + 1) {
		result = result * a;
	}
	
	return result;
}

int mySquare(int a) {
	return a * a;
}

int mySqrt(int a) {
	int i = 1;
	while(mySquare_call_(i) <= a) {
		i = i + 1;
	}
	
	return i - 1;
}

int myRoot(int a, int b) {
	int i = 1;
	while(myPow_call_(i, b) <= a) {
		i = i + 1;
	}
	
	return i - 1;
}

int myLog(int a, int b) {
	int i = 1;
	while(myPow_call_(b, i) <= a) {
		i = i + 1;
	}
	
	return i - 1;
}

void main() {
	int a = 2;
	int b = 10;
	int x = myPow_call_(a, b);
	count_set_(count_get_() + 1);
	x = mySquare_call_(a);
	count_set_(count_get_() + 1);
	x = mySqrt_call_(a);
	count_set_(count_get_() + 1);
	x = mySqrt_call_(25);
	count_set_(count_get_() + 1);
	x = myRoot_call_(1024, 2);
	count_set_(count_get_() + 1);
	x = myLog_call_(1024, 2);
	count_set_(count_get_() + 1);
	x = mySquare_call_(count_get_());
}

void _a() {
	printf("Advice A\n");
}

int myLog_exec_(int a, int b) {
	int _result_;
	_result_ = myLog(a, b);
	return _result_;
}

int mySqrt_exec_(int a) {
	int _result_;
	_result_ = mySqrt(a);
	return _result_;
}

int mySquare_exec_(int a) {
	int _result_;
	_a();
	_result_ = mySquare(a);
	return _result_;
}

int myPow_exec_(int a, int b) {
	int _result_;
	_result_ = myPow(a, b);
	return _result_;
}

void main_exec_() {
	main();
}

int myRoot_exec_(int a, int b) {
	int _result_;
	_result_ = myRoot(a, b);
	return _result_;
}

int myPow_call_(int a, int b) {
	int _result_;
	_result_ = myPow_exec_(a, b);
	return _result_;
}

void main_call_() {
	main_exec_();
}

int myLog_call_(int a, int b) {
	int _result_;
	_result_ = myLog_exec_(a, b);
	return _result_;
}

int mySqrt_call_(int a) {
	int _result_;
	_result_ = mySqrt_exec_(a);
	return _result_;
}

int mySquare_call_(int a) {
	int _result_;
	_result_ = mySquare_exec_(a);
	return _result_;
}

int myRoot_call_(int a, int b) {
	int _result_;
	_result_ = myRoot_exec_(a, b);
	return _result_;
}

int count_get_() {
	int _result_;
	_result_ = count;
	return _result_;
}

void count_set_(int _new_) {
	count = _new_;
}
