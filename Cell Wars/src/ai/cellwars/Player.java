/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.cellwars;

import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author frikkie
 */

// TODO implement strategy to determine which algorithm to use for movement
public class Player {
    Integer cells;
    String color;
    LinkedList<Cell> cellList = new LinkedList<>();
    
    /**
     * Constructor creates player and generates the player's cells
     * @param cells
     * @param color
     * @param toX
     * @param toY 
     */
    public Player(Integer cells, String color,int fromX, int fromY, int toX, int toY){
        this.cells = cells;
        this.color = color;
        generateCells(fromX, fromY, toX, toY);
    }
    
    /**
     * Get the cellList of the player
     * @return 
     */
    public LinkedList<Cell> getCellList(){
        return this.cellList;
    }
    
    public void setCellList(LinkedList<Cell> cellList){
        this.cellList = cellList;
    }
    
    /**
     * This function generates cells and places them in the cellList at random within range
     * @param toX
     * @param toY 
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
     * @param min
     * @param max
     * @return 
     */
    private int randomInteger(int min, int max){
        Random rand = new Random();
        int returnThis = rand.nextInt((max - min)) +min;
        return returnThis;
    }
}
