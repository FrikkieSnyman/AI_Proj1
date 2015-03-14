/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.cellwars;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

/**
 *
 * @author frikkie
 */
public class CustomTableCellRenderer extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component c = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
        CustomTableModel model = (CustomTableModel) table.getModel();
        System.out.println("here");
        if (model.getBlockType(column, row) == BlockType.RED_OCCUPIED){
            c.setBackground(Color.RED);
        } else if (model.getBlockType(column, row) == BlockType.RED_OCCUPIED){
            c.setBackground(Color.BLUE);
        }
        return c;
    }
}
