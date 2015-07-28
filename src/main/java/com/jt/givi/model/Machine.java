package com.jt.givi.model;

/**
 * Created by superman on 7/18/2015.
 */
public class Machine {
    public enum Column {
        NO(0), PART_NO(1), TARGET(2), ACTUAL(3), MULTIPLY(4), STATUS(5), LAST_CHANGED(6);
        private int index;

        Column(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }

    private int machineNo;
    private Mold mold;
    private int target;
    private int actual;
    private Status status;

    public Machine(int machineNo, Mold mold, int target, int actual, Status status) {
        this.machineNo = machineNo;
        this.mold = mold;
        this.target = target;
        this.actual = actual;
        this.status = status;
    }

    public int getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(int machineNo) {
        this.machineNo = machineNo;
    }

    public Mold getMold() {
        return mold;
    }

    public void setMold(Mold mold) {
        this.mold = mold;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
