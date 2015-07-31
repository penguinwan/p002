/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jt.givi.core;

import com.jgoodies.binding.list.ArrayListModel;
import com.jt.givi.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author superman
 */
public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private MasterSetupManager masterSetupManager;
    private StateManager stateManager;
    private StorageManager storageManager;
    private PiCommunicationManager piCommunicationManager;
    private ConfigManager configManager;
    private MasterSetupTableModel masterSetupTableModel;
    private MachineTableModel machineTableModel;

    public Controller() throws IOException, ParseException {
        configManager = new ConfigManager();
        masterSetupManager = new MasterSetupManager(configManager.masterFilePath);
        stateManager = new StateManager(configManager.stateFilePath);
        storageManager = new StorageManager(configManager.logFolderPath);
        piCommunicationManager = new PiCommunicationManager(configManager.serialTimeout, configManager.sendDelay);
        initMasterSetupTableModel();
        initMachineTableModel();
    }

    private void initMasterSetupTableModel() throws IOException {
        logger.info("Initializing master setup table...");
        List<Mold> moldList = masterSetupManager.load();
        ArrayListModel listModel = new ArrayListModel();
        listModel.addAll(moldList);
        masterSetupTableModel = new MasterSetupTableModel(listModel);
    }

    private void initMachineTableModel() throws IOException, ParseException {
        logger.info("Initializing machine table...");
        List<Machine> machineList = stateManager.load();
        ArrayListModel listModel = new ArrayListModel();
        listModel.addAll(machineList);
        machineTableModel = new MachineTableModel(listModel);
    }

    public List<Mold> getMoldList() throws IOException {
        return masterSetupManager.load();
    }

    public List<Machine> getMachineList() {
        List<Machine> machineList = new ArrayList<>();
        for (int i = 0; i < machineTableModel.getListModel().size(); i++) {
            machineList.add((Machine) machineTableModel.getListModel().get(i));
        }
        return machineList;
    }

    public void saveMasterSetup() throws IOException, ParseException {
        List<Mold> moldList = new ArrayList<Mold>();
        for (Object obj : masterSetupTableModel.getListModel().toArray()) {
            Mold mold = (Mold) obj;
            moldList.add(mold);
        }
        masterSetupManager.save(moldList);
    }

    public void resetMachine(int machineNo, Mold selectedMold, int target, int actual) throws InterruptedException {
        int row = machineNo - 1;

        String remark = "";
        boolean actualHasChange = false;
        boolean partNoHasChange = false;

        Machine currentMachine = (Machine) machineTableModel.getListModel().get(row);
        Mold currentMold = currentMachine.getMold();

        if(currentMachine.getActual() != actual) {
            actualHasChange = true;
            remark += StorageManager.REMARK_ACTUAL_MODIFIED;
        }

        if (!selectedMold.getPartNo().equals(currentMold.getPartNo())) {
            partNoHasChange = true;
            remark += StorageManager.REMARK_PART_RESET;
        }

        machineTableModel.setValueAt(selectedMold.getPartNo(), row, Machine.Column.PART_NO.getIndex());
        machineTableModel.setValueAt(target, row, Machine.Column.TARGET.getIndex());
        machineTableModel.setValueAt(actual, row, Machine.Column.ACTUAL.getIndex());
        machineTableModel.setValueAt(selectedMold.getMultiply(), row, Machine.Column.MULTIPLY.getIndex());
        machineTableModel.fireTableDataChanged();

        if (actualHasChange || partNoHasChange) {
            try {
                storageManager.writeLog(currentMachine, remark);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (actualHasChange) {
            piCommunicationManager.adjust(machineNo, actual);
        }

        if (partNoHasChange) {
            piCommunicationManager.reset(machineNo);
        }
    }

    public void onClose() throws Exception {
        List<Machine> machineList = getMachineList();
        stateManager.save(machineList);
    }

    public void updateMachine(int machineNo) throws InterruptedException, TimeoutException, IOException {
        ValueContainer valueContainer = piCommunicationManager.getValue(machineNo);
        if(valueContainer.equals(ValueContainer.EMPTY)) {
            return;
        }
        logger.info("Communication return value={} state={}", valueContainer.getValue(), valueContainer.getState().getName());

        int row = machineNo - 1;

        Machine updatedMachine = (Machine) machineTableModel.getListModel().get(row);
        Status currentStatus = (Status) machineTableModel.getValueAt(row, Machine.Column.STATUS.getIndex());
        Status.State currentState = currentStatus.getState();
        Status.State newState = valueContainer.getState();

        if (!newState.equals(currentState)) {
            // reset datetime
            Status newStatus = new Status(newState, Calendar.getInstance().getTime());
            machineTableModel.setValueAt(newStatus, row, Machine.Column.STATUS.getIndex());

            // log state change
            String remark = StorageManager.REMARK_STATUS_CHANGED;
            storageManager.writeLog(updatedMachine, remark);
        }

        int counter = valueContainer.getValue();
        int value = counter * updatedMachine.getMold().getMultiply();
        machineTableModel.setValueAt(value, row, Machine.Column.ACTUAL.getIndex());
        machineTableModel.fireTableDataChanged();
    }

    public void logMachine() throws IOException, InterruptedException {
        List<Machine> machineList = getMachineList();
        for (Machine machine : machineList) {
            storageManager.writeLog(machine, "");
        }
    }

    public MasterSetupTableModel getMasterSetupTableModel() {
        return masterSetupTableModel;
    }

    public MachineTableModel getMachineTableModel() {
        return machineTableModel;
    }
}
