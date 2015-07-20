package com.jt.givi.core

import com.jt.givi.model.Mold
import groovy.json.JsonBuilder

/**
 * Created with IntelliJ IDEA.
 * User: superman
 * Date: 7/18/15
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
class MasterSetupManagerTest {
    //@Test
    void testLoadMold() {
        URL url = MasterSetupManagerTest.classLoader.getResource('master.csv')
        MasterSetupManager mgr = new MasterSetupManager(url.getPath())
        def moldList = mgr.load()
        JsonBuilder jsonBuilder = new JsonBuilder(moldList)
        println jsonBuilder.toPrettyString()
    }

    //@Test
    void testSaveMaster() {
        def moldList = [
                new Mold('PartA', '8'),
                new Mold('PartB', '4'),
                new Mold('PartC', '2')]
        URL url = MasterSetupManagerTest.classLoader.getResource('master-output.csv')
        println url.path
        MasterSetupManager mgr = new MasterSetupManager(url.getPath())
        mgr.save(moldList)

    }
}
