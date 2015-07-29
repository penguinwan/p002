package com.jt.givi.core;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.jt.givi.model.Machine;
import com.jt.givi.model.Mold;
import com.jt.givi.model.Status;
import com.jt.givi.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by superman on 7/18/2015.
 */
public class StateManager {
    private static final Logger logger = LoggerFactory.getLogger(StateManager.class);
    private String filePath;

    public StateManager(String filePath) {
        this.filePath = filePath;
    }

    public List<Machine> load() throws IOException, NumberFormatException, ParseException {
        logger.info("Loading machine state...");
        FileReader fileReader = new FileReader(filePath);
        CSVReader reader = new CSVReader(fileReader);
        List<String[]> content = reader.readAll();
        List<Machine> machineList = new ArrayList<Machine>();
        for (String[] strArr : content) {
            String strMachineNo = strArr[Machine.Column.NO.getIndex()];
            String partNo = strArr[Machine.Column.PART_NO.getIndex()];
            String strTarget = strArr[Machine.Column.TARGET.getIndex()];
            String strActual = strArr[Machine.Column.ACTUAL.getIndex()];
            String strMultiply = strArr[Machine.Column.MULTIPLY.getIndex()];
            String strStatus = strArr[Machine.Column.STATUS.getIndex()];
            String strLastChanged = strArr[Machine.Column.LAST_CHANGED.getIndex()];

            int machineNo = Integer.valueOf(strMachineNo);
            int target = Integer.valueOf(strTarget);
            int actual = Integer.valueOf(strActual);
            int multiply = Integer.valueOf(strMultiply);

            Status status = new Status(
                    Status.State.forName(strStatus),
                    Util.dateTimeFromString(strLastChanged));

            Mold mold = new Mold(partNo, multiply);
            Machine machine = new Machine(machineNo, mold, target, actual, status);
            machineList.add(machine);
        }

        Collections.sort(machineList, getComparator());
        return machineList;
    }

    public void save(List<Machine> machineList) throws IOException {
        logger.info("Saving machine state...");
        Collections.sort(machineList, getComparator());
        List<String[]> content = new ArrayList<String[]>();
        for (Machine machine : machineList) {
            String[] line = new String[7];
            line[Machine.Column.NO.getIndex()] = String.valueOf(machine.getMachineNo());
            line[Machine.Column.PART_NO.getIndex()] = machine.getMold().getPartNo();
            line[Machine.Column.TARGET.getIndex()] = String.valueOf(machine.getTarget());
            line[Machine.Column.ACTUAL.getIndex()] = String.valueOf(machine.getActual());
            line[Machine.Column.MULTIPLY.getIndex()] = String.valueOf(machine.getMold().getMultiply());
            line[Machine.Column.STATUS.getIndex()] = machine.getStatus().getState().toString();
            line[Machine.Column.LAST_CHANGED.getIndex()] = Util.dateTimeToString(machine.getStatus().getLastChanged());
            content.add(line);
        }

        FileWriter fileWriter = new FileWriter(new File(filePath), false);
        CSVWriter writer = new CSVWriter(fileWriter);
        writer.writeAll(content);
        writer.close();
    }

    public Comparator getComparator() {
        return new Comparator<Machine>() {
            @Override
            public int compare(Machine o1, Machine o2) {
                if (o1.getMachineNo() > o2.getMachineNo()) {
                    return 1;
                } else if (o1.getMachineNo() < o2.getMachineNo()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };
    }
}
