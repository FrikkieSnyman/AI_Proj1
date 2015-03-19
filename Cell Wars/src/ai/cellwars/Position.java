/*
 * Andre Calitz 13020006
 * Frikkie Snyman 13028741
 */
package ai.cellwars;

/**
 * Class to represent a position on the board
 * @author Frikkie and Andre
 */
public class Position {
        Integer x,y;
        Cell ownedByCell;
        /**
         * Constructor of position
         * @param x At this x
         * @param y At this y
         * @param cell For this cell
         */
        public Position(Integer x, Integer y, Cell cell){
            this.x = x;
            this.y = y;
            this.ownedByCell = cell;
        }
        /**
         * Retrieve y position
         * @return Integer current y
         */
        public Integer getY(){
            return y;
        }
        /**
         * Retrieve x position
         * @return Integer current x
         */
        public Integer getX(){
            return x;
        }
        /**
         * Set y position
         * @param y To this y
         */
        public void setY(Integer y){
            this.y = y;
        }
        /**
         * Set x position
         * @param x To this x
         */
        public void setX(Integer x){
            this.x = x;
        }
        /**
         * Retrieve owner of position
         * @return Cell owner
         */
        public Cell getOwner(){
            return ownedByCell;
        }
        /**
         * Set owner of position
         * @param cell To this cell
         */
        public void setOwner(Cell cell){
            ownedByCell = cell;
        }
        /**
         * Check if this is the position
         * @param x This x
         * @param y This y
         * @return boolean true if match, else false
         */
        public Boolean isCoord(int x, int y){
            if (this.x == x && this.y == y){
                return true;
            }
            
            return false;
        }
}
