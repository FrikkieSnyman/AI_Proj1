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
    Boolean boardCreated = false;
    Integer boardSize;
    Integer cellsPerPlayer;
    Integer players; //2 = PvP; 1 = PvAI; 0 = AIvAI
    Player redPlayer = null;
    Player bluePlayer = null;
    Player currentPlayer = null;
    Board board = null;
    // javax.swing.JTable table = null;
    javax.swing.JPanel gamePanel = null;
    LinkedList<Cell> allCells = new LinkedList<>();
    javax.swing.JButton[][] btns;
    
    Boolean gameOver = false;
    
    /**
     * 
     * @param boardSize
     * @param cellsPerPlayer
     * @param players 
     */
    public Game(Integer boardSize, Integer cellsPerPlayer, Integer players){
        this.boardSize = boardSize;
        this.cellsPerPlayer = cellsPerPlayer;
        this.players = players;

//        btns = new javax.swing.JButton[boardSize][boardSize];
        
        switch (players) {
            case 0:
                bluePlayer = new AI(cellsPerPlayer, "blue", 0, 0, boardSize/2 -1, boardSize, this); 
                allCells.addAll(bluePlayer.getCellList());
                redPlayer = new AI(cellsPerPlayer, "red", boardSize/2 +1, 0, boardSize, boardSize, this);
                allCells.addAll(redPlayer.getCellList());
                board = new Board(boardSize, allCells, this);
                break;
            case 1:
                bluePlayer = new Player(cellsPerPlayer, "blue", 0, 0, boardSize/2 -1, boardSize); 
                allCells.addAll(bluePlayer.getCellList());
                redPlayer = new AI(cellsPerPlayer, "red", boardSize/2 +1, 0, boardSize, boardSize, this);
                allCells.addAll(redPlayer.getCellList());
                board = new Board(boardSize, allCells, this);
                break;
            case 2:
                bluePlayer = new Player(cellsPerPlayer, "blue", 0, 0, boardSize/2 -1, boardSize); 
                allCells.addAll(bluePlayer.getCellList());
                redPlayer = new Player(cellsPerPlayer, "red", boardSize/2 +1, 0, boardSize, boardSize);
                allCells.addAll(redPlayer.getCellList());
                board = new Board(boardSize, allCells, this);
                
                break;
        }
        
        //Start current player as blue
        currentPlayer = bluePlayer;

        gamePanel = board.getPanel();
        gamePanel.setLayout(new java.awt.GridLayout(boardSize, boardSize));

        // table = board.getTable();
        // board.setTable(board);
//        drawCells();
        
        initGUI();
        drawCellsV2();
        
        bluePlayer.start();
        redPlayer.start();
    }
    
    public LinkedList<Cell> save() {
        return (LinkedList<Cell>) allCells.clone();
    }
    
    public void load(LinkedList<Cell> state) {
        allCells = state;
    }
    
    /**
     * 
     */
    public void drawCells(){
        //Create button grid
//        for (int x = 0; x < boardSize; ++x) {
//            for (int y = 0; y < boardSize; ++y) {
//                btns[y][x] = new javax.swing.JButton(y + "_" + x);
//                btns[y][x].setText("");
//                btns[y][x].setFocusable(false);
//
//                gamePanel.add(btns[y][x]);
//            }
//        }
//        board = new Board(boardSize, allCells, this);
//
//        gamePanel = board.getPanel();
//        gamePanel.setLayout(new java.awt.GridLayout(boardSize, boardSize));
        if (!boardCreated){
            btns = board.boardUI.createBTNS(boardSize);
        }

        //Set cells to correct color
        for (int i = 0; i < allCells.size(); ++i) {
            Cell tmpCell = allCells.get(i);
//            System.out.println(tmpCell.positionX + " " + tmpCell.positionY + " " + tmpCell.color);

            if (tmpCell.getColor().compareTo("blue") == 0) {
                board.setBlockType(BlockType.BLUE_OCCUPIED,tmpCell.positionX,tmpCell.positionY);
                btns[tmpCell.positionX][tmpCell.positionY].setBackground(Color.blue);
//                btns[tmpCell.positionX][tmpCell.positionY].setFocusable(true);
            }
            else {
                board.setBlockType(BlockType.RED_OCCUPIED,tmpCell.positionX,tmpCell.positionY);
                btns[tmpCell.positionX][tmpCell.positionY].setBackground(Color.red);
//                btns[tmpCell.positionX][tmpCell.positionY].setFocusable(true);
            }
        }


        //Set cell influence to correct color
        //if currentplayer = red
//            board.determineInfluenced(BlockType.RED_INFLUENCED, BlockType.RED_OCCUPIED, redPlayer.getCellList());
//        // else
//            board.determineInfluenced(BlockType.BLUE_INFLUENCED, BlockType.BLUE_OCCUPIED, bluePlayer.getCellList());
        

        //Set cell influence to correct color depending on current player
        if (!boardCreated) {
            board.determineInfluenced(BlockType.RED_INFLUENCED, BlockType.RED_OCCUPIED, redPlayer.getCellList());
            board.determineInfluenced(BlockType.BLUE_INFLUENCED, BlockType.BLUE_OCCUPIED, bluePlayer.getCellList());
            boardCreated = true;
        } else {
//            System.out.println("Here here here");
            if (currentPlayer == redPlayer) {
                board.determineInfluenced(BlockType.RED_INFLUENCED, BlockType.RED_OCCUPIED, redPlayer.getCellList());
            } else {
                board.determineInfluenced(BlockType.BLUE_INFLUENCED, BlockType.BLUE_OCCUPIED, bluePlayer.getCellList());
            }
        }

        
        BlockType[][] bt = board.getBoard();
        
        for (int i = 0; i < boardSize; ++i){
            for (int j = 0; j < boardSize; ++j){
                if (bt[i][j] != BlockType.EMPTY){
                    if(bt[i][j] == BlockType.RED_INFLUENCED){
                        btns[i][j].setBackground(new Color(232, 169, 169));
                    } else if (bt[i][j] == BlockType.BLUE_INFLUENCED){
                        btns[i][j].setBackground(new Color(169, 181, 232));
                    }
                } else {
                    btns[i][j].setBackground(new Color(0,0,0));
                }
            }
        }

        //Redraw board
        gamePanel.validate();
    }
    
    public void initGUI() {
        //Create buttons for board
        btns = board.boardUI.createBTNS(boardSize);
        
        //Place cells on the board
        placeCells();
        
        //Determine cell influence for both players
        board.determineInfluenced(BlockType.RED_INFLUENCED, BlockType.RED_OCCUPIED, redPlayer.getCellList());
        board.determineInfluenced(BlockType.BLUE_INFLUENCED, BlockType.BLUE_OCCUPIED, bluePlayer.getCellList());
//        board.determineFullInfluenceV2(BlockType.RED_INFLUENCED, BlockType.RED_OCCUPIED, redPlayer.getCellList());
//        board.determineFullInfluenceV2(BlockType.BLUE_INFLUENCED, BlockType.BLUE_OCCUPIED, bluePlayer.getCellList());
        //Draw cells
        drawCellsV2();
        
        //Show GUI
        board.boardUI.setVisible(true);
    }
    
    public void placeCells() {
        //Set cells to correct color
        for (int i = 0; i < allCells.size(); ++i) {
            Cell tmpCell = allCells.get(i);

            if (tmpCell.getColor().compareTo("blue") == 0) {
                board.setBlockType(BlockType.BLUE_OCCUPIED,tmpCell.positionX,tmpCell.positionY);
            }
            else {
                board.setBlockType(BlockType.RED_OCCUPIED,tmpCell.positionX,tmpCell.positionY);
            }
        }
    }
    
    public void determineInfluence() {
        //Determine cell influence based on the last move
        if (currentPlayer == redPlayer) {
            board.determineInfluenced(BlockType.RED_INFLUENCED, BlockType.RED_OCCUPIED, redPlayer.getCellList());
//            board.determineFullInfluenceV2(BlockType.RED_INFLUENCED, BlockType.RED_OCCUPIED, redPlayer.getCellList());
        } else {
            board.determineInfluenced(BlockType.BLUE_INFLUENCED, BlockType.BLUE_OCCUPIED, bluePlayer.getCellList());
//            board.determineFullInfluenceV2(BlockType.BLUE_INFLUENCED, BlockType.BLUE_OCCUPIED, bluePlayer.getCellList());
            
        }
    }
    
    public void drawCellsV2() {
        for (int x = 0; x < boardSize; ++x) {
            for (int y = 0; y < boardSize; ++y) {
                switch (board.getBlockType(x, y)) {
                    case BLUE_OCCUPIED:
                        btns[x][y].setBackground(Color.blue);
                        break;
                    case BLUE_INFLUENCED:
                        btns[x][y].setBackground(new Color(169, 181, 232));
                        break;
                    case RED_OCCUPIED:
                        btns[x][y].setBackground(Color.red);
                        break;
                    case RED_INFLUENCED:
                        btns[x][y].setBackground(new Color(232, 169, 169));
                        break;
                    case EMPTY:
                        btns[x][y].setBackground(Color.black);
                        break;
                    default:
                        break;
                }
            }
        }
        
        //Redraw board
        gamePanel.validate();
    }
    
    public void drawBoard() {
//        placeCells();
        
        determineInfluence();
        
        drawCellsV2();
    }
    
    public void redrawBoard() {
        /**
         * 
         */
//        drawCells();
//        drawCells();
        
        drawBoard();
        drawBoard();
        drawBoard();
    }
    
    public void swapPlayers() {
        if (currentPlayer == redPlayer) {
            currentPlayer = bluePlayer;
        } else {
            currentPlayer = redPlayer;
        }
    }
}
