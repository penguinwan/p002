/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jt.givi.core;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.jt.givi.model.Mold;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author superman
 */
public class MasterSetupManager {
    private String masterFilePath;

    public MasterSetupManager(String masterFilePath) {
        this.masterFilePath = masterFilePath;
    }

    public List<Mold> load() throws IOException {
        FileReader fileReader = new FileReader(masterFilePath);
        CSVReader reader = new CSVReader(fileReader);
        List<String[]> content = reader.readAll();
        List<Mold> moldList = new ArrayList<Mold>();
        for (String[] strArr : content) {
            moldList.add(new Mold(strArr[0], strArr[1]));
        }
        return moldList;
    }

    public void save(List<Mold> moldList) throws IOException {
        List<String[]> content = new ArrayList<String[]>();
        for (Mold mold : moldList) {
            String[] line = new String[]{mold.getPartNo(), mold.getMultiply()};
            content.add(line);
        }

        FileWriter fileWriter = new FileWriter(new File(masterFilePath), false);
        CSVWriter writer = new CSVWriter(fileWriter);
        writer.writeAll(content);
        writer.close();
    }

}