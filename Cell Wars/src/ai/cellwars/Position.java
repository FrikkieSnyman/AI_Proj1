/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.cellwars;

/**
 *
 * @author frikkie
 */
public class Position {
        Integer x,y;
        Cell ownedByCell;
        /**
         * 
         * @param x
         * @param y
         * @param cell 
         */
        public Position(Integer x, Integer y, Cell cell){
            this.x = x;
            this.y = y;
            this.ownedByCell = cell;
        }
        /**
         * 
         * @return 
         */
        public Integer getY(){
            return y;
        }
        /**
         * 
         * @return 
         */
        public Integer getX(){
            return x;
        }
        /**
         * 
         * @param y 
         */
        public void setY(Integer y){
            this.y = y;
        }
        /**
         * 
         * @param x 
         */
        public void setX(Integer x){
            this.x = x;
        }
        /**
         * 
         * @return 
         */
        public Cell getOwner(){
            return ownedByCell;
        }
        /**
         * 
         * @param cell 
         */
        public void setOwner(Cell cell){
            ownedByCell = cell;
        }
        /**
         * 
         * @param x
         * @param y
         * @return 
         */
        public Boolean isCoord(int x, int y){
            if (this.x == x && this.y == y){
                return true;
            }
            
            return false;
        }
}
