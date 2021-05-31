#include <stdio.h>
#include "calculate.h"
JNIEXPORT int JNICALL Java_calculate_multiply(JNIEnv *env, jobject javaobj, jint num1, jint num2) 
{
	return num1*num2;
}
