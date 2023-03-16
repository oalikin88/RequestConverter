#include <stdio.h>
#include <process.h>

int main(int argc, char *argv[], char **penv) {
	_execl("bin\\javaw.exe", "bin\\javaw.exe",
	"-jar", ".\\RequestConverter-1.2.jar", NULL);
	return 0;
}