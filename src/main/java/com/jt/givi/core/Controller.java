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

    public void saveMasterSetup() throws IOException, ParseException {
        List<Mold> moldList = new ArrayList<Mold>();
        for (Object obj : masterSetupTableModel.getListModel().toArray()) {
            Mold mold = (Mold) obj;
            moldList.add(mold);
        }
        masterSetupManager.save(moldList);
    }

    public void saveMachine(int machineNo, Mold selectedMold, int target, int actual) {
        int row = machineNo - 1;
        machineTableModel.setValueAt(selectedMold.getPartNo(), row, Machine.Column.PART_NO.getIndex());
        machineTableModel.setValueAt(target, row, Machine.Column.TARGET.getIndex());
        machineTableModel.setValueAt(actual, row, Machine.Column.ACTUAL.getIndex());
        machineTableModel.setValueAt(selectedMold.getMultiply(), row, Machine.Column.MULTIPLY.getIndex());
    }


    public MasterSetupTableModel getMasterSetupTableModel() {
        return masterSetupTableModel;
    }

    public MachineTableModel getMachineTableModel() {
        return machineTableModel;
    }
}
