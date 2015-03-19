/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.cellwars;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author frikkie
 */
public class AI extends Player{
    Game game = null;
    BlockType[][] saveState = null;
    LinkedList<Cell> saveRed = null;
    LinkedList<Cell> saveBlue = null;
    LinkedList<Cell> saveGame = null;
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
        
        game.lock.lock();
        
        try {
            moveList.clear();

            for (int i = 0; i < cellList.size(); ++i) {
                generateMoves(cellList.get(i));
            }

    //        System.out.println(moveList.size());

    //        Integer moveNum = randomInteger(0, moveList.size());



            Move currentMove = getBestMove();
    //        Move currentMove = randomMove();

            game.board.boardUI.moveAICell(currentMove.getStartY(), currentMove.getStartX(), currentMove.getStopY(), currentMove.getStopX());

            if (game.players == 0) {
                try {
                    Thread.sleep(150);
                } catch (Exception e) {

                }
            }
        } finally {
            game.lock.unlock();
        }
    }
    
    public void generateMoves(Cell c) {
        Move tempMove = null;
        
        tempMove = new Move(c.positionX, c.positionX + 1, c.positionY, c.positionY);
        
        if (validMove(tempMove.getStartY(), tempMove.getStartX(), tempMove.getStopY(), tempMove.getStopX())) {
            tempMove.setHValue(generateH(tempMove));
            moveList.add(tempMove);
        }
        
        tempMove = new Move(c.positionX, c.positionX - 1, c.positionY, c.positionY);
        
        if (validMove(tempMove.getStartY(), tempMove.getStartX(), tempMove.getStopY(), tempMove.getStopX())) {
            tempMove.setHValue(generateH(tempMove));
            moveList.add(tempMove);
        }
        
        tempMove = new Move(c.positionX, c.positionX, c.positionY, c.positionY + 1);
        
        if (validMove(tempMove.getStartY(), tempMove.getStartX(), tempMove.getStopY(), tempMove.getStopX())) {
            tempMove.setHValue(generateH(tempMove));
            moveList.add(tempMove);
        }
        
        tempMove = new Move(c.positionX, c.positionX, c.positionY, c.positionY - 1);
        
        if (validMove(tempMove.getStartY(), tempMove.getStartX(), tempMove.getStopY(), tempMove.getStopX())) {
            tempMove.setHValue(generateH(tempMove));
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
        
        Integer moveCount = 0;
        
        ListIterator<Cell> iterator = game.allCells.listIterator();
        Cell current = null;
        
        while (iterator.hasNext()) {
            current = iterator.next();
            
            if (current.positionX == stopX && current.positionY == stopY) {
                return false;
            } else if (current.positionX == startX && current.positionY == startY) {
                moveCount = current.moveCount;
            }
        }
        
//        System.out.println(moveCount);
        
        if (startX == stopX) {
            if (Math.abs(startY - stopY) > moveCount) {
                return false;
            }
        } else {
            if (Math.abs(startX - stopX) > moveCount) {
                return false;
            }
        }
        
        return true;
    }
    
    public Integer generateH(Move move) {
        Integer h = 0;
        
        saveState = game.board.cloneBoard();
        saveRed = game.redPlayer.save();
        saveBlue = game.bluePlayer.save();
        saveGame = game.save();
        
        game.board.boardUI.moveAICell(move.getStartY(), move.getStartX(), move.getStopY(), move.getStopX());
        moveCell(move.getStartY(), move.getStartX(), move.getStopY(), move.getStopX());
        
        if (game.currentPlayer == game.redPlayer) {
            h = game.redPlayer.cellList.size() - game.bluePlayer.cellList.size();
        } else {
            h = game.bluePlayer.cellList.size() - game.redPlayer.cellList.size();
        }
        
        game.board.resetBoard(saveState);
        game.redPlayer.load(saveRed);
        game.bluePlayer.load(saveBlue);
        game.load(saveGame);
        
        return h;
//        return 1;
    }
    
    public void moveCell(Integer startY, Integer startX, Integer stopY, Integer stopX) {
        if (game.board.blockType[startX][startY].equals(BlockType.BLUE_OCCUPIED)) {
            game.board.blockType[stopX][stopY] = BlockType.BLUE_OCCUPIED;
            game.board.blockType[startX][startY] = BlockType.EMPTY;

            for (int i = 0; i < game.bluePlayer.cellList.size(); ++i){
                if (game.bluePlayer.cellList.get(i).positionX == startX && game.bluePlayer.cellList.get(i).positionY == startY){
                    game.bluePlayer.cellList.get(i).setPositionX(stopX);
                    game.bluePlayer.cellList.get(i).setPositionY(stopY);
                }
            }
        } else {
            game.board.blockType[stopX][stopY] = BlockType.RED_OCCUPIED;
            game.board.blockType[startX][startY] = BlockType.EMPTY;
            for (int i = 0; i < game.redPlayer.cellList.size(); ++i){
                if (game.redPlayer.cellList.get(i).positionX == startX && game.redPlayer.cellList.get(i).positionY == startY){
                    game.redPlayer.cellList.get(i).setPositionX(stopX);
                    game.redPlayer.cellList.get(i).setPositionY(stopY);
                }
            }
        }

        /*
        * UpdateCellList with new position of selected cell in all celllists
        * Recalculate influenced cells
        */
        for (int i = 0; i < game.allCells.size(); ++i){
            if (Objects.equals(game.allCells.get(i).positionX, startX) && Objects.equals(game.allCells.get(i).positionY, startY)){
                game.allCells.get(i).setPositionX(stopX);
                game.allCells.get(i).setPositionY(stopY);
            }
        }

        game.determineInfluence();
    }
    
    public void moveCellV2(Integer startY, Integer startX, Integer stopY, Integer stopX) {
        if (saveState[startX][startY].equals(BlockType.BLUE_OCCUPIED)) {
            saveState[stopX][stopY] = BlockType.BLUE_OCCUPIED;
            saveState[startX][startY] = BlockType.EMPTY;
            
            for (int i = 0; i < game.bluePlayer.cellList.size(); ++i){
                if (saveBlue.get(i).positionX == startX && saveBlue.get(i).positionY == startY){
                    saveBlue.get(i).setPositionX(stopX);
                    saveBlue.get(i).setPositionY(stopY);
                }
            }
        } else {
            saveState[stopX][stopY] = BlockType.RED_OCCUPIED;
            saveState[startX][startY] = BlockType.EMPTY;
            for (int i = 0; i < game.redPlayer.cellList.size(); ++i){
                if (saveRed.get(i).positionX == startX && saveRed.get(i).positionY == startY){
                    saveRed.get(i).setPositionX(stopX);
                    saveRed.get(i).setPositionY(stopY);
                }
            }
        }
         
        /*
        * UpdateCellList with new position of selected cell in all celllists
        * Recalculate influenced cells
        */
        for (int i = 0; i < saveGame.size(); ++i){
            if (Objects.equals(saveGame.get(i).positionX, startX) && Objects.equals(saveGame.get(i).positionY, startY)){
                saveGame.get(i).setPositionX(stopX);
                saveGame.get(i).setPositionY(stopY);
            }
        }
        
        game.determineInfluence();
    }
    
    public Move getBestMove() {
        boolean changed = true;
        
        ListIterator<Move> iterator = moveList.listIterator();
        Move current = null;
        
        Move best = iterator.next();
        
        while (iterator.hasNext()) {
            current = iterator.next();
            
            if (current.getHValue() != best.getHValue()) {
                changed = false;
            }
            
            if (current.getHValue() > best.getHValue()) {
                best = current;
            }
        }
        
        if (changed) {
            best = randomMove();
        }
        
        return best;
    }
    
    public Move randomMove() {
        return moveList.get(randomInteger(0, moveList.size() - 1));
    }
}
