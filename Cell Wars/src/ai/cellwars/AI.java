/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.cellwars;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

/**
 *
 * @author frikkie
 */
public class AI extends Player{
    Game game = null;
    LinkedList<Move> moveList = null;
    
    /**
     * Constructor creates player and generates the player's cells
     * @param cells
     * @param color
     * @param toX
     * @param toY 
     */
    public AI(Integer cells, String color,int fromX, int fromY, int toX, int toY, Game game){
        super(cells, color, fromX, fromY, toX, toY);
        this.game = game;
        moveList = new LinkedList<Move>();
        
        System.out.println("Creating the terminator");
    }
    
    public void run() {
        while (!game.gameOver) {
            if (game.currentPlayer != this) {
                System.out.print("");
            } else {
                makeMove();
//                break;
            }
        }
        
        System.out.println("Game over");
    }
    
    public void makeMove() {
//        System.out.println("I can now make a move");
        
        moveList.clear();
        
        for (int i = 0; i < cellList.size(); ++i) {
            generateMoves(cellList.get(i));
        }
        
//        System.out.println(moveList.size());
        
        Integer moveNum = randomInteger(0, moveList.size());
        
        Move currentMove = moveList.get(moveNum);
        
        game.board.boardUI.moveAICell(currentMove.getStartY(), currentMove.getStartX(), currentMove.getStopY(), currentMove.getStopX());
        
        try {
            Thread.sleep(200);
        } catch (Exception e) {
            
        }
        
        
//        game.swapPlayers();
    }
    
    public void generateMoves(Cell c) {
        Move tempMove = null;
        
        tempMove = new Move(c.positionX, c.positionX + 1, c.positionY, c.positionY);
        
        if (validMove(tempMove.getStartY(), tempMove.getStartX(), tempMove.getStopY(), tempMove.getStopX())) {
            moveList.add(tempMove);
        }
        
        tempMove = new Move(c.positionX, c.positionX - 1, c.positionY, c.positionY);
        
        if (validMove(tempMove.getStartY(), tempMove.getStartX(), tempMove.getStopY(), tempMove.getStopX())) {
            moveList.add(tempMove);
        }
        
        tempMove = new Move(c.positionX, c.positionX, c.positionY, c.positionY + 1);
        
        if (validMove(tempMove.getStartY(), tempMove.getStartX(), tempMove.getStopY(), tempMove.getStopX())) {
            moveList.add(tempMove);
        }
        
        tempMove = new Move(c.positionX, c.positionX, c.positionY, c.positionY - 1);
        
        if (validMove(tempMove.getStartY(), tempMove.getStartX(), tempMove.getStopY(), tempMove.getStopX())) {
            moveList.add(tempMove);
        }
    }
    
    public boolean validMove(Integer startY, Integer startX, Integer stopY, Integer stopX) {
        if (startX == stopX && startY == stopY) {
            return false;
        } else if (startX != stopX && startY != stopY) {
            return false;
        }
        
        if (stopX < 0 || stopX >= game.boardSize) {
            return false;
        } else if (stopY < 0 || stopY >= game.boardSize) {
            return false;
        }
        
//        Integer moveCount = 0;
//        
//        ListIterator<Cell> iterator = game.allCells.listIterator();
//        Cell current = null;
//        
//        while (iterator.hasNext()) {
//            current = iterator.next();
//            
//            if (current.positionX == stopX && current.positionY == stopY) {
//                return false;
//            } else if (current.positionX == startX && current.positionY == startY) {
//                moveCount = current.moveCount;
//            }
//        }
//        
////        System.out.println(moveCount);
//        
//        if (startX == stopX) {
//            if (Math.abs(startY - stopY) > moveCount) {
//                return false;
//            }
//        } else {
//            if (Math.abs(startX - stopX) > moveCount) {
//                return false;
//            }
//        }
        
        return true;
    }
//    /**
//     * Generates a random integer between min and max
//     * @param min
//     * @param max
//     * @return 
//     */
//    private int randomInteger(int min, int max){
//        Random rand = new Random();
//        int returnThis = rand.nextInt((max - min)) +min;
//        return returnThis;
//    }
}
