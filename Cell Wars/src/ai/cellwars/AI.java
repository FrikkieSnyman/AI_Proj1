/*
 * Andre Calitz 13020006
 * Frikkie Snyman 13028741
 */
package ai.cellwars;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Random;

/**
 * AI class that extends Player class which is used when an
 * AI is selected as a player
* @author Frikkie and Andre
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
     * @param cells Number of cells of player
     * @param color String in lowercase of player color. Either "blue" or "red"
     * @param fromX Used to generate cells in random from this X
     * @param fromY Used to generate cells in random from this Y
     * @param toX Used to generate cells in random to this X
     * @param toY Used to generate cells in random to this Y
     * @param game Reference to the game being played
     */
    public AI(Integer cells, String color,int fromX, int fromY, int toX, int toY, Game game){
        super(cells, color, fromX, fromY, toX, toY);
        this.game = game;
        moveList = new LinkedList<Move>();
        
        System.out.println("Creating the terminator");
    }
    /**
     * Function to run thread
     */
    @Override
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
    /**
     * Generates the move the AI will make
     */
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
    /**
     * Helper function to generate moves
     * @param c Reference to cell to determine valid moves
     */
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
    /**
     * Determines whether suggested move is valid
     * @param startY Move from this Y
     * @param startX Move from this X
     * @param stopY Move to this Y
     * @param stopX Move to this X
     * @return Returns true if move is valid, false otherwise
     */
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
    /**
     * Generates a heuristic for the suggested move
     * @param move Move that is suggested
     * @return Returns an Integer with the value of the heuristic
     */
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
    /**
     * Function to move the cell
     * @param startY Move from this Y
     * @param startX Move from this X
     * @param stopY Move to this Y
     * @param stopX Move to this X
     */
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
    /**
     * Function to retrieve the best move based on heuristics
     * @return Returns a Move object
     */
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
    /**
     * Function to select random move if all heuristics are the same to avoid stagnation
     * @return Move object
     */
    public Move randomMove() {
        return moveList.get(randomInteger(0, moveList.size() - 1));
    }
}
