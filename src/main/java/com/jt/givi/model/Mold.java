/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jt.givi.model;

/**
 * @author superman
 */
public class Mold {

    private String partNo;
    private String multiply;

    public Mold(String partNo, String multiply) {
        this.partNo = partNo;
        this.multiply = multiply;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getMultiply() {
        return multiply;
    }

    public void setMultiply(String multiply) {
        this.multiply = multiply;
    }

    @Override
    public String toString() {
        return partNo;
    }
}
