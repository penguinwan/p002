/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jt.givi.gui;

import com.jt.givi.model.Mold;

import java.awt.event.ItemEvent;
import java.util.List;

/**
 * @author superman
 */
public class EditPanel extends javax.swing.JPanel {
    private List<Mold> moldList;

    /**
     * Creates new form EditPanel
     */
    public EditPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cmbMachineNo = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        cmbPartNo = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        txtTarget = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtActual = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtMultiply = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 255, 255));
        setPreferredSize(new java.awt.Dimension(600, 385));
        setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Edit Machine");
        jLabel1.setFocusable(false);
        jLabel1.setPreferredSize(new java.awt.Dimension(600, 35));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        add(jLabel1, gridBagConstraints);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 320));
        jPanel1.setLayout(new java.awt.GridLayout(5, 2, 5, 5));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setText("Machine No");
        jLabel6.setFocusable(false);
        jPanel1.add(jLabel6);

        cmbMachineNo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        cmbMachineNo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        cmbMachineNo.setPreferredSize(new java.awt.Dimension(250, 60));
        jPanel1.add(cmbMachineNo);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setText("Part No");
        jLabel8.setFocusable(false);
        jPanel1.add(jLabel8);

        cmbPartNo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        cmbPartNo.setPreferredSize(new java.awt.Dimension(250, 60));
        cmbPartNo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbPartNoItemStateChanged(evt);
            }
        });
        jPanel1.add(cmbPartNo);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("Target");
        jLabel9.setFocusable(false);
        jPanel1.add(jLabel9);

        txtTarget.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtTarget.setPreferredSize(new java.awt.Dimension(250, 60));
        jPanel1.add(txtTarget);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setText("Actual");
        jLabel10.setFocusable(false);
        jPanel1.add(jLabel10);

        txtActual.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtActual.setNextFocusableComponent(cmbMachineNo);
        txtActual.setPreferredSize(new java.awt.Dimension(250, 60));
        jPanel1.add(txtActual);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setText("Multiply");
        jLabel11.setFocusable(false);
        jPanel1.add(jLabel11);

        txtMultiply.setEditable(false);
        txtMultiply.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtMultiply.setEnabled(false);
        txtMultiply.setFocusable(false);
        jPanel1.add(txtMultiply);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(jPanel1, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(255, 255, 51));
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 30));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Tab = Navigate");
        jLabel3.setFocusable(false);
        jPanel2.add(jLabel3);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Up/Down = Select");
        jLabel2.setFocusable(false);
        jPanel2.add(jLabel2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        add(jPanel2, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void cmbPartNoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbPartNoItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            Object item = evt.getItem();
            Mold selectedMold = (Mold) item;
            txtMultiply.setText(selectedMold.getMultiply());
        }
    }//GEN-LAST:event_cmbPartNoItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmbMachineNo;
    private javax.swing.JComboBox cmbPartNo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtActual;
    private javax.swing.JTextField txtMultiply;
    private javax.swing.JTextField txtTarget;
    // End of variables declaration//GEN-END:variables


    public String getMachineNo() {
        return cmbMachineNo.getSelectedItem().toString();
    }

    public String getActual() {
        return txtActual.getText();
    }

    public Mold getSelectedMold() {
        return (Mold) cmbPartNo.getSelectedItem();
    }

    public String getTarget() {
        return txtTarget.getText();
    }

    public void setMoldList(List<Mold> moldList) {
        this.moldList = moldList;
        cmbPartNo.setModel(new javax.swing.DefaultComboBoxModel(moldList.toArray()));
        if (moldList.size() > 0) {
            txtMultiply.setText(moldList.get(0).getMultiply());
        }
    }

    public void initFocus() {
        cmbMachineNo.requestFocus();
    }
}