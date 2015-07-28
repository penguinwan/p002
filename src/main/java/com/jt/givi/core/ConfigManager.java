package com.jt.givi.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by superman on 7/28/2015.
 */
public class ConfigManager {
    public String stateFilePath;
    public String masterFilePath;
    public String logFolderPath;
    public int serialTimeout;

    public ConfigManager() throws IOException, NumberFormatException {
        load();
    }

    private void load() throws IOException, NumberFormatException {
        Properties prop = new Properties();
        InputStream inputStream = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties");

        if (inputStream == null) {
            System.out.println("Failed to load configuration file. [config.properties]");
            return;
        }

        prop.load(inputStream);
        stateFilePath = prop.getProperty("state.file.path");
        masterFilePath = prop.getProperty("master.file.path");
        logFolderPath = prop.getProperty("log.folder.path");
        serialTimeout = Integer.valueOf(prop.getProperty("serial.timeout.milisecond"));
    }
}
