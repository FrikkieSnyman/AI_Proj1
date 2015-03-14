/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.cellwars;

/**
 *
 * @author frikkie
 */
public class Game {
    
    Integer boardSize;
    Integer cellsPerPlayer;
    Integer players; //2 = PvP; 1 = PvAI; 0 = AIvAI
    Player redPlayer = null;
    Player bluePlayer = null;
    Board board = null;
    
    public Game(Integer boardSize, Integer cellsPerPlayer, Integer players){
        this.boardSize = boardSize;
        this.cellsPerPlayer = cellsPerPlayer;
        this.players = players;
        board = new Board(boardSize);
    }
}
