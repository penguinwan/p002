package com.jt.givi.gui;

import com.jt.givi.model.Machine;
import com.jt.givi.model.Status;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created by superman on 7/20/2015.
 */
public class MachineTable extends JTable {

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
        Component comp = super.prepareRenderer(renderer, row, col);
        if (col == Machine.Column.STATUS.getIndex()) {
            Status status = (Status) getModel().getValueAt(row, col);

            if (status.getState().equals(Status.State.BLACK)) {
                comp.setBackground(Color.BLACK);
                comp.setForeground(Color.WHITE);
            } else if (status.getState().equals(Status.State.GREEN)) {
                comp.setBackground(Color.GREEN);
                comp.setForeground(Color.BLACK);
            } else if (status.getState().equals(Status.State.RED)) {
                comp.setBackground(Color.RED);
                comp.setForeground(Color.BLACK);
            } else if (status.getState().equals(Status.State.YELLOW)) {
                comp.setBackground(Color.YELLOW);
                comp.setForeground(Color.BLACK);
            }
        } else {
            if(row % 2 == 0) {
                comp.setBackground(Color.LIGHT_GRAY);
                comp.setForeground(Color.BLACK);
            } else {
                comp.setBackground(Color.WHITE);
                comp.setForeground(Color.BLACK);
            }
        }
        return comp;
    }
}
