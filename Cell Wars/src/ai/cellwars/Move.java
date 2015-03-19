/*
 * Andre Calitz 13020006
 * Frikkie Snyman 13028741
 */
package ai.cellwars;

/**
 * Class that simulates moves on the board
 * @author Frikkie and Andre
 */
public class Move {
    Integer startX;
    Integer stopX;
    Integer startY;
    Integer stopY;
    Integer hValue;
    /**
     * Constructor for move
     * @param startX From this X
     * @param stopX To this X
     * @param startY From this Y
     * @param stopY To this Y
     */
    public Move(Integer startX, Integer stopX, Integer startY, Integer stopY) {
        this.startX = startX;
        this.stopX = stopX;
        this.startY = startY;
        this.stopY = stopY;
    }
    /**
     * Function to retrieve the initial x
     * @return Integer initial x value
     */
    public Integer getStartX() {
        return startX;
    }
    /**
     * Function to retrieve new X
     * @return Integer new x
     */
    public Integer getStopX() {
        return stopX;
    }
    /**
     * Function to retrieve initial y value
     * @return Integer initial y
     */
    public Integer getStartY() {
        return startY;
    }
    /**
     * Function get new y
     * @return Integer new y
     */
    public Integer getStopY() {
        return stopY;
    }
    /**
     * Set the heuristic of the move
     * @param h To this value
     */
    public void setHValue(Integer h) {
        hValue = h;
    }
    /**
     * Function to get heuristic value
     * @return Integer heuristic value
     */
    public Integer getHValue() {
        return hValue;
    }
}
