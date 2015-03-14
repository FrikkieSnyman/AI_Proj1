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
        int x,y;
        public Position(int x, int y){
            this.x = x;
            this.y = y;
        }
        
        public int getY(){
            return y;
        }
        
        public int getX(){
            return x;
        }
        
        public void setY(int y){
            this.y = y;
        }
        
        public void setX(int x){
            this.x = x;
        }
}
