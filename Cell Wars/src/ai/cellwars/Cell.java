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
    
    public Cell(){
        
    }
    
    public Cell(Integer positionX, Integer positionY, String color){
        this.positionX = positionX;
        this.positionY = positionY;
        this.color = color;
        this.infList = new LinkedList<>();
    }
    
    public void setPositionX (Integer positionX){
        this.positionX = positionX;
    }
    
    public void setPositionY (Integer positionY){
        this.positionY = positionY;
    }
    
    public void setColor (String color){
        this.color = color;
    }
    
    public Integer[] getPosition(){
        Integer[] returnThis = {positionX, positionY};
        return returnThis;
    }
    
    public String getColor(){
        return color;
    }
    
    public Integer getMoveCount(){
        return moveCount;
    }
    
    public void setMoveCout(Integer moveCount){
        this.moveCount = moveCount;
    }
}
