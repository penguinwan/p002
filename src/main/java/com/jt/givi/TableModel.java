/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jt.givi;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author superman
 */
public class TableModel extends AbstractTableModel {

    private boolean editable = false;
    
    public static int COL_PART = 1;
    public static int COL_TARGET = 2;
    public static int COL_ACTUAL = 3;
    public static int COL_STATUS = 4;

    private String[] columnNames = {
        "No",
        "Part",
        "Target",
        "Actual",
        "Status"
    };
    private Object[][] data = {
        {"1", "Part", new Integer(0), new Integer(0), new Integer(0)},
        {"2", "Part", new Integer(0), new Integer(0), new Integer(0)},
        {"3", "Part", new Integer(0), new Integer(0), new Integer(0)},
        {"4", "Part", new Integer(0), new Integer(0), new Integer(0)},
        {"5", "Part", new Integer(0), new Integer(0), new Integer(0)},
        {"6", "Part", new Integer(0), new Integer(0), new Integer(0)},
        {"7", "Part", new Integer(0), new Integer(0), new Integer(0)},
        {"8", "Part", new Integer(0), new Integer(0), new Integer(0)},
        {"9", "Part", new Integer(0), new Integer(0), new Integer(0)},
        {"10", "Part", new Integer(0), new Integer(0), new Integer(0)}
    };

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        if (col == 0) {
            return false;
        } else {
            return editable;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {

        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    public void setEditable(boolean t) {
        this.editable = t;
    }
}
