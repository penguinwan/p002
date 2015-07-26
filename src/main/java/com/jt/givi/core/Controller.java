/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jt.givi.core;

import com.jgoodies.binding.list.ArrayListModel;
import com.jt.givi.model.*;

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
//    public static final String MASTER_FILE_PATH = "/media/share/master.csv";
//    public static final String STATE_FILE_PATH = "/media/share/state.csv";
//    public static final String LOG_FILE_PATH = "/media/share";
    public static final String MASTER_FILE_PATH = "C:/temp/master.csv";
    public static final String STATE_FILE_PATH = "C:/temp/state.csv";
    public static final String LOG_FILE_PATH = "C/temp";
    public static final int SERIAL_TIMEOUT = 8000;

    private MasterSetupManager masterSetupManager;
    private StateManager stateManager;
    private StorageManager storageManager;
    private PiCommunicationManager piCommunicationManager;
    private MasterSetupTableModel masterSetupTableModel;
    private MachineTableModel machineTableModel;

    public Controller() throws IOException, ParseException {
        masterSetupManager = new MasterSetupManager(MASTER_FILE_PATH);
        stateManager = new StateManager(STATE_FILE_PATH);
        storageManager = new StorageManager(LOG_FILE_PATH);
        piCommunicationManager = new PiCommunicationManager(SERIAL_TIMEOUT);
        initMasterSetupTableModel();
        initMachineTableModel();
    }

    private void initMasterSetupTableModel() throws IOException {
        List<Mold> moldList = masterSetupManager.load();
        ArrayListModel listModel = new ArrayListModel();
        listModel.addAll(moldList);
        masterSetupTableModel = new MasterSetupTableModel(listModel);
    }

    private void initMachineTableModel() throws IOException, ParseException {
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

    public void resetMachine(int machineNo, Mold selectedMold, int target, int actual) {
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

    public void saveMachineState() throws Exception {
        List<Machine> machineList = getMachineList();
        stateManager.save(machineList);
    }

    public void updateMachineValue(int machineNo) throws InterruptedException, TimeoutException, IOException {
        ValueContainer valueContainer = piCommunicationManager.getValue(machineNo);
        System.out.println(String.format("Communication return value:%d state:%s", valueContainer.getValue(), valueContainer.getState().getName()));

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
        //TODO: change multiply to integer
        //int value = counter * updatedMachine.getMold().getMultiply();
        machineTableModel.setValueAt(valueContainer.getValue(), row, Machine.Column.ACTUAL.getIndex());
    }

    public MasterSetupTableModel getMasterSetupTableModel() {
        return masterSetupTableModel;
    }

    public MachineTableModel getMachineTableModel() {
        return machineTableModel;
    }
}
