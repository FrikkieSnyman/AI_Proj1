/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.cellwars;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author frikkie
 */
public class Board {
    private final ReentrantLock lock = new ReentrantLock();
    
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
//        boardUI.setVisible(true);
        this.influenced = new LinkedList<>();
    }
    
    public BlockType[][] cloneBoard() {
        BlockType[][] temp = new BlockType[boardSize][boardSize];
        
        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                temp[i][j] = blockType[i][j];
            }
        }
            
        return temp;
    }
    
    public void resetBoard(BlockType[][] bt) {
        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                blockType[i][j] = bt[i][j];
            }
        }
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
        if (boardUI.game.players == 0) {
            lock.lock();
        }
        
        try {
            BlockType opp = null;
            BlockType oppBt = null;

            if (occ == BlockType.BLUE_OCCUPIED){
                opp = BlockType.RED_OCCUPIED;
                oppBt = BlockType.RED_INFLUENCED;
            } else {
                opp = BlockType.BLUE_OCCUPIED;
                oppBt = BlockType.BLUE_INFLUENCED;
            }


            for (int i = 0; i < cellList.size(); ++i){
                Cell cell = cellList.get(i);
                cell.infList = new LinkedList<>();
                clearInfluencedBlocks(bt);
                Integer x = cellList.get(i).positionX;
                Integer y = cellList.get(i).positionY;

                if (blockType[x][y] == occ){
                    if ((y+1) < boardSize){
                        if (blockType[x][y+1] != occ && blockType[x][y+1] != opp){
                            blockType[x][y+1] = bt;
                            Position pos = new Position(x,y+1,cell);
                            cell.infList.add(pos);
                            influenced.add(pos);
                        }
                        if ((x+1) < boardSize){
                            if (blockType[x+1][y+1] != occ && blockType[x+1][y+1] != opp){
                                blockType[x+1][y+1] = bt;
                                Position pos = new Position(x,y+1,cell);
                                influenced.add(pos);
                                cell.infList.add(pos);
                            }
                        }
                        if((x-1) >= 0){
                            if (blockType[x-1][y+1] != occ && blockType[x-1][y+1] != opp){
                                blockType[x-1][y+1] = bt;
                                Position pos = new Position(x-1,y+1,cell);
                                influenced.add(pos);
                                cell.infList.add(pos);
                            }
                        }
                    }
                    if((y-1) >= 0){
                        if (blockType[x][y-1] != occ && blockType[x][y-1] != opp){
                            blockType[x][y-1] = bt;
                            Position pos = new Position(x,y-1,cell);
                            influenced.add(pos);
                            cell.infList.add(pos);
                        }
                        if ((x+1) < boardSize){
                            if (blockType[x+1][y-1] != occ && blockType[x+1][y-1] != opp){
                                blockType[x+1][y-1] = bt;
                                Position pos = new Position(x+1,y-1,cell);
                                influenced.add(pos);
                                cell.infList.add(pos);
                            }
                        }
                        if((x-1) >= 0){
                            if (blockType[x-1][y-1] != occ && blockType[x-1][y-1] != opp){
                                blockType[x-1][y-1] = bt;
                                Position pos = new Position(x-1,y-1,cell);
                                influenced.add(pos);
                                cell.infList.add(pos);
                            }
                        }
                    }
                    if ((x+1) < boardSize){
                        if (blockType[x+1][y] != occ && blockType[x+1][y] != opp){
                            blockType[x+1][y] = bt;
                            Position pos = new Position(x+1,y,cell);
                            influenced.add(pos);
                            cell.infList.add(pos);
                        }
                        if ((y+1) < boardSize){
                            if (blockType[x+1][y+1] != occ && blockType[x+1][y+1] != opp){
                                blockType[x+1][y+1] = bt;
                                Position pos = new Position(x+1,y+1,cell);
                                influenced.add(pos);
                                cell.infList.add(pos);
                            }
                        }
                        if((y-1) >= 0){
                            if (blockType[x+1][y-1] != occ && blockType[x+1][y-1] != opp){
                                blockType[x+1][y-1] = bt;
                                Position pos = new Position(x+1,y-1,cell);
                                influenced.add(pos);
                                cell.infList.add(pos);
                            }
                        }
                    }
                    if ((x-1) >= 0){
                        if (blockType[x-1][y] != occ && blockType[x-1][y] != opp){
                            blockType[x-1][y] = bt;
                            Position pos = new Position(x-1,y,cell);
                            influenced.add(pos);
                            cell.infList.add(pos);
                        }
                        if ((y+1) < boardSize){
                            if (blockType[x-1][y+1] != occ && blockType[x-1][y+1] != opp){
                                blockType[x-1][y+1] = bt;
                                Position pos = new Position(x-1,y+1,cell);
                                influenced.add(pos);
                                cell.infList.add(pos);
                            }
                        }
                        if((y-1) >= 0){
                            if (blockType[x-1][y-1] != occ && blockType[x-1][y-1] != opp){
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
        } finally {
            if (boardUI.game.players == 0) {
                lock.unlock();
            }
        }   
    }
    /**
     * 
     * @param bt
     * @param occ
     * @param cellList 
     */
    public void determineFullInfluenceV2(BlockType bt, BlockType occ, LinkedList<Cell> cellList){
//        influenced = new LinkedList<>();
        
//        clear(bt);
        BlockType opp = null;
        BlockType oppBt = null;
        
        if (occ == BlockType.BLUE_OCCUPIED){
            opp = BlockType.RED_OCCUPIED;
            oppBt = BlockType.RED_INFLUENCED;
        } else {
            opp = BlockType.BLUE_OCCUPIED;
            oppBt = BlockType.BLUE_INFLUENCED;
        }
        for (int i = 0; i < cellList.size(); ++i){
            Cell cell = cellList.get(i);
            cell.infList = new LinkedList<>();
            clearInfluencedBlocks(bt);
            Integer x = cellList.get(i).positionX;
            Integer y = cellList.get(i).positionY;
                
            if ((cellList.get(i).positionX - 1) >= 0 && ( (blockType[cellList.get(i).positionX - 1][cellList.get(i).positionY] != occ) && (blockType[cellList.get(i).positionX - 1][cellList.get(i).positionY] != opp))){
                blockType[cellList.get(i).positionX - 1][cellList.get(i).positionY] = bt;
                influenced.add(new Position(cellList.get(i).positionX - 1, cellList.get(i).positionY,cellList.get(i)));
                cellList.get(i).infList.add(influenced.getLast());
            }
            if ((cellList.get(i).positionX +1) < boardSize && ( (blockType[cellList.get(i).positionX + 1][cellList.get(i).positionY] != occ) && (blockType[cellList.get(i).positionX + 1][cellList.get(i).positionY] != opp))){
                blockType[cellList.get(i).positionX + 1][cellList.get(i).positionY] = bt;
                influenced.add(new Position(cellList.get(i).positionX + 1, cellList.get(i).positionY,cellList.get(i)));
                cellList.get(i).infList.add(influenced.getLast());
            }
            if ((cellList.get(i).positionY - 1) >= 0 && ( (blockType[cellList.get(i).positionX][cellList.get(i).positionY - 1] != occ) && (blockType[cellList.get(i).positionX][cellList.get(i).positionY - 1] != opp))){
                blockType[cellList.get(i).positionX][cellList.get(i).positionY - 1] = bt;
                influenced.add(new Position(cellList.get(i).positionX, cellList.get(i).positionY - 1,cellList.get(i)));
                cellList.get(i).infList.add(influenced.getLast());
            }
            if ((cellList.get(i).positionY + 1) < boardSize && ( (blockType[cellList.get(i).positionX][cellList.get(i).positionY + 1] != occ) && (blockType[cellList.get(i).positionX][cellList.get(i).positionY + 1] != opp))){
                blockType[cellList.get(i).positionX][cellList.get(i).positionY + 1] = bt;
                influenced.add(new Position(cellList.get(i).positionX, cellList.get(i).positionY + 1,cellList.get(i)));
                cellList.get(i).infList.add(influenced.getLast());
            }
            if ((((cellList.get(i).positionX - 1) >= 0) && ((cellList.get(i).positionY - 1) >= 0)) && ( (blockType[cellList.get(i).positionX - 1][cellList.get(i).positionY - 1] != occ) && (blockType[cellList.get(i).positionX - 1][cellList.get(i).positionY - 1] != opp))){
                blockType[cellList.get(i).positionX - 1][cellList.get(i).positionY - 1] = bt;
                influenced.add(new Position(cellList.get(i).positionX - 1, cellList.get(i).positionY - 1,cellList.get(i)));
                cellList.get(i).infList.add(influenced.getLast());
            }
            if ((((cellList.get(i).positionX - 1) >= 0) && ((cellList.get(i).positionY + 1) < boardSize))  && ( (blockType[cellList.get(i).positionX - 1][cellList.get(i).positionY + 1] != occ) && (blockType[cellList.get(i).positionX - 1][cellList.get(i).positionY + 1] != opp))){
                blockType[cellList.get(i).positionX - 1][cellList.get(i).positionY + 1] = bt;
                influenced.add(new Position(cellList.get(i).positionX - 1, cellList.get(i).positionY + 1,cellList.get(i)));
                cellList.get(i).infList.add(influenced.getLast());
            }
            if ((((cellList.get(i).positionX + 1) < boardSize) && ((cellList.get(i).positionY + 1) < boardSize))  && ( (blockType[cellList.get(i).positionX + 1][cellList.get(i).positionY + 1] != occ) && (blockType[cellList.get(i).positionX + 1][cellList.get(i).positionY + 1] != opp))){
                blockType[cellList.get(i).positionX + 1][cellList.get(i).positionY + 1] = bt;
                influenced.add(new Position(cellList.get(i).positionX + 1, cellList.get(i).positionY + 1,cellList.get(i)));
                cellList.get(i).infList.add(influenced.getLast());
            }
            if ((((cellList.get(i).positionX + 1) < boardSize) && ((cellList.get(i).positionY - 1) >= 0))  && ( (blockType[cellList.get(i).positionX + 1][cellList.get(i).positionY - 1] != occ) && (blockType[cellList.get(i).positionX + 1][cellList.get(i).positionY - 1] != opp))){
                blockType[cellList.get(i).positionX + 1][cellList.get(i).positionY - 1] = bt;
                influenced.add(new Position(cellList.get(i).positionX + 1, cellList.get(i).positionY - 1,cellList.get(i)));
                cellList.get(i).infList.add(influenced.getLast());
            }
            
            determineNeighbours(cellList.get(i),bt, occ);
//            determineFullInfluence(bt, occ, cellList);
        }
    }
    /**
     * 
     * @param cell
     * @param bt
     * @param occ 
     */
    private void determineNeighbours(Cell cell, BlockType bt, BlockType occ){
        Cell superCell = new Cell(cell.color);
        int cellX = cell.positionX;
        int cellY = cell.positionY;
        superCell.infList.addAll(cell.infList);
        LinkedList<Cell> local = new LinkedList<>();
        
        for (int i = 0; i < cell.infList.size(); ++i){
            Position tmp = cell.infList.get(i);
            
            if (((tmp.x - 1) >= 0) && (tmp.x < cellX)){
                if (blockType[tmp.x-1][tmp.y] == bt){
                    Cell owner = cell;
                    for (int j = 0; j < influenced.size(); ++j){
                        if (influenced.get(j).isCoord(tmp.x-1, tmp.y)){
                            owner = influenced.get(j).ownedByCell;
                            local.add(owner);
                            cell.neighbours.add(owner);
                            for (int p = 0; p < owner.neighbours.size(); ++p){
                                if (!local.contains(owner.neighbours.get(p))){
                                    local.add(owner.neighbours.get(p));
                                }
                            }
                        }
                    }  
                }
                if ((tmp.y + 1) < boardSize){
                    if (blockType[tmp.x-1][tmp.y+1] == bt){
//                        System.out.println(tmp.x + " " + tmp.y);
                        Cell owner = cell;
                        for (int j = 0; j < influenced.size(); ++j){
                            if (influenced.get(j).isCoord(tmp.x-1, tmp.y+1)){
                                owner = influenced.get(j).ownedByCell;
                                local.add(owner);
                                cell.neighbours.add(owner);
                                for (int p = 0; p < owner.neighbours.size(); ++p){
                                    if (!local.contains(owner.neighbours.get(p))){
                                        local.add(owner.neighbours.get(p));
                                    }
                                }
                            }
                        }  
                    }
                }
                if ((tmp.y - 1) >= 0){
                    if (blockType[tmp.x-1][tmp.y-1] == bt){
//                        System.out.println(tmp.x + " " + tmp.y);
                        Cell owner = cell;
                        for (int j = 0; j < influenced.size(); ++j){
                            if (influenced.get(j).isCoord(tmp.x-1, tmp.y-1)){
                                owner = influenced.get(j).ownedByCell;
                                local.add(owner);
                                cell.neighbours.add(owner);
                                for (int p = 0; p < owner.neighbours.size(); ++p){
                                    if (!local.contains(owner.neighbours.get(p))){
                                        local.add(owner.neighbours.get(p));
                                    }
                                }
                            }
                        }  
                    }
                }
            }
            
            if (((tmp.x + 1) < boardSize) && (tmp.x > cellX)){
                if (blockType[tmp.x+1][tmp.y] == bt){
                    Cell owner = cell;
                    for (int j = 0; j < influenced.size(); ++j){
                        if (influenced.get(j).isCoord(tmp.x+1, tmp.y)){
                            owner = influenced.get(j).ownedByCell;
                            local.add(owner);
                            cell.neighbours.add(owner);
                            for (int p = 0; p < owner.neighbours.size(); ++p){
                                if (!local.contains(owner.neighbours.get(p))){
                                    local.add(owner.neighbours.get(p));
                                }
                            }
                        }
                    }
                    
                }
                if ((tmp.y + 1) < boardSize){
                    if (blockType[tmp.x+1][tmp.y+1] == bt){
//                        System.out.println(tmp.x + " " + tmp.y);
                        Cell owner = cell;
                        for (int j = 0; j < influenced.size(); ++j){
                            if (influenced.get(j).isCoord(tmp.x+1, tmp.y+1)){
                                owner = influenced.get(j).ownedByCell;
                                local.add(owner);
                                cell.neighbours.add(owner);
                                for (int p = 0; p < owner.neighbours.size(); ++p){
                                    if (!local.contains(owner.neighbours.get(p))){
                                        local.add(owner.neighbours.get(p));
                                    }
                                }
                            }
                        }  
                    }
                }
                if ((tmp.y - 1) >= 0){
                    if (blockType[tmp.x+1][tmp.y-1] == bt){
//                        System.out.println(tmp.x + " " + tmp.y);
                        Cell owner = cell;
                        for (int j = 0; j < influenced.size(); ++j){
                            if (influenced.get(j).isCoord(tmp.x+1, tmp.y-1)){
                                owner = influenced.get(j).ownedByCell;
                                local.add(owner);
                                cell.neighbours.add(owner);
                                for (int p = 0; p < owner.neighbours.size(); ++p){
                                    if (!local.contains(owner.neighbours.get(p))){
                                        local.add(owner.neighbours.get(p));
                                    }
                                }
                            }
                        }  
                    }
                }
            }
            
            if (((tmp.y - 1) >= 0) && (tmp.y < cellY)){
                if (blockType[tmp.x][tmp.y-1] == bt){
                    Cell owner = cell;
                    for (int j = 0; j < influenced.size(); ++j){
                        if (influenced.get(j).isCoord(tmp.x, tmp.y-1)){
                            owner = influenced.get(j).ownedByCell;
                            local.add(owner);
                            cell.neighbours.add(owner);
                            for (int p = 0; p < owner.neighbours.size(); ++p){
                                if (!local.contains(owner.neighbours.get(p))){
                                    local.add(owner.neighbours.get(p));
                                }
                            }
                        }
                    }
                    
                }
                if ((tmp.x + 1) < boardSize){
                    if (blockType[tmp.x+1][tmp.y-1] == bt){
//                        System.out.println(tmp.x + " " + tmp.y);
                        Cell owner = cell;
                        for (int j = 0; j < influenced.size(); ++j){
                            if (influenced.get(j).isCoord(tmp.x+1, tmp.y-1)){
                                owner = influenced.get(j).ownedByCell;
                                local.add(owner);
                                cell.neighbours.add(owner);
                                for (int p = 0; p < owner.neighbours.size(); ++p){
                                    if (!local.contains(owner.neighbours.get(p))){
                                        local.add(owner.neighbours.get(p));
                                    }
                                }
                            }
                        }  
                    }
                }
                if ((tmp.x - 1) >= 0){
                    if (blockType[tmp.x-1][tmp.y-1] == bt){
//                        System.out.println(tmp.x + " " + tmp.y);
                        Cell owner = cell;
                        for (int j = 0; j < influenced.size(); ++j){
                            if (influenced.get(j).isCoord(tmp.x-1, tmp.y-1)){
                                owner = influenced.get(j).ownedByCell;
                                local.add(owner);
                                cell.neighbours.add(owner);
                                for (int p = 0; p < owner.neighbours.size(); ++p){
                                    if (!local.contains(owner.neighbours.get(p))){
                                        local.add(owner.neighbours.get(p));
                                    }
                                }
                            }
                        }  
                    }
                }
            }
            
            if (((tmp.y + 1) < boardSize) && (tmp.y > cellY)){
                if (blockType[tmp.x][tmp.y+1] == bt){
                    Cell owner = cell;
                    for (int j = 0; j < influenced.size(); ++j){
                        if (influenced.get(j).isCoord(tmp.x, tmp.y+1)){
                            owner = influenced.get(j).ownedByCell;
                            local.add(owner);
                            cell.neighbours.add(owner);
                            for (int p = 0; p < owner.neighbours.size(); ++p){
                                if (!local.contains(owner.neighbours.get(p))){
                                    local.add(owner.neighbours.get(p));
                                }
                            }
                        }
                    }
                    
                }
                if ((tmp.x + 1) < boardSize){
                    if (blockType[tmp.x+1][tmp.y+1] == bt){
//                        System.out.println(tmp.x + " " + tmp.y);
                        Cell owner = cell;
                        for (int j = 0; j < influenced.size(); ++j){
                            if (influenced.get(j).isCoord(tmp.x+1, tmp.y+1)){
                                owner = influenced.get(j).ownedByCell;
                                local.add(owner);
                                cell.neighbours.add(owner);
                                for (int p = 0; p < owner.neighbours.size(); ++p){
                                    if (!local.contains(owner.neighbours.get(p))){
                                        local.add(owner.neighbours.get(p));
                                    }
                                }
                            }
                        }  
                    }
                }
                if ((tmp.x - 1) >= 0){
                    if (blockType[tmp.x-1][tmp.y+1] == bt){
//                        System.out.println(tmp.x + " " + tmp.y);
                        Cell owner = cell;
                        for (int j = 0; j < influenced.size(); ++j){
                            if (influenced.get(j).isCoord(tmp.x-1, tmp.y+1)){
                                owner = influenced.get(j).ownedByCell;
                                local.add(owner);
                                cell.neighbours.add(owner);
                                for (int p = 0; p < owner.neighbours.size(); ++p){
                                    if (!local.contains(owner.neighbours.get(p))){
                                        local.add(owner.neighbours.get(p));
                                    }
                                }
                            }
                        }  
                    }
                }
                
            }
        }
        
        for (int i = 0; i < local.size(); ++i){
            for (int j = 0; j < local.get(i).infList.size(); ++ j){
                if (!superCell.infList.contains(local.get(i).infList.get(j)))
                    superCell.infList.add(local.get(i).infList.get(j));
            }
//            superCell.infList.addAll(local.get(i).infList);
        }
        
        int xArr[] = new int[superCell.infList.size()];
        int yArr[] = new int[superCell.infList.size()];
        for (int k = 0; k < superCell.infList.size(); ++k){
            xArr[k] = superCell.infList.get(k).x;
            yArr[k] = superCell.infList.get(k).y;
            for (int l = 0; l<local.size(); ++l){
                local.get(l).infList.add(superCell.infList.get(k));
            }
        }
        Arrays.sort(xArr);
        Arrays.sort(yArr);
        int minx = xArr[0];
        int maxx = xArr[superCell.infList.size()-1];
        int miny = yArr[0];
        int maxy = yArr[superCell.infList.size()-1];
        
        for (int x = minx; x <= maxx; ++x){
            for (int y = miny; y <=maxy; ++y){
                if (blockType[x][y] == BlockType.EMPTY){
                    blockType[x][y] = bt;
                    Position newPos = new Position(x,y,cell);
                    cell.infList.add(newPos);
                    superCell.infList.add(newPos);
                    influenced.add(newPos);
                }
            }
        }
//       System.out.println(influenced.size());  
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
                                  
                                boardUI.game.determineInfluence();
//                                boardUI.game.drawCells();
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
                    } else if (blockType[x+1][y] == opp){
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
                            if (tmpList.get(l).positionX == (x+1) && tmpList.get(l).positionY == y){
                                Cell yolo = tmpList.remove(l);
                                yolo.color = cell.color;
                                yolo.infList = null;
                                curList.add(yolo);
                                temp.add(yolo);
                                blockType[x+1][y] = occ;
                                  
                                boardUI.game.determineInfluence();
//                                boardUI.game.drawCells();
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
                    } else if (blockType[x][y+1] == opp){
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
                            if (tmpList.get(l).positionX == (x) && tmpList.get(l).positionY == (y+1)){
                                Cell yolo = tmpList.remove(l);
                                yolo.color = cell.color;
                                yolo.infList = null;
                                curList.add(yolo);
                                temp.add(yolo);
                                blockType[x][y+1] = occ;
                                  
                                boardUI.game.determineInfluence();
//                                boardUI.game.drawCells();
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
                    } else if (blockType[x][y-1] == opp){
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
                            if (tmpList.get(l).positionX == (x) && tmpList.get(l).positionY == (y-1)){
                                Cell yolo = tmpList.remove(l);
                                yolo.color = cell.color;
                                yolo.infList = null;
                                curList.add(yolo);
                                temp.add(yolo);
                                blockType[x][y-1] = occ;
                                  
                                boardUI.game.determineInfluence();
//                                boardUI.game.drawCells();
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
    
    private void clear(BlockType bt){
        
        for (int i = 0; i < boardSize; ++i){
            for (int j = 0; j < boardSize; ++j){
                if (blockType[i][j] == bt){
                    blockType[i][j] = BlockType.EMPTY;
                }
            }
        }
    }

    private void clearInfluencedBlocks(BlockType bt) {
        for (int i = 0; i < influenced.size(); ++i){
            if (bt == BlockType.BLUE_INFLUENCED){
//                if (influenced.get(i).getOwner().color.compareTo("blue") == 0 ){
//                if (influenced.get(i) != null);
                    influenced.remove(i);
//                }
            } else if (bt == BlockType.RED_INFLUENCED){
//                if (influenced.get(i).getOwner().color.compareTo("red") == 0 ){
//                if (influenced.get(i) != null);
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
