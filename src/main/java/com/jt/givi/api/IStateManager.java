package com.jt.givi.api;

import com.jt.givi.model.Machine;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by superman on 11/19/2015.
 */
public interface IStateManager {
    List<Machine> load() throws IOException, NumberFormatException, ParseException;

    void save(List<Machine> machineList) throws IOException;
}
