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
    LinkedList<Position> influenced  = null;
    
    public Board(Integer boardSize, LinkedList<Cell> cellList, Game game){
        this.boardSize = boardSize;
        this.cellList = cellList;
        boardUI = new BoardUI(boardSize, game);
        blockType = new BlockType[boardSize][boardSize];
        boardUI.setVisible(true);
        this.influenced = new LinkedList<>();
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
    
    public javax.swing.JPanel getPanel(){
        return boardUI.getPanel();
    }
    
    public void setTable(Board gameBoard){
        boardUI.setBoard(gameBoard);
    }
    
//    public void setTable(javax.swing.JTable table){
//        boardUI.setTable(table);
//    }

    public void determineInfluenced(BlockType bt, BlockType occ, LinkedList<Cell> cellList) {

        for (int i = 0; i < cellList.size(); ++i){
            Cell cell = cellList.get(i);
            Integer x = cellList.get(i).positionX;
            Integer y = cellList.get(i).positionY;
            
            if (blockType[x][y] == occ){
                System.out.println("here");
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

    private void determineFullInfluence(BlockType bt, BlockType occ, LinkedList<Cell> cellList) {
        LinkedList<Cell> temp = new LinkedList<>();
        temp.addAll(cellList);
        
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
                                    local.add(influenced.get(k).getOwner());
                                    temp.remove(influenced.get(k).getOwner());
                                }
                            }
                        }
                        if ((y-1) >= 0){
                            if (blockType[x-1][y-1] == bt){
                                for (int k = 0; k < influenced.size(); ++k){
                                    if (influenced.get(k).isCoord(x-1, y-1)){
                                        local.add(influenced.get(k).getOwner());
                                        temp.remove(influenced.get(k).getOwner());
                                    }
                                }
                            }
                        }
                        if ((y+1) < boardSize){
                            if (blockType[x-1][y+1] == bt){
                                for (int k = 0; k < influenced.size(); ++k){
                                    if (influenced.get(k).isCoord(x-1, y+1)){
                                        local.add(influenced.get(k).getOwner());
                                        temp.remove(influenced.get(k).getOwner());
                                    }
                                }
                            }
                        }                     
                }
                if ((x+1) < boardSize){
                        if (blockType[x+1][y] == bt){
                            for (int k = 0; k < influenced.size(); ++k){
                                if (influenced.get(k).isCoord(x+1, y)){
                                    local.add(influenced.get(k).getOwner());
                                    temp.remove(influenced.get(k).getOwner());
                                }
                            }
                        }
                        if ((y+1) < boardSize){
                            if (blockType[x+1][y+1] == bt){
                                for (int k = 0; k < influenced.size(); ++k){
                                    if (influenced.get(k).isCoord(x+1, y+1)){
                                        local.add(influenced.get(k).getOwner());
                                        temp.remove(influenced.get(k).getOwner());
                                    }
                                }
                            }    
                        }
                        if ((y-1) >= 0){
                            if (blockType[x+1][y-1] == bt){
                                for (int k = 0; k < influenced.size(); ++k){
                                    if (influenced.get(k).isCoord(x+1, y-1)){
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
                                    local.add(influenced.get(k).getOwner());
                                    temp.remove(influenced.get(k).getOwner());
                                }
                            }
                        }
                        if ((x+1) < boardSize){
                            if (blockType[x+1][y+1] == bt){
                                for (int k = 0; k < influenced.size(); ++k){
                                    if (influenced.get(k).isCoord(x+1, y+1)){
                                        local.add(influenced.get(k).getOwner());
                                        temp.remove(influenced.get(k).getOwner());
                                    }
                                }
                            }
                        }
                        if ((x-1) >= 0){
                            if (blockType[x-1][y+1] == bt){
                                for (int k = 0; k < influenced.size(); ++k){
                                    if (influenced.get(k).isCoord(x-1, y+1)){
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
                                    local.add(influenced.get(k).getOwner());
                                    temp.remove(influenced.get(k).getOwner());
                                }
                            }
                        }
                        if ((x+1) < boardSize){
                            if (blockType[x+1][y-1] == bt){
                                for (int k = 0; k < influenced.size(); ++k){
                                    if (influenced.get(k).isCoord(x+1, y-1)){
                                        local.add(influenced.get(k).getOwner());
                                        temp.remove(influenced.get(k).getOwner());
                                    }
                                }
                            }
                        }
                        if ((x-1) >= 0){
                            if (blockType[x-1][y-1] == bt){
                                for (int k = 0; k < influenced.size(); ++k){
                                    if (influenced.get(k).isCoord(x-1, y-1)){
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
            Cell tmp = local.remove();
            
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
                        influenced.add(new Position(i,j,null));
                        for (int k = 0; k < local.size(); ++k){
                            if (local.get(k) != null){
                                local.get(k).infList.add(new Position(i,j,local.get(k)));
                            }
                        }
                    }
                   
                    System.out.println(blockType[i][j].name());
                    
                }
            }
        }
        
        
    }
}
