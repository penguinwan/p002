package com.jt.givi.core;

import au.com.bytecode.opencsv.CSVWriter;
import com.jt.givi.model.Machine;
import com.jt.givi.util.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by superman on 7/21/2015.
 */
public class StorageManager {
    public static final String REMARK_STATUS_CHANGED = "Status Changed ";
    public static final String REMARK_PART_RESET = "Part Reset ";
    public static final String REMARK_ACTUAL_MODIFIED = "Actual Modified ";

    private static final String FILE_NAME_FORMAT = "%s-machine-%s.log";
    private static final String[] LOG_FILE_HEADER = new String[]{
            LogHeader.DATE.getName(),
            LogHeader.TIME.getName(),
            LogHeader.PART_NO.getName(),
            LogHeader.TARGET.getName(),
            LogHeader.ACTUAL.getName(),
            LogHeader.MULTIPLIER.getName(),
            LogHeader.STATUS.getName(),
            LogHeader.REMARK.getName()};

    private enum LogHeader {
        DATE("Date", 0),
        TIME("Time", 1),
        PART_NO("Part Number", 2),
        TARGET("Target", 3),
        ACTUAL("Actual", 4),
        MULTIPLIER("Multiplier", 5),
        STATUS("Status", 6),
        REMARK("Remark", 7);
        private String name;
        private int index;

        LogHeader(String name, int index) {
            this.name = name;
            this.index = index;
        }

        public String getName() {
            return this.name;
        }

        public int getIndex() {
            return this.index;
        }
    }

    private String logFileFolder;
    private BlockingQueue<Message> queue;

    public StorageManager(String logFileFolder) {
        this.logFileFolder = logFileFolder;
        this.queue = new LinkedBlockingQueue<>();
        LogWriter writer = new LogWriter(this, queue);
        new Thread(writer).start();
    }

    protected File getLogFile(String machineNo) throws IOException {
        Date today = Calendar.getInstance().getTime();
        String fileName = String.format(FILE_NAME_FORMAT,
                Util.dateToString(today),
                machineNo);
        File logFile = new File(logFileFolder, fileName);
        if (!logFile.exists()) {
            logFile.createNewFile();

            // writer CSV header
            List<String[]> header = Collections.singletonList(LOG_FILE_HEADER);
            CSVWriter csvWriter = new CSVWriter(new FileWriter(logFile));
            csvWriter.writeAll(header);
            csvWriter.close();
        }

        return logFile;
    }

    public void writeLog(Machine machine, String remarks) throws IOException, InterruptedException {
        Message message = new Message();
        message.machine = machine;
        message.remark = remarks;
        queue.put(message);
    }

    void logToFile(Machine machine, String remarks) throws IOException {
        Date now = Calendar.getInstance().getTime();
        String[] content = new String[LOG_FILE_HEADER.length];
        content[LogHeader.DATE.getIndex()] = Util.dateToString(now);
        content[LogHeader.TIME.getIndex()] = Util.timeToString(now);
        content[LogHeader.PART_NO.getIndex()] = machine.getMold().getPartNo();
        content[LogHeader.TARGET.getIndex()] = String.valueOf(machine.getTarget());
        content[LogHeader.ACTUAL.getIndex()] = String.valueOf(machine.getActual());
        content[LogHeader.MULTIPLIER.getIndex()] = String.valueOf(machine.getMold().getMultiply());
        content[LogHeader.STATUS.getIndex()] = machine.getStatus().getState().getName();
        content[LogHeader.REMARK.getIndex()] = remarks == null ? "" : remarks;

        List<String[]> contentList = Collections.singletonList(content);

        File logFile = getLogFile(String.valueOf(machine.getMachineNo()));
        CSVWriter csvWriter = new CSVWriter(new FileWriter(logFile, true));
        csvWriter.writeAll(contentList);
        csvWriter.close();
    }

    private static class Message {
        Machine machine;
        String remark;
    }

    private static class LogWriter implements Runnable {
        private BlockingQueue<Message> inputQueue;
        private StorageManager storageManager;

        LogWriter(StorageManager storageManager, BlockingQueue<Message> inputQueue) {
            this.storageManager = storageManager;
            this.inputQueue = inputQueue;
        }

        public void run() {
            try {
                while (true) {
                    Message message = inputQueue.take();
                    Machine machine = message.machine;
                    String remarks = message.remark;

                    storageManager.logToFile(machine, remarks);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
