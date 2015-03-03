/*
Created by
	FRIKKIE SNYMAN 	-	13028741
	ANDRE CALITZ 	-	13020006
*/
#include <vector>

using namespace std;

#ifndef _GAME_BOARD
#define _GAME_BOARD

class Board
{
public:
	Board();
	Board(int);
	~Board();

private:
	vector< vector<bool> >* boardMatrix;
};

#endif