/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jt.givi.core;

import com.jgoodies.binding.list.ArrayListModel;
import com.jt.givi.model.Machine;
import com.jt.givi.model.MachineTableModel;
import com.jt.givi.model.MasterSetupTableModel;
import com.jt.givi.model.Mold;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author superman
 */
public class Controller {
    public static final String MASTER_FILE_PATH = "C:/temp/master.csv";
    public static final String STATE_FILE_PATH = "C:/temp/state.csv";

    private MasterSetupManager masterSetupManager;
    private StateManager stateManager;
    private MasterSetupTableModel masterSetupTableModel;
    private MachineTableModel machineTableModel;

    public Controller() throws IOException, ParseException {
        masterSetupManager = new MasterSetupManager(MASTER_FILE_PATH);
        stateManager = new StateManager(STATE_FILE_PATH);
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
        List<Machine> machineList = new ArrayList<Machine>();
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
        machineTableModel.setValueAt(selectedMold.getPartNo(), row, Machine.Column.PART_NO.getIndex());
        machineTableModel.setValueAt(target, row, Machine.Column.TARGET.getIndex());
        machineTableModel.setValueAt(actual, row, Machine.Column.ACTUAL.getIndex());
        machineTableModel.setValueAt(selectedMold.getMultiply(), row, Machine.Column.MULTIPLY.getIndex());
    }

    public void saveMachineState() throws Exception {
        List<Machine> machineList = getMachineList();
        stateManager.save(machineList);
    }


    public MasterSetupTableModel getMasterSetupTableModel() {
        return masterSetupTableModel;
    }

    public MachineTableModel getMachineTableModel() {
        return machineTableModel;
    }
}
