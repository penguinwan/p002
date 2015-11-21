package com.jt.givi;

import com.jt.givi.core.*;
import com.jt.givi.gui.Frame;
import com.jt.givi.model.MachineTableModel;
import com.jt.givi.model.MasterSetupTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by superman on 7/24/2015.
 */
public class Launcher3 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher3.class);

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ConfigManager configManager = new ConfigManager();
                    MasterSetupManager masterSetupManager = new MasterSetupManager(configManager.masterFilePath);
                    StateManager stateManager = new StateManager(configManager.stateFilePath);
                    StorageManager storageManager = new StorageManager(configManager.logFolderPath);
                    PiCommunicationManager piCommunicationManager = new PiCommunicationManager(configManager.serialTimeout, configManager.sendDelay, configManager.updateMachineRetry);

                    LOGGER.info("Starting...");
                    com.jt.givi.core.Controller controller = new com.jt.givi.core.Controller(masterSetupManager, stateManager, storageManager, piCommunicationManager);
                    MasterSetupTableModel masterSetupTableModel = controller.getMasterSetupTableModel();
                    MachineTableModel machineTableModel = controller.getMachineTableModel();

                    Frame frame = new Frame(controller, masterSetupTableModel, machineTableModel);

                    LOGGER.info("Initializing scheduler...");
                    ScheduledExecutorService getValueScheduler = Executors.newScheduledThreadPool(1);
                    UpdateMachineTask machine1 = new UpdateMachineTask(controller, 1);
                    UpdateMachineTask machine2 = new UpdateMachineTask(controller, 2);
                    UpdateMachineTask machine3 = new UpdateMachineTask(controller, 3);
                    UpdateMachineTask machine4 = new UpdateMachineTask(controller, 4);
                    UpdateMachineTask machine5 = new UpdateMachineTask(controller, 5);
                    UpdateMachineTask machine6 = new UpdateMachineTask(controller, 6);
                    UpdateMachineTask machine7 = new UpdateMachineTask(controller, 7);
                    UpdateMachineTask machine8 = new UpdateMachineTask(controller, 8);
                    UpdateMachineTask machine9 = new UpdateMachineTask(controller, 9);
                    UpdateMachineTask machine10 = new UpdateMachineTask(controller, 10);

                    int initialDelay1 = configManager.updateMachineInitialDelay + (0 * configManager.updateMachineInitialDelayDifference);
                    getValueScheduler.scheduleAtFixedRate(machine1, initialDelay1, configManager.updateMachineInterval, TimeUnit.MILLISECONDS);
                    int initialDelay2 = configManager.updateMachineInitialDelay + (1 * configManager.updateMachineInitialDelayDifference);
                    getValueScheduler.scheduleAtFixedRate(machine2, initialDelay2, configManager.updateMachineInterval, TimeUnit.MILLISECONDS);
                    int initialDelay3 = configManager.updateMachineInitialDelay + (2 * configManager.updateMachineInitialDelayDifference);
                    getValueScheduler.scheduleAtFixedRate(machine3, initialDelay3, configManager.updateMachineInterval, TimeUnit.MILLISECONDS);
                    int initialDelay4 = configManager.updateMachineInitialDelay + (3 * configManager.updateMachineInitialDelayDifference);
                    getValueScheduler.scheduleAtFixedRate(machine4, initialDelay4, configManager.updateMachineInterval, TimeUnit.MILLISECONDS);
                    int initialDelay5 = configManager.updateMachineInitialDelay + (4 * configManager.updateMachineInitialDelayDifference);
                    getValueScheduler.scheduleAtFixedRate(machine5, initialDelay5, configManager.updateMachineInterval, TimeUnit.MILLISECONDS);
                    int initialDelay6 = configManager.updateMachineInitialDelay + (5 * configManager.updateMachineInitialDelayDifference);
                    getValueScheduler.scheduleAtFixedRate(machine6, initialDelay6, configManager.updateMachineInterval, TimeUnit.MILLISECONDS);
                    int initialDelay7 = configManager.updateMachineInitialDelay + (6 * configManager.updateMachineInitialDelayDifference);
                    getValueScheduler.scheduleAtFixedRate(machine7, initialDelay7, configManager.updateMachineInterval, TimeUnit.MILLISECONDS);
                    int initialDelay8 = configManager.updateMachineInitialDelay + (7 * configManager.updateMachineInitialDelayDifference);
                    getValueScheduler.scheduleAtFixedRate(machine8, initialDelay8, configManager.updateMachineInterval, TimeUnit.MILLISECONDS);
                    int initialDelay9 = configManager.updateMachineInitialDelay + (8 * configManager.updateMachineInitialDelayDifference);
                    getValueScheduler.scheduleAtFixedRate(machine9, initialDelay9, configManager.updateMachineInterval, TimeUnit.MILLISECONDS);
                    int initialDelay10 = configManager.updateMachineInitialDelay + (9 * configManager.updateMachineInitialDelayDifference);
                    getValueScheduler.scheduleAtFixedRate(machine10, initialDelay10, configManager.updateMachineInterval, TimeUnit.MILLISECONDS);

                    ScheduledExecutorService logMachineScheduler = Executors.newScheduledThreadPool(1);
                    LogMachineTask logMachine = new LogMachineTask(controller);
                    logMachineScheduler.scheduleAtFixedRate(logMachine, 0, configManager.storageInterval, TimeUnit.MINUTES);

                    ScheduledExecutorService saveMachineStateScheduler = Executors.newScheduledThreadPool(1);
                    SaveMachineStateTask saveMachineStateTask = new SaveMachineStateTask(controller);
                    saveMachineStateScheduler.scheduleAtFixedRate(saveMachineStateTask, 0, configManager.saveMachineStateInterval, TimeUnit.SECONDS);

                    frame.setVisible(true);

                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
    }

    private static final class UpdateMachineTask implements Runnable {
        private com.jt.givi.core.Controller controller;
        private int machineNo;

        UpdateMachineTask(com.jt.givi.core.Controller controller, int machineNo) {
            this.controller = controller;
            this.machineNo = machineNo;
        }

        @Override
        public void run() {
            try {
                controller.updateMachine(machineNo);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class LogMachineTask implements Runnable {
        Controller controller;

        LogMachineTask(Controller controller) {
            this.controller = controller;
        }

        public void run() {
            try {
                controller.logMachine();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class SaveMachineStateTask implements Runnable {
        Controller controller;

        SaveMachineStateTask(Controller controller) {
            this.controller = controller;
        }

        public void run() {
            try {
                controller.saveMachineState();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
