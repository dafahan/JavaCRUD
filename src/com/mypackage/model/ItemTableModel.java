package com.mypackage.model;

import javax.swing.table.AbstractTableModel;
import com.mypackage.database.Item;

import java.util.List;

public class ItemTableModel extends AbstractTableModel {
    private List<Item> items;
    private String[] columnNames = { "ID", "Name", "Type", "Price", "Aksi" };
    
    public ItemTableModel(List<Item> items) {
        this.items = items;
 
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        if(rowIndex==0)return getColumnName(rowIndex);
        Item item = items.get(rowIndex);
        switch (columnIndex) {
            
            case 0: // ID column
                return item.getId();
            case 1: // Name column
                return item.getName();
            case 2: // Type column
                return item.getType();
            case 3: // Price column
                return item.getPrice();
            case 4: // Aksi column with buttons
                return "Edit/Delete";
            default:
                return null;
        }
    }

 
}
