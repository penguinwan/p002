package com.jt.givi.core

import com.jt.givi.model.Machine
import com.jt.givi.model.Mold
import com.jt.givi.model.Status
import org.junit.Test

import static org.junit.Assert.assertTrue

/**
 * Created by superman on 7/21/2015.
 */
class StorageManagerTest {
    //@Test
    void testGetLogFile() {
        StorageManager mgr = new StorageManager("C:/temp")
        File file = mgr.getLogFile("1")
        assertTrue(file.exists())
    }

    //@Test
    void testWriteLog() throws IOException {
        StorageManager mgr = new StorageManager("C:/temp")
        def machine = new Machine(
                1,
                new Mold("ABC", "2"),
                1000,
                99,
                new Status(Status.State.YELLOW, null)
        )
        mgr.writeLog(machine, "susie")
    }
}
