/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jt.givi.model;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.ArrayListModel;

/**
 * @author superman
 */
public class MasterSetupTableModel extends AbstractTableAdapter {

    public static final String[] COL_NAMES = new String[]{"Part No", "Multiply"};

    private ArrayListModel listModel;

    public ArrayListModel getListModel() {
        return listModel;
    }

    public MasterSetupTableModel(ArrayListModel listModel) {
        super(listModel, COL_NAMES);
        this.listModel = listModel;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Mold mold = (Mold) getRow(rowIndex);
        if (columnIndex == 0) {
            return mold.getPartNo();
        } else {
            return mold.getMultiply();
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //super.setValueAt(aValue, rowIndex, columnIndex);
        if (columnIndex == 0) {
            ((Mold) listModel.get(rowIndex)).setPartNo(aValue.toString());
        } else {
            ((Mold) listModel.get(rowIndex)).setMultiply(aValue.toString());
        }
    }

    public void removeRow(int rowIndex) {
        listModel.remove(rowIndex);
    }

    public void addRow() {
        listModel.add(new Mold("", ""));
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

}
