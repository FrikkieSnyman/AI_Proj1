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
public class Board {
    Integer boardSize;
    BoardUI boardUI = null;
    BlockType[][] blockType = null;
    
    public Board(Integer boardSize){
        this.boardSize = boardSize;
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
    
    public javax.swing.JTable getTable(){
        return boardUI.getTable();
    }
    
    public void setTable(javax.swing.JTable table){
        boardUI.setTable(table);
    }

    public void determineInfluenced(BlockType bt, BlockType occ) {
        for (int x = 0; x < boardSize; ++x){
            for (int y = 0; y < boardSize; ++y){
                if (blockType[x][y] == occ){
                    if ((y+1) < boardSize){
                        if (blockType[x][y+1] != occ){
                            blockType[x][y+1] = bt;
                        }
                        if ((x+1) < boardSize){
                            if (blockType[x+1][y+1] != occ){
                                blockType[x+1][y+1] = bt;
                            }
                        }
                        if((x-1) >= 0){
                            if (blockType[x-1][y+1] != occ){
                                blockType[x-1][y+1] = bt;
                            }
                        }
                    }
                    if((y-1) >= 0){
                        if (blockType[x][y-1] != occ){
                            blockType[x][y-1] = bt;
                        }
                        if ((x+1) < boardSize){
                            if (blockType[x+1][y-1] != occ){
                                blockType[x+1][y-1] = bt;
                            }
                        }
                        if((x-1) >= 0){
                            if (blockType[x-1][y-1] != occ){
                                blockType[x-1][y-1] = bt;
                            }
                        }
                    }
                    if ((x+1) < boardSize){
                        if (blockType[x+1][y] != occ){
                            blockType[x+1][y] = bt;
                        }
                        if ((y+1) < boardSize){
                            if (blockType[x+1][y+1] != occ){
                                blockType[x+1][y+1] = bt;
                            }
                        }
                        if((y-1) >= 0){
                            if (blockType[x+1][y-1] != occ){
                                blockType[x+1][y-1] = bt;
                            }
                        }
                    }
                    if ((x-1) >= 0){
                        if (blockType[x-1][y] != occ){
                            blockType[x-1][y] = bt;
                        }
                        if ((y+1) < boardSize){
                            if (blockType[x-1][y+1] != occ){
                                blockType[x-1][y+1] = bt;
                            }
                        }
                        if((y-1) >= 0){
                            if (blockType[x-1][y-1] != occ){
                                blockType[x-1][y-1] = bt;
                            }
                        }
                    }
                    
                }
            }
        }
    }
}
