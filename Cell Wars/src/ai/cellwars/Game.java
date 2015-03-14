/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.cellwars;

import java.awt.Component;
import java.util.LinkedList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

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
    javax.swing.JTable table = null;
    LinkedList<Cell> allCells = new LinkedList<>();
    
    public Game(Integer boardSize, Integer cellsPerPlayer, Integer players){
        this.boardSize = boardSize;
        this.cellsPerPlayer = cellsPerPlayer;
        this.players = players;
        board = new Board(boardSize);
        table = board.getTable();
        bluePlayer = new Player(cellsPerPlayer, "blue", 0, 0, boardSize/2 -1, boardSize); 
        allCells.addAll(bluePlayer.getCellList());
        redPlayer = new Player(cellsPerPlayer, "red", boardSize/2 +1, 0, boardSize, boardSize);
        allCells.addAll(redPlayer.getCellList());
        drawCells();
    }
    
    public void drawCells(){
 
    }
    

}
