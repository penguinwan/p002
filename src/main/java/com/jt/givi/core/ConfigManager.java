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
    public int sendDelay;
    public int storageInterval;
    public int updateMachineInitialDelay;
    public int updateMachineInitialDelayDifference;
    public int updateMachineInterval;
    public int updateMachineRetry;
    public int saveMachineStateInterval;

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
        sendDelay = Integer.valueOf(prop.getProperty("send.delay.milisecond"));
        storageInterval = Integer.valueOf(prop.getProperty("storage.interval.minute"));
        updateMachineInitialDelay = Integer.valueOf(prop.getProperty("update.initial.delay.milisecond"));
        updateMachineInitialDelayDifference = Integer.valueOf(prop.getProperty("update.initial.delay.difference.milisecond"));
        updateMachineInterval = Integer.valueOf(prop.getProperty("update.interval.milisecond"));
        updateMachineRetry = Integer.valueOf(prop.getProperty("update.retry"));
        saveMachineStateInterval = Integer.valueOf(prop.getProperty("save.machine.state.interval.second"));

        logger.debug("state file={}", stateFilePath);
        logger.debug("master file={}", masterFilePath);
        logger.debug("log folder={}", logFolderPath);
        logger.debug("serial timeout={}", serialTimeout);
        logger.debug("send delay={}", sendDelay);
        logger.debug("storage interval={}", storageInterval);
        logger.debug("update machine initial delay={}", updateMachineInitialDelay);
        logger.debug("update machine initial delay difference={}", updateMachineInitialDelayDifference);
        logger.debug("update interval={}", updateMachineInterval);
        logger.debug("update retry={}", updateMachineRetry);
        logger.debug("save machine state interval={}", saveMachineStateInterval);
    }
}

