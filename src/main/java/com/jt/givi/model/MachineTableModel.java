package com.jt.givi.model;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.ArrayListModel;
import com.jt.givi.util.Util;

/**
 * Created by superman on 7/18/2015.
 */
public class MachineTableModel extends AbstractTableAdapter {
    public static final String[] COL_NAMES = new String[]{
            "Machine No",
            "Part No",
            "Target",
            "Actual",
            "Multiply",
            "Status"};
    private ArrayListModel listModel;

    public ArrayListModel getListModel() {
        return listModel;
    }

    public MachineTableModel(ArrayListModel listModel) {
        super(listModel, COL_NAMES);
        this.listModel = listModel;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Machine machine = ((Machine) listModel.get(rowIndex));
        if (columnIndex == Machine.Column.NO.getIndex()) {
            return machine.getMachineNo();
        } else if (columnIndex == Machine.Column.PART_NO.getIndex()) {
            return machine.getMold().getPartNo();
        } else if (columnIndex == Machine.Column.TARGET.getIndex()) {
            return machine.getTarget();
        } else if (columnIndex == Machine.Column.ACTUAL.getIndex()) {
            return machine.getActual();
        } else if (columnIndex == Machine.Column.MULTIPLY.getIndex()) {
            return machine.getMold().getMultiply();
        } else if (columnIndex == Machine.Column.STATUS.getIndex()) {
            return machine.getStatus();
        } else {
            return "NULL";
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) throws NumberFormatException {
        Machine machine = (Machine) listModel.get(rowIndex);
        if (columnIndex == Machine.Column.PART_NO.getIndex()) {
            machine.getMold().setPartNo((String) aValue);
        } else if (columnIndex == Machine.Column.TARGET.getIndex()) {
            machine.setTarget((Integer) aValue);
        } else if (columnIndex == Machine.Column.ACTUAL.getIndex()) {
            machine.setActual((Integer) aValue);
        } else if (columnIndex == Machine.Column.MULTIPLY.getIndex()) {
            machine.getMold().setMultiply((Integer) aValue);
        } else if (columnIndex == Machine.Column.STATUS.getIndex()) {
            machine.setStatus((Status) aValue);
        } else {
            throw new IllegalArgumentException("Invalid column index:" + columnIndex);
        }
    }
}
