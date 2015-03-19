/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.cellwars;

import java.util.LinkedList;

/**
 *
 * @author frikkie
 */
public class Cell {
    Integer positionX;
    Integer positionY;
    Integer moveCount = 1;
    String color;
    LinkedList<Position> infList = null;
    LinkedList<Cell> neighbours = new LinkedList<>();
    /**
     * 
     */
    public Cell(String color){
        this.infList = new LinkedList<>();
        this.color = color;
    }
    /**
     * 
     * @param positionX
     * @param positionY
     * @param color 
     */
    public Cell(Integer positionX, Integer positionY, String color){
        this.positionX = positionX;
        this.positionY = positionY;
        this.color = color;
        this.infList = new LinkedList<>();
    }
    /**
     * 
     * @param positionX 
     */
    public void setPositionX (Integer positionX){
        this.positionX = positionX;
    }
    /**
     * 
     * @param positionY 
     */
    public void setPositionY (Integer positionY){
        this.positionY = positionY;
    }
    /**
     * 
     * @param color 
     */
    public void setColor (String color){
        this.color = color;
    }
    /**
     * 
     * @return 
     */
    public Integer[] getPosition(){
        Integer[] returnThis = {positionX, positionY};
        return returnThis;
    }
    /**
     * 
     * @return 
     */
    public String getColor(){
        return color;
    }
    /**
     * 
     * @return 
     */
    public Integer getMoveCount(){
        return moveCount;
    }
    /**
     * 
     * @param moveCount 
     */
    public void setMoveCout(Integer moveCount){
        this.moveCount = moveCount;
    }
}
