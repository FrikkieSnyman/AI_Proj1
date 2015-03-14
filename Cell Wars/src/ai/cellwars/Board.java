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
public class Board {
    Integer boardSize;
    BoardUI boardUI = null;
    public Board(Integer boardSize){
        this.boardSize = boardSize;
        boardUI = new BoardUI(boardSize);
        boardUI.setVisible(true);
    }
    
    public javax.swing.JTable getTable(){
        return boardUI.getTable();
    }
    
    public void setTable(javax.swing.JTable table){
        boardUI.setTable(table);
    }
}
