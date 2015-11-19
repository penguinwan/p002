/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jt.givi.core;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.jt.givi.api.IMasterSetupManager;
import com.jt.givi.model.Mold;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author superman
 */
public class MasterSetupManager implements IMasterSetupManager {
    private static final Logger logger = LoggerFactory.getLogger(MasterSetupManager.class);
    private String masterFilePath;

    public MasterSetupManager(String masterFilePath) {
        this.masterFilePath = masterFilePath;
    }

    @Override
    public List<Mold> load() throws IOException, NumberFormatException {
        logger.info("Loading master setup...");
        FileReader fileReader = new FileReader(masterFilePath);
        CSVReader reader = new CSVReader(fileReader);
        List<String[]> content = reader.readAll();
        List<Mold> moldList = new ArrayList<Mold>();
        for (String[] strArr : content) {
            int multiply = Integer.valueOf(strArr[1]);
            moldList.add(new Mold(strArr[0], multiply));
        }
        return moldList;
    }

    @Override
    public void save(List<Mold> moldList) throws IOException {
        logger.info("Saving master setup...");
        List<String[]> content = new ArrayList<String[]>();
        for (Mold mold : moldList) {
            String[] line = new String[]{mold.getPartNo(), String.valueOf(mold.getMultiply())};
            content.add(line);
        }

        FileWriter fileWriter = new FileWriter(new File(masterFilePath), false);
        CSVWriter writer = new CSVWriter(fileWriter);
        writer.writeAll(content);
        writer.close();
    }

}
