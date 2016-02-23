package com.jt.givi.core

import com.jt.givi.model.Mold
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import static org.junit.Assert.assertEquals
import static org.junit.Assert.fail

/**
 * Created with IntelliJ IDEA.
 * User: superman
 * Date: 7/18/15
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
class MasterSetupManagerTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder()

    @Test
    void testLoadMold() {
        URL url = MasterSetupManagerTest.classLoader.getResource('master.csv')
        MasterSetupManager mgr = new MasterSetupManager(url.path)
        def moldList = mgr.load()
        moldList.each {
            if (it.partNo == 'PartA') {
                assertEquals(8, it.multiply)
            } else if (it.partNo == 'PartB') {
                assertEquals(4, it.multiply)
            } else if (it.partNo == 'PartC') {
                assertEquals(2, it.multiply)
            } else {
                fail("Part no not found ${it.partNo}")
            }
        }
    }

    @Test
    void testSaveMaster() {
        def moldList = [
                new Mold('PartA', 8),
                new Mold('PartB', 4),
                new Mold('PartC', 2)]
        File masterSetupFile = folder.newFile('master-output.csv')
        MasterSetupManager mgr = new MasterSetupManager(masterSetupFile.path)
        mgr.save(moldList)

        masterSetupFile.eachLine { line, index ->
            if (index == 1) {
                assertEquals("\"PartA\",\"8\"", line)
            } else if (index == 2) {
                assertEquals("\"PartB\",\"4\"", line)
            } else if (index == 3) {
                assertEquals("\"PartC\",\"2\"", line)
            } else {
                fail("Wrong index ${index}")
            }
        }

    }
}
