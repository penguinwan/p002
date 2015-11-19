package com.jt.givi.api;

import com.jt.givi.model.Machine;

import java.io.IOException;

/**
 * Created by superman on 11/19/2015.
 */
public interface IStorageManager {
    String REMARK_STATUS_CHANGED = "Status Changed ";
    String REMARK_PART_RESET = "Part Reset ";
    String REMARK_ACTUAL_MODIFIED = "Actual Modified ";

    void writeLog(Machine machine, String remarks) throws IOException, InterruptedException;
}
