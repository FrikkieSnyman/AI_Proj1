main: test.o main.o
	g++ test.o main.o -o main

main.o: main.cpp
	g++ -c main.cpp

test.o: headers/test.h source/test.cpp
		g++ -c headers/test.h source/test.cpp