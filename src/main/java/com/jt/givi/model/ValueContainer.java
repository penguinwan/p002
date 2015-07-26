package com.jt.givi.model;

/**
 * Created by superman on 7/24/2015.
 */
public class ValueContainer {
    private int machineNo;
    private int value;
    private Status.State state;

    public ValueContainer(int machineNo, int value, Status.State state) {
        this.machineNo = machineNo;
        this.value = value;
        this.state = state;
    }

    public int getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(int machineNo) {
        this.machineNo = machineNo;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Status.State getState() {
        return state;
    }

    public void setState(Status.State state) {
        this.state = state;
    }
}
