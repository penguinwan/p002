package com.jt.givi;

import com.jt.givi.core.ConfigManager;
import com.jt.givi.core.Controller;
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

                    LOGGER.info("Starting...");
                    com.jt.givi.core.Controller controller = new com.jt.givi.core.Controller(configManager);
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

                    getValueScheduler.scheduleAtFixedRate(machine1, 10, configManager.updateMachineInterval, TimeUnit.SECONDS);
                    getValueScheduler.scheduleAtFixedRate(machine2, 12, configManager.updateMachineInterval, TimeUnit.SECONDS);
                    getValueScheduler.scheduleAtFixedRate(machine3, 14, configManager.updateMachineInterval, TimeUnit.SECONDS);
                    getValueScheduler.scheduleAtFixedRate(machine4, 16, configManager.updateMachineInterval, TimeUnit.SECONDS);
                    getValueScheduler.scheduleAtFixedRate(machine5, 18, configManager.updateMachineInterval, TimeUnit.SECONDS);
                    getValueScheduler.scheduleAtFixedRate(machine6, 20, configManager.updateMachineInterval, TimeUnit.SECONDS);
                    getValueScheduler.scheduleAtFixedRate(machine7, 22, configManager.updateMachineInterval, TimeUnit.SECONDS);
                    getValueScheduler.scheduleAtFixedRate(machine8, 24, configManager.updateMachineInterval, TimeUnit.SECONDS);
                    getValueScheduler.scheduleAtFixedRate(machine9, 26, configManager.updateMachineInterval, TimeUnit.SECONDS);
                    getValueScheduler.scheduleAtFixedRate(machine10, 28, configManager.updateMachineInterval, TimeUnit.SECONDS);

                    ScheduledExecutorService logMachineScheduler = Executors.newScheduledThreadPool(1);
                    LogMachineTask logMachine = new LogMachineTask(controller);
                    logMachineScheduler.scheduleAtFixedRate(logMachine, 0, configManager.storageInterval, TimeUnit.MINUTES);

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
}
