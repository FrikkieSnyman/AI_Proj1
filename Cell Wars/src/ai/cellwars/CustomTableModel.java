/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.cellwars;

import java.util.LinkedList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author frikkie
 */
public class CustomTableModel extends AbstractTableModel{
    private LinkedList<Cell> cellList;
    private BlockType[][] blockType;
    
    private int rowCount, colCount;
    public CustomTableModel(LinkedList<Cell> cellList, int rowCount, int colCount){
        this.cellList = cellList;
        this.rowCount = rowCount;
        this.colCount = colCount;
        
        blockType = new BlockType[colCount][rowCount];
        for (int i = 0; i < cellList.size(); ++i){
            Cell tmp = cellList.get(i);
            Integer[] pos = tmp.getPosition();
            if (tmp.getColor().compareTo("blue") == 0){
                blockType[pos[0]][pos[1]] = BlockType.BLUE_OCCUPIED;
            } else{
                blockType[pos[0]][pos[1]] = BlockType.RED_OCCUPIED;
            }
        }
    }
    
    public BlockType getBlockType(int x, int y){
        return blockType[x][y];
    }
    
    public void setBlockType(int x, int y, BlockType bt){
        blockType[x][y] = bt;
    }
    
    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return colCount;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        return blockType[i][i1];
    }
    
    @Override
    public void setValueAt(Object bt, int row, int col){
        blockType[col][row] = (BlockType) bt;
        fireTableCellUpdated(row,col);
    }

}
