/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.cellwars;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
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
    // javax.swing.JTable table = null;
    javax.swing.JPanel gamePanel = null;
    LinkedList<Cell> allCells = new LinkedList<>();
    javax.swing.JButton[][] btns;
    
    public Game(Integer boardSize, Integer cellsPerPlayer, Integer players){
        this.boardSize = boardSize;
        this.cellsPerPlayer = cellsPerPlayer;
        this.players = players;

        btns = new javax.swing.JButton[boardSize][boardSize];
        
        bluePlayer = new Player(cellsPerPlayer, "blue", 0, 0, boardSize/2 -1, boardSize); 
        allCells.addAll(bluePlayer.getCellList());
        redPlayer = new Player(cellsPerPlayer, "red", boardSize/2 +1, 0, boardSize, boardSize);
        allCells.addAll(redPlayer.getCellList());
        board = new Board(boardSize, allCells);

        gamePanel = board.getPanel();
        gamePanel.setLayout(new java.awt.GridLayout(boardSize, boardSize));

        // table = board.getTable();
        // board.setTable(board);
        drawCells();
    }
    
    public void drawCells(){
        // GridBagConstraints con = new GridBagConstraints();
        
         for (int i = 0; i < allCells.size(); ++i){
             Cell tmp = allCells.get(i);
//             table.setValueAt(tmp.getColor().toUpperCase(), tmp.positionY, tmp.positionX);
             if (tmp.getColor().compareTo("blue") == 0){
                 board.setBlockType(BlockType.BLUE_OCCUPIED,tmp.positionX,tmp.positionY);
             } else{
                 board.setBlockType(BlockType.RED_OCCUPIED,tmp.positionX,tmp.positionY);
             }
         }

        // gamePanel.add(new javax.swing.JButton("test"));

        //Create button grid
        for (int x = 0; x < boardSize; ++x) {
            for (int y = 0; y < boardSize; ++y) {

                // con.gridx = x;
                // con.gridy = y;

                btns[y][x] = new javax.swing.JButton(y + "_" + x);

                

                // btns[x][y].setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

                gamePanel.add(btns[y][x]);
            }
        }

        //Set cells to correct color
        for (int i = 0; i < allCells.size(); ++i) {
            Cell tmpCell = allCells.get(i);

            if (tmpCell.getColor().compareTo("blue") == 0) {
                btns[tmpCell.positionX][tmpCell.positionY].setBackground(Color.blue);
            }
            else {
                btns[tmpCell.positionX][tmpCell.positionY].setBackground(Color.red);
            }
        }

        //Set cell influence to correct color
        board.determineInfluenced(BlockType.RED_INFLUENCED, BlockType.RED_OCCUPIED, redPlayer.getCellList());
        board.determineInfluenced(BlockType.BLUE_INFLUENCED, BlockType.BLUE_OCCUPIED, bluePlayer.getCellList());
        
        BlockType[][] bt = board.getBoard();
        
        for (int i = 0; i < boardSize; ++i){
            for (int j = 0; j < boardSize; ++j){
                if (bt[i][j] != BlockType.EMPTY){
                    // System.out.println("here");
                    if(bt[i][j] == BlockType.BLUE_INFLUENCED){
                        System.out.println("here");
                        btns[i][j].setBackground(Color.red);
                    } else if (bt[i][j] == BlockType.RED_INFLUENCED){
                        btns[i][j].setBackground(Color.blue);
                    }
                }
            }
        }

        //Redraw board
        gamePanel.validate();
    }
}
