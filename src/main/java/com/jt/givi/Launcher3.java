package com.jt.givi;

import com.jt.givi.gui.Frame;
import com.jt.givi.model.MachineTableModel;
import com.jt.givi.model.MasterSetupTableModel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by superman on 7/24/2015.
 */
public class Launcher3 {

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
                    com.jt.givi.core.Controller controller = new com.jt.givi.core.Controller();
                    MasterSetupTableModel masterSetupTableModel = controller.getMasterSetupTableModel();
                    MachineTableModel machineTableModel = controller.getMachineTableModel();

                    Frame frame = new Frame(controller, masterSetupTableModel, machineTableModel);

                    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
                    UpdateMachineTask machine1 = new UpdateMachineTask(controller, frame, 1);
                    UpdateMachineTask machine2 = new UpdateMachineTask(controller, frame, 2);
                    UpdateMachineTask machine3 = new UpdateMachineTask(controller, frame, 3);
                    UpdateMachineTask machine4 = new UpdateMachineTask(controller, frame, 4);
                    UpdateMachineTask machine5 = new UpdateMachineTask(controller, frame, 5);
                    UpdateMachineTask machine6 = new UpdateMachineTask(controller, frame, 6);
                    UpdateMachineTask machine7 = new UpdateMachineTask(controller, frame, 7);
                    UpdateMachineTask machine8 = new UpdateMachineTask(controller, frame, 8);
                    UpdateMachineTask machine9 = new UpdateMachineTask(controller, frame, 9);
                    UpdateMachineTask machine10 = new UpdateMachineTask(controller, frame, 10);

                    scheduler.scheduleAtFixedRate(machine1, 3, 10, TimeUnit.SECONDS);
                    scheduler.scheduleAtFixedRate(machine2, 12, 10, TimeUnit.SECONDS);
                    scheduler.scheduleAtFixedRate(machine3, 14, 10, TimeUnit.SECONDS);
                    scheduler.scheduleAtFixedRate(machine4, 16, 10, TimeUnit.SECONDS);
                    scheduler.scheduleAtFixedRate(machine5, 18, 10, TimeUnit.SECONDS);
                    scheduler.scheduleAtFixedRate(machine6, 20, 10, TimeUnit.SECONDS);
                    scheduler.scheduleAtFixedRate(machine7, 22, 10, TimeUnit.SECONDS);
                    scheduler.scheduleAtFixedRate(machine8, 24, 10, TimeUnit.SECONDS);
                    scheduler.scheduleAtFixedRate(machine9, 26, 10, TimeUnit.SECONDS);
                    scheduler.scheduleAtFixedRate(machine10, 28, 10, TimeUnit.SECONDS);

                    frame.setVisible(true);

                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
    }

    private static final class UpdateMachineTask implements Runnable {
        private com.jt.givi.core.Controller controller;
        private Frame frame;
        private int machineNo;

        UpdateMachineTask(com.jt.givi.core.Controller controller, Frame frame, int machineNo) {
            this.controller = controller;
            this.frame = frame;
            this.machineNo = machineNo;
        }

        @Override
        public void run() {
            try {
                controller.updateMachine(machineNo);
                frame.updateMainPanel();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
