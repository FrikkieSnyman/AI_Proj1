# Created by
# 	FRIKKIE SNYMAN 	-	13028741
# 	ANDRE CALITZ 	-	13020006

main: board.o main.o
	g++ board.o main.o -o main

main.o: main.cpp
	g++ -c main.cpp

board.o: headers/board.h source/board.cpp
	g++ -c source/board.cpp