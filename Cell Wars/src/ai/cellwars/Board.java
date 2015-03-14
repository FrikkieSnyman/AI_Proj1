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
public class Board {
    Integer boardSize;
    BoardUI boardUI = null;
    BlockType[][] blockType = null;
    LinkedList<Cell> cellList = null;
    
    public Board(Integer boardSize, LinkedList<Cell> cellList){
        this.boardSize = boardSize;
        this.cellList = cellList;
        boardUI = new BoardUI(boardSize);
        blockType = new BlockType[boardSize][boardSize];
        boardUI.setVisible(true);
    }
    
    public BlockType[][] getBoard(){
        return this.blockType;
    }
    
    public void setBlockType(BlockType bt, int x, int y){
        this.blockType[x][y] = bt;
    }
    
    public BlockType getBlockType(int x, int y){
        return blockType[x][y];
    }
    
    public javax.swing.JTable getTable(){
        return boardUI.getTable();
    }
    
    public void setTable(Board gameBoard){
        boardUI.setBoard(gameBoard);
    }
    
    public void setTable(javax.swing.JTable table){
        boardUI.setTable(table);
    }

    public void determineInfluenced(BlockType bt, BlockType occ) {

        for (int i = 0; i < cellList.size(); ++i){
            Cell cell = cellList.get(i);
            Integer x = cellList.get(i).positionX;
            Integer y = cellList.get(i).positionY;

            if (blockType[x][y] == occ){
                if ((y+1) < boardSize){
                    if (blockType[x][y+1] != occ){
                        blockType[x][y+1] = bt;
                        cell.infList.add(new Position(x,y+1));
                    }
                    if ((x+1) < boardSize){
                        if (blockType[x+1][y+1] != occ){
                            blockType[x+1][y+1] = bt;
                            cell.infList.add(new Position(x+1,y+1));
                        }
                    }
                    if((x-1) >= 0){
                        if (blockType[x-1][y+1] != occ){
                            blockType[x-1][y+1] = bt;
                            cell.infList.add(new Position(x-1,y+1));
                        }
                    }
                }
                if((y-1) >= 0){
                    if (blockType[x][y-1] != occ){
                        blockType[x][y-1] = bt;
                        cell.infList.add(new Position(x,y-1));
                    }
                    if ((x+1) < boardSize){
                        if (blockType[x+1][y-1] != occ){
                            blockType[x+1][y-1] = bt;
                            cell.infList.add(new Position(x+1,y-1));
                        }
                    }
                    if((x-1) >= 0){
                        if (blockType[x-1][y-1] != occ){
                            blockType[x-1][y-1] = bt;
                            cell.infList.add(new Position(x-1,y-1));
                        }
                    }
                }
                if ((x+1) < boardSize){
                    if (blockType[x+1][y] != occ){
                        blockType[x+1][y] = bt;
                        cell.infList.add(new Position(x+1,y));
                    }
                    if ((y+1) < boardSize){
                        if (blockType[x+1][y+1] != occ){
                            blockType[x+1][y+1] = bt;
                            cell.infList.add(new Position(x+1,y+1));
                        }
                    }
                    if((y-1) >= 0){
                        if (blockType[x+1][y-1] != occ){
                            blockType[x+1][y-1] = bt;
                            cell.infList.add(new Position(x+1,y-1));
                        }
                    }
                }
                if ((x-1) >= 0){
                    if (blockType[x-1][y] != occ){
                        blockType[x-1][y] = bt;
                        cell.infList.add(new Position(x-1,y));
                    }
                    if ((y+1) < boardSize){
                        if (blockType[x-1][y+1] != occ){
                            blockType[x-1][y+1] = bt;
                            cell.infList.add(new Position(x-1,y+1));
                        }
                    }
                    if((y-1) >= 0){
                        if (blockType[x-1][y-1] != occ){
                            blockType[x-1][y-1] = bt;
                            cell.infList.add(new Position(x-1,y-1));
                        }
                    }
                }

            }
        }
         

        
    }
}
