/*
 * Andre Calitz 13020006
 * Frikkie Snyman 13028741
 */
package ai.cellwars;

import java.util.LinkedList;
import java.util.Random;

/**
 * Class to represent each participant
 * @author Frikkie and Andre
 */
public class Player extends Thread{
    Integer cells;
    String color;
    LinkedList<Cell> cellList = new LinkedList<>();
    
    /**
     * Constructor creates player and generates the player's cells
     * @param cells How many cells
     * @param color Cell colors 
     * @param fromX Generate cells from this X
     * @param fromY Generate cells from this y
     * @param toX Generate cells to this X
     * @param toY Generate cells to this y
     */
    public Player(Integer cells, String color,int fromX, int fromY, int toX, int toY){
        this.cells = cells;
        this.color = color;
        generateCells(fromX, fromY, toX, toY);
    }
    /**
     * Clone the cells
     * @return LinkedList of old cells
     */
    public LinkedList<Cell> save() {
        return (LinkedList<Cell>) cellList.clone();
    }
    /**
     * Set cells to state
     * @param state Set cells to this list
     */
    public void load(LinkedList<Cell> state) {
        cellList = state;
    }
    
    /**
     * Get the cellList of the player
     * @return LinkedList of cells
     */
    public LinkedList<Cell> getCellList(){
        return this.cellList;
    }
    /**
     * Set cell list of player to this
     * @param cellList Set to this
     */
    public void setCellList(LinkedList<Cell> cellList){
        this.cellList = cellList;
    }
    
    /**
     * This function generates cells and places them in the cellList at random within range
     * @param fromX From this X
     * @param fromY From this y
     * @param toX To this x
     * @param toY To this y
     */
    private void generateCells(int fromX, int fromY, int toX, int toY){
        Integer randX, randY;
        for (int i = 0; i < cells; ++i){
            randX = randomInteger(fromX, toX);
            randY = randomInteger(fromY, toY);
            Cell cell = new Cell(randX, randY, color);
            Boolean found = false;
            for (int j = 0; j < cellList.size(); ++j){
                if (cellList.get(j).positionX == randX && cellList.get(j).positionY == randY){
                    found = true;
                }
            }
            if (!found){
                cellList.add(cell);
            } else{
                --i;
            }
        }
    }
    /**
     * Generates a random integer between min and max
     * @param min Min inclusive
     * @param max Max exclusive
     * @return int random generated integer
     */
    protected int randomInteger(int min, int max){
        Random rand = new Random();
        int returnThis = rand.nextInt((max - min)) +min;
        return returnThis;
    }
}
