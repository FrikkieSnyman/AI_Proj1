/*
 * Andre Calitz 13020006
 * Frikkie Snyman 13028741
 */
package ai.cellwars;

import java.util.LinkedList;

/**
 *  Class to resemble cells
 * @author Frikkie and Andre
 */
public class Cell {
    Integer positionX;
    Integer positionY;
    Integer moveCount = 1;
    String color;
    LinkedList<Position> infList = null;
    LinkedList<Cell> neighbours = new LinkedList<>();
    /**
     * Constructor of cell
     * @param color Color of cell in lowercase
     */
    public Cell(String color){
        this.infList = new LinkedList<>();
        this.color = color;
    }
    /**
     * Constructor of cell
     * @param positionX Cell position at X
     * @param positionY Cell position at Y
     * @param color Cell color in lowercase
     */
    public Cell(Integer positionX, Integer positionY, String color){
        this.positionX = positionX;
        this.positionY = positionY;
        this.color = color;
        this.infList = new LinkedList<>();
    }
    /**
     * Set cell's x position
     * @param positionX To this X
     */
    public void setPositionX (Integer positionX){
        this.positionX = positionX;
    }
    /**
     * Set cell's Y position
     * @param positionY To this Y
     */
    public void setPositionY (Integer positionY){
        this.positionY = positionY;
    }
    /**
     * Set cell's color
     * @param color To this color
     */
    public void setColor (String color){
        this.color = color;
    }
    /**
     * Retrieves cell position
     * @return Integer[] with x at 0, y at 1
     */
    public Integer[] getPosition(){
        Integer[] returnThis = {positionX, positionY};
        return returnThis;
    }
    /**
     * Retrieve cell color
     * @return String of cell's color
     */
    public String getColor(){
        return color;
    }
    /**
     * Retrieve the distance that cell can move
     * @return Integer
     */
    public Integer getMoveCount(){
        return moveCount;
    }
    /**
     * Set the distance that cell can move
     * @param moveCount To this move count
     */
    public void setMoveCout(Integer moveCount){
        this.moveCount = moveCount;
    }
}
