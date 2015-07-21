package com.jt.givi.core

import com.jt.givi.model.Machine
import com.jt.givi.model.Mold
import com.jt.givi.model.Status
import groovy.json.JsonBuilder
import org.junit.Test

/**
 * Created by superman on 7/18/2015.
 */
class StateManagerTest {
    //@Test
    void testLoad() {
        URL url = StateManagerTest.classLoader.getResource('state.csv')
        StateManager mgr = new StateManager(url.getPath())
        def machineList = mgr.load();
        JsonBuilder jsonBuilder = new JsonBuilder(machineList)
        println jsonBuilder.toPrettyString()
    }

    //@Test
    void testSave() {
        def filePath = 'C:\\temp\\state.csv';
        def machineList = [
                new Machine(1, new Mold('1', '2'), 100, 99, new Status(Status.State.BLACK, new Date())),
                new Machine(9, new Mold('9', '2'), 100, 99, new Status(Status.State.GREEN, new Date())),
                new Machine(8, new Mold('8', '2'), 100, 99, new Status(Status.State.RED, new Date())),
                new Machine(2, new Mold('2', '2'), 100, 99, new Status(Status.State.YELLOW, new Date())),
                new Machine(4, new Mold('4', '2'), 100, 99, new Status(Status.State.RED, new Date())),
                new Machine(7, new Mold('7', '2'), 100, 99, new Status(Status.State.BLACK, new Date())),
                new Machine(3, new Mold('3', '2'), 100, 99, new Status(Status.State.YELLOW, new Date())),
                new Machine(5, new Mold('5', '2'), 100, 99, new Status(Status.State.BLACK, new Date())),
                new Machine(6, new Mold('6', '2'), 100, 99, new Status(Status.State.YELLOW, new Date())),
                new Machine(0, new Mold('0', '2'), 100, 99, new Status(Status.State.YELLOW, new Date())),
        ]

        StateManager mgr = new StateManager(filePath);
        mgr.save(machineList);
    }
}
