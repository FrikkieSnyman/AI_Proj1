/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.cellwars;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author frikkie
 */
public class CustomEditor extends DefaultCellEditor{
    private JTable table;
    private CustomTableModel model;
    
    public CustomEditor(JTable table){
        super(new JTextField());
        this.table = table;
        this.model = (CustomTableModel) table.getModel();
    }
    
    @Override
    public boolean stopCellEditing(){
        model.setBlockType(table.getEditingColumn(), table.getEditingRow(), BlockType.RED_INFLUENCED);
        return super.stopCellEditing();
        
    }
}
