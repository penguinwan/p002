/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jt.givi;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author superman
 */
public class Controller {

    private TableModel tableModel;
    private Map<Pin, Integer> pinMap = new HashMap<Pin, Integer>();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Controller(TableModel model) {
        pinMap.put(RaspiPin.GPIO_04, 0);
        this.tableModel = model;
    }

    public void increaseActual(Pin pin) {
        int rowNumber = pinMap.get(pin);
        System.out.println("table model " + tableModel);
        int currentActual = (int) tableModel.getValueAt(rowNumber, TableModel.COL_ACTUAL);
        int newActual = currentActual + 1;
        System.out.println("increase actual rowNumber:" + rowNumber + " currentActual:" + currentActual);
        tableModel.setValueAt(newActual, rowNumber, TableModel.COL_ACTUAL);
    }

    public String dumpModel() {
        Calendar cal = Calendar.getInstance();
        String today = dateFormat.format(cal.getTime());
        String part = (String) tableModel.getValueAt(0, TableModel.COL_PART);
        String target = tableModel.getValueAt(0, TableModel.COL_TARGET).toString();
        String actual = tableModel.getValueAt(0, TableModel.COL_ACTUAL).toString();

        // use string format
        String concat = String.format("%s,%s,%s,%s%n",
                today,
                part,
                target,
                actual);
        return concat;
    }
}
