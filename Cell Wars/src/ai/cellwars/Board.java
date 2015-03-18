/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.cellwars;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * 
 * @author frikkie
 */
public class Board {
    Integer boardSize;
    BoardUI boardUI = null;
    BlockType[][] blockType = null;
    LinkedList<Cell> cellList = null;
    LinkedList<Position> influenced  = null;
  /**
   * 
   * @param boardSize
   * @param cellList
   * @param game 
   */  
    public Board(Integer boardSize, LinkedList<Cell> cellList, Game game){
        this.boardSize = boardSize;
        this.cellList = cellList;
        boardUI = new BoardUI(boardSize, game);
        blockType = new BlockType[boardSize][boardSize];
        for (int i = 0; i < boardSize; ++i){
            Arrays.fill(blockType[i], BlockType.EMPTY);
        }
        boardUI.setVisible(true);
        this.influenced = new LinkedList<>();
    }
    /**
     * 
     * @return 
     */
    public BlockType[][] getBoard(){
        return this.blockType;
    }
    /**
     * 
     * @param bt
     * @param x
     * @param y 
     */
    public void setBlockType(BlockType bt, int x, int y){
        this.blockType[x][y] = bt;
    }
    /**
     * 
     * @param x
     * @param y
     * @return 
     */
    public BlockType getBlockType(int x, int y){
        return blockType[x][y];
    }
    /**
     * 
     * @return 
     */
    public javax.swing.JPanel getPanel(){
        return boardUI.getPanel();
    }
    /**
     * 
     * @param gameBoard 
     */
    public void setTable(Board gameBoard){
        boardUI.setBoard(gameBoard);
    }
    
    /**
     * 
     * @param bt
     * @param occ
     * @param cellList 
     */
    public void determineInfluenced(BlockType bt, BlockType occ, LinkedList<Cell> cellList) {
        for (int i = 0; i < cellList.size(); ++i){
            Cell cell = cellList.get(i);
            cell.infList = new LinkedList<>();
            clearInfluencedBlocks(bt);
            Integer x = cellList.get(i).positionX;
            Integer y = cellList.get(i).positionY;
            
            if (blockType[x][y] == occ){
                if ((y+1) < boardSize){
                    if (blockType[x][y+1] != occ){
                        blockType[x][y+1] = bt;
                        Position pos = new Position(x,y+1,cell);
                        cell.infList.add(pos);
                        influenced.add(pos);
                    }
                    if ((x+1) < boardSize){
                        if (blockType[x+1][y+1] != occ){
                            blockType[x+1][y+1] = bt;
                            Position pos = new Position(x,y+1,cell);
                            influenced.add(pos);
                            cell.infList.add(pos);
                        }
                    }
                    if((x-1) >= 0){
                        if (blockType[x-1][y+1] != occ){
                            blockType[x-1][y+1] = bt;
                            Position pos = new Position(x-1,y+1,cell);
                            influenced.add(pos);
                            cell.infList.add(pos);
                        }
                    }
                }
                if((y-1) >= 0){
                    if (blockType[x][y-1] != occ){
                        blockType[x][y-1] = bt;
                        Position pos = new Position(x,y-1,cell);
                        influenced.add(pos);
                        cell.infList.add(pos);
                    }
                    if ((x+1) < boardSize){
                        if (blockType[x+1][y-1] != occ){
                            blockType[x+1][y-1] = bt;
                            Position pos = new Position(x+1,y-1,cell);
                            influenced.add(pos);
                            cell.infList.add(pos);
                        }
                    }
                    if((x-1) >= 0){
                        if (blockType[x-1][y-1] != occ){
                            blockType[x-1][y-1] = bt;
                            Position pos = new Position(x-1,y-1,cell);
                            influenced.add(pos);
                            cell.infList.add(pos);
                        }
                    }
                }
                if ((x+1) < boardSize){
                    if (blockType[x+1][y] != occ){
                        blockType[x+1][y] = bt;
                        Position pos = new Position(x+1,y,cell);
                        influenced.add(pos);
                        cell.infList.add(pos);
                    }
                    if ((y+1) < boardSize){
                        if (blockType[x+1][y+1] != occ){
                            blockType[x+1][y+1] = bt;
                            Position pos = new Position(x+1,y+1,cell);
                            influenced.add(pos);
                            cell.infList.add(pos);
                        }
                    }
                    if((y-1) >= 0){
                        if (blockType[x+1][y-1] != occ){
                            blockType[x+1][y-1] = bt;
                            Position pos = new Position(x+1,y-1,cell);
                            influenced.add(pos);
                            cell.infList.add(pos);
                        }
                    }
                }
                if ((x-1) >= 0){
                    if (blockType[x-1][y] != occ){
                        blockType[x-1][y] = bt;
                        Position pos = new Position(x-1,y,cell);
                        influenced.add(pos);
                        cell.infList.add(pos);
                    }
                    if ((y+1) < boardSize){
                        if (blockType[x-1][y+1] != occ){
                            blockType[x-1][y+1] = bt;
                            Position pos = new Position(x-1,y+1,cell);
                            influenced.add(pos);
                            cell.infList.add(pos);
                        }
                    }
                    if((y-1) >= 0){
                        if (blockType[x-1][y-1] != occ){
                            blockType[x-1][y-1] = bt;
                            Position pos = new Position(x-1,y-1,cell);
                            influenced.add(pos);
                            cell.infList.add(pos);
                        }
                    }
                }
            }
            
            
        }
        
        determineFullInfluence(bt, occ, cellList);
        
    }
    /**
     * 
     * @param bt
     * @param occ
     * @param cellList 
     */
    private void determineFullInfluence(BlockType bt, BlockType occ, LinkedList<Cell> cellList) {
        LinkedList<Cell> temp = new LinkedList<>();
        temp.addAll(cellList);
        BlockType opp = null;
        BlockType oppBt = null;
        
        if (occ == BlockType.BLUE_OCCUPIED){
            opp = BlockType.RED_OCCUPIED;
            oppBt = BlockType.RED_INFLUENCED;
        } else {
            opp = BlockType.BLUE_OCCUPIED;
            oppBt = BlockType.BLUE_INFLUENCED;
        }
        
        while(temp.size() > 0){
            Cell cell = temp.remove();
            LinkedList<Cell> local = new LinkedList<>();
            local.add(cell);
            int minX;
            int minY;
            int maxX;
            int maxY;
            
            for (int i = 0; i < cell.infList.size(); ++i){
                Integer x = cell.infList.get(i).getX();
                Integer y = cell.infList.get(i).getY();
                
                if ((x-1) >= 0){
                    if (blockType[x-1][y] == bt){
                        for (int k = 0; k < influenced.size(); ++k){
                            if (influenced.get(k).isCoord(x-1, y)){
                                if (!local.contains(influenced.get(k).getOwner())){
                                    local.add(influenced.get(k).getOwner());
                                    temp.remove(influenced.get(k).getOwner());
                                }
                            }
                        }
                    } else if (blockType[x-1][y] == opp){
                        // Cell takeover
                        LinkedList<Cell> tmpList = null;
                        LinkedList<Cell> curList = null;
                        if (occ == BlockType.BLUE_OCCUPIED){
                            tmpList = boardUI.game.redPlayer.cellList;
                            curList = boardUI.game.bluePlayer.cellList;
                        } else {
                            tmpList = boardUI.game.bluePlayer.cellList;
                            curList = boardUI.game.redPlayer.cellList;
                        }
                        
                        for (int l = 0; l < tmpList.size(); ++l){
                            if (tmpList.get(l).positionX == (x-1) && tmpList.get(l).positionY == y){
                                Cell yolo = tmpList.remove(l);
                                yolo.color = cell.color;
                                yolo.infList = null;
                                curList.add(yolo);
                                temp.add(yolo);
                                blockType[x-1][y] = occ;
                                boardUI.game.drawCells();
                            }
                        }
                        
                    }
                }
                
                
                if ((x+1) < boardSize){
                    if (blockType[x+1][y] == bt){
                        for (int k = 0; k < influenced.size(); ++k){
                            if (influenced.get(k).isCoord(x+1, y)){
                                if (!local.contains(influenced.get(k).getOwner())){
                                    local.add(influenced.get(k).getOwner());
                                    temp.remove(influenced.get(k).getOwner());
                                }
                                
                            }
                        }
                    }
                }
                if ((y+1) < boardSize){
                    if (blockType[x][y+1] == bt){
                        for (int k = 0; k < influenced.size(); ++k){
                            if (influenced.get(k).isCoord(x, y+1)){
                                if (!local.contains(influenced.get(k).getOwner())){
                                    local.add(influenced.get(k).getOwner());
                                    temp.remove(influenced.get(k).getOwner());
                                }
                                
                            }
                        }
                    }
                }
                if ((y-1) >= 0){
                    if (blockType[x][y-1] == bt){
                        for (int k = 0; k < influenced.size(); ++k){
                            if (influenced.get(k).isCoord(x, y-1)){
                                if (!local.contains(influenced.get(k).getOwner())){
                                    local.add(influenced.get(k).getOwner());
                                    temp.remove(influenced.get(k).getOwner());
                                }
                                
                            }
                        }
                    }
                }
            } 

            minX = boardSize;
            maxX = 0;
            minY = boardSize;
            maxY = 0;
            Cell tmp = local.peek();
            
            for (int i = 0; i < tmp.infList.size(); ++i){
                
                if (tmp.infList.get(i).x < minX){
                    minX = tmp.infList.get(i).x;
                } else if (tmp.infList.get(i).x > maxX){
                    maxX = tmp.infList.get(i).x;
                }
                if (tmp.infList.get(i).y < minY){
                    minY = tmp.infList.get(i).y;
                } else if (tmp.infList.get(i).y > maxY){
                    maxY = tmp.infList.get(i).y;
                }
            }
            
            for (int j = 0; j < local.size(); ++j){
                Cell c = local.get(j);
                if (c != null){
                    for (int i = 0; i < c.infList.size(); ++i){
                        if (c.infList.get(i).x < minX){
                            minX = c.infList.get(i).x;
                        } else if (c.infList.get(i).x > maxX){
                            maxX = c.infList.get(i).x;
                        }
                        if (c.infList.get(i).y < minY){
                            minY = c.infList.get(i).y;
                        } else if (c.infList.get(i).y > maxY){
                            maxY = c.infList.get(i).y;
                        }
                    }
                }
            }
            
            for (int i = minX; i <= maxX; ++i){
                for (int j = minY; j <= maxY; ++j){
                    if (blockType[i][j] != occ){
                        blockType[i][j] = bt;
                        Cell own = getClosest(i,j,local);
                        Position tmpPos = new Position(i,j,own);
                        influenced.add(tmpPos);
                        for (int k = 0; k < local.size(); ++k){
                            local.get(k).moveCount = local.size();
                            local.get(k).infList.add(tmpPos);
                        }
                    }
                    
                }
            }
        }        
    }

