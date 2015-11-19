package com.jt.givi.api;

import com.jt.givi.model.ValueContainer;

import java.util.concurrent.TimeoutException;

/**
 * Created by superman on 11/19/2015.
 */
public interface IPiCommunicationManager {
    void reset(int machineNo) throws InterruptedException;

    void adjust(int machineNo, int actualValue) throws InterruptedException;

    ValueContainer getValue(int machineNo)
            throws InterruptedException, TimeoutException;
}
