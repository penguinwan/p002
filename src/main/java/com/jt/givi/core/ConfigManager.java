package com.jt.givi.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by superman on 7/28/2015.
 */
public class ConfigManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);

    public String stateFilePath;
    public String masterFilePath;
    public String logFolderPath;
    public int serialTimeout;

    public ConfigManager() throws IOException, NumberFormatException {
        load();
    }

    private void load() throws IOException, NumberFormatException {
        logger.info("Loading configuration file...");
        Properties prop = new Properties();
        InputStream inputStream = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties");

        if (inputStream == null) {
            logger.error("Failed to load configuration file. [config.properties]");
            return;
        }

        prop.load(inputStream);
        stateFilePath = prop.getProperty("state.file.path");
        masterFilePath = prop.getProperty("master.file.path");
        logFolderPath = prop.getProperty("log.folder.path");
        serialTimeout = Integer.valueOf(prop.getProperty("serial.timeout.milisecond"));

        logger.debug("state file={}", stateFilePath);
        logger.debug("master file={}", masterFilePath);
        logger.debug("log folder={}", logFolderPath);
        logger.debug("serial timeout={}", serialTimeout);
    }
}
