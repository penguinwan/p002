/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jt.givi;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author superman
 */
public class MyTable extends JTable {

    public MyTable() {
        setFocusable(true);
        setColumnSelectionAllowed(false);
        setRowSelectionAllowed(false);

        InputMap im = getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap am = getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "F2");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0), "F9");
        am.put("F2", new EditAction());
        am.put("F9", new SaveAction());
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
        Component comp = super.prepareRenderer(renderer, row, col);
        if (col == TableModel.COL_STATUS) {
            int value = (int) getModel().getValueAt(row, col);
            if (value == 1) {
                comp.setBackground(Color.GREEN);
            } else if (value == 2) {
                comp.setBackground(Color.RED);
            } else if (value == 3) {
                comp.setBackground(Color.YELLOW);
            } else {
                comp.setBackground(Color.WHITE);
            }
            return comp;
        } else {
            comp.setBackground(Color.WHITE);
            return comp;
        }
    }

    private final class EditAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            setColumnSelectionAllowed(true);
            setRowSelectionAllowed(true);
            TableModel tableModel = (TableModel) getModel();
            tableModel.setEditable(true);

            int row = 0;
            int col = 1;
            boolean success = editCellAt(row, col);
            if (success) {
                boolean toggle = false;
                boolean extend = false;
                changeSelection(row, col, toggle, extend);
            }
        }

    }

    private final class SaveAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            setColumnSelectionAllowed(false);
            setRowSelectionAllowed(false);
            TableModel tableModel = (TableModel) getModel();
            tableModel.setEditable(false);
        }

    }

}