    private void clearInfluencedBlocks(BlockType bt) {
        for (int i = 0; i < influenced.size(); ++i){
            if (bt == BlockType.BLUE_INFLUENCED){
//                if (influenced.get(i).getOwner().color.compareTo("blue") == 0 ){
                    influenced.remove(i);
//                }
            } else if (bt == BlockType.RED_INFLUENCED){
//                if (influenced.get(i).getOwner().color.compareTo("red") == 0 ){
                    influenced.remove(i);
//                }
            }
        }
        
        for (int i = 0; i < boardSize; ++i){
            for (int j = 0; j < boardSize; ++j){
                if (blockType[i][j] == bt){
                    for (int k = 0; k < influenced.size(); ++k){
                        if (influenced.get(k).isCoord(i, j)){
                            break;
                        }
                        blockType[i][j] = BlockType.EMPTY;
                    }
                }
            }
        }
    }

    private Cell getClosest(int i, int j, LinkedList<Cell> local) {
        Cell returnThis = local.get(0);
        int minx = local.get(0).positionX - i;
        int miny = local.get(0).positionY - j;
        double minr = Math.sqrt((minx*minx + miny*miny));
        
        for (int c = 1; c < local.size(); ++c){
            int x = Math.abs(local.get(0).positionX - i);
            int y = Math.abs(local.get(0).positionY - j);
            double r = Math.sqrt((x*x + y*y));
            
            if (minr > r){
                r = minr;
                returnThis = local.get(c);
            }
        }
        
        return returnThis;
    }
}
