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
        public Position(Integer x, Integer y, Cell cell){
            this.x = x;
            this.y = y;
            this.ownedByCell = cell;
        }
        
        public Integer getY(){
            return y;
        }
        
        public Integer getX(){
            return x;
        }
        
        public void setY(Integer y){
            this.y = y;
        }
        
        public void setX(Integer x){
            this.x = x;
        }
        
        public Cell getOwner(){
            return ownedByCell;
        }
        
        public void setOwner(Cell cell){
            ownedByCell = cell;
        }
        
        public Boolean isCoord(int x, int y){
            if (this.x == x && this.y == y){
                return true;
            }
            
            return false;
        }
}
