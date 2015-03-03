/*
Created by
	FRIKKIE SNYMAN 	-	13028741
	ANDRE CALITZ 	-	13020006
*/

#include "../headers/board.h"

	Board::Board(){

	}

	Board::Board(int n){
		boardMatrix = new vector< vector<bool> >(n, (vector<bool>(n)));
	}

	Board::~Board(){
		delete boardMatrix;
	}