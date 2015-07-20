/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jt.givi.gui;

import com.jt.givi.core.Controller;
import com.jt.givi.model.MachineTableModel;
import com.jt.givi.model.MasterSetupTableModel;
import com.jt.givi.model.Mold;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;

import static javax.swing.JComponent.*;

import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * @author superman
 */
public class Frame extends javax.swing.JFrame {

    private JPanel mainPanel;
    private EditPanel editPanel;
    private MasterSetupPanel2 masterSetupPanel;
    private Controller controller;

    /**
     * Creates new form Frame
     */
    public Frame(Controller controller,
                 MasterSetupTableModel masterSetupTableModel,
                 MachineTableModel machineTableModel) {
        initComponents();
        this.controller = controller;

        setVisible(true);
        setFocusable(true);

        mainPanel = new MainPanel(machineTableModel);
        mainPanel.setSize(getSize());
        mainPanel.setFocusable(false);
        getLayeredPane().add(mainPanel, new Integer(1));
        mainPanel.setVisible(true);

        editPanel = new EditPanel();
        editPanel.setSize(editPanel.getPreferredSize());
        editPanel.setFocusable(false);
        getLayeredPane().add(editPanel, new Integer(2));
        editPanel.setVisible(false);

        masterSetupPanel = new MasterSetupPanel2(masterSetupTableModel);
        masterSetupPanel.setSize(masterSetupPanel.getPreferredSize());
        masterSetupPanel.setFocusable(false);
        getLayeredPane().add(masterSetupPanel, new Integer(3));
        masterSetupPanel.setVisible(false);

        setupInputMap();
    }

    private void setupInputMap() {
        System.out.println("setting input map...");
        JPanel contentPane = (JPanel) getContentPane();
        InputMap im = contentPane.getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = contentPane.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "F2");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0), "F7");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0), "F9");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ESC");
        am.put("F2", new F2Action(this));
        am.put("F7", new F7Action(this));
        am.put("F9", new F9Action(this));
        am.put("ESC", new EscAction(this));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1360, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 760, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
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
                    Controller controller = new Controller();
                    MasterSetupTableModel masterSetupTableModel = controller.getMasterSetupTableModel();
                    MachineTableModel machineTableModel = controller.getMachineTableModel();

                    new Frame(controller, masterSetupTableModel, machineTableModel).setVisible(true);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private class F2Action extends AbstractAction {

        private Frame frame;

        public F2Action(Frame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("F2 pressed...");
            frame.masterSetupPanel.setVisible(false);

            EditPanel editPanel = frame.editPanel;
            if (!editPanel.isVisible()) {
                try {
                    editPanel.setMoldList(controller.getMoldList());
                    editPanel.setVisible(true);
                    editPanel.initFocus();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private class F7Action extends AbstractAction {

        private Frame frame;

        public F7Action(Frame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.editPanel.setVisible(false);

            MasterSetupPanel2 panel = frame.masterSetupPanel;
            if (!panel.isVisible()) {
                panel.setVisible(true);
                panel.initFocus();
            }
        }
    }

    private class F9Action extends AbstractAction {

        private Frame frame;

        public F9Action(Frame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (frame.editPanel.isVisible()) {
                try {
                    Mold selectedMold = frame.editPanel.getSelectedMold();
                    int machineNo = Integer.valueOf(frame.editPanel.getMachineNo());
                    int target = Integer.valueOf(frame.editPanel.getTarget());
                    int actual = Integer.valueOf(frame.editPanel.getActual());
                    controller.saveMachine(machineNo, selectedMold, target, actual);

                    frame.mainPanel.updateUI();
                    frame.editPanel.setVisible(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else if (frame.masterSetupPanel.isVisible()) {
                try {
                    controller.saveMasterSetup();
                    frame.masterSetupPanel.setVisible(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private class EscAction extends AbstractAction {

        private Frame frame;

        public EscAction(Frame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (frame.editPanel.isVisible()) {
                frame.editPanel.setVisible(false);
            } else if (frame.masterSetupPanel.isVisible()) {
                frame.masterSetupPanel.setVisible(false);
            }
        }
    }
}