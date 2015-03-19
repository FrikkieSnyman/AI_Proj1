/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.cellwars;

/**
 *
 * @author rez
 */
public class Move {
    Integer startX;
    Integer stopX;
    Integer startY;
    Integer stopY;
    Integer hValue;
    
    public Move(Integer startX, Integer stopX, Integer startY, Integer stopY) {
        this.startX = startX;
        this.stopX = stopX;
        this.startY = startY;
        this.stopY = stopY;
    }
    
    public Integer getStartX() {
        return startX;
    }
    
    public Integer getStopX() {
        return stopX;
    }
    
    public Integer getStartY() {
        return startY;
    }
    
    public Integer getStopY() {
        return stopY;
    }
    
    public void setHValue(Integer h) {
        hValue = h;
    }
    
    public Integer getHValue() {
        return hValue;
    }
}
