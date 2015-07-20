/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jt.givi;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

/**
 *
 * @author superman
 */
public class Panel extends javax.swing.JPanel {
    private PanelModel model = new PanelModel();

    /**
     * Creates new form Panel
     */
    public Panel() {
        initComponents();
        
        setFocusable(true);

        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0), "F8");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0), "F9");
        am.put("F8", new F8Action());
        am.put("F9", new F9Action());
        
        setValues();
        disableTextField();
    }
    
    public void setValues() {
        txtActual.setText(String.valueOf(model.getActualValue()));
        lblActual.setText(String.valueOf(model.getActualValue()));
        txtTarget.setText(String.valueOf(model.getTargetValue()));
        lblTarget.setText(String.valueOf(model.getTargetValue()));
        txtComponentName.setText(model.getComponentName());
        lblComponentName.setText(model.getComponentName());
    }

    public void disableTextField() {
        txtActual.setVisible(false);
        lblActual.setVisible(true);
        
        txtTarget.setVisible(false);
        lblTarget.setVisible(true);
        
        txtComponentName.setVisible(false);
        lblComponentName.setVisible(true);
    }
    
    public void enableTextField() {
        txtActual.setVisible(true);
        lblActual.setVisible(false);
        
        txtTarget.setVisible(true);
        lblTarget.setVisible(false);
        
        txtComponentName.setVisible(true);
        lblComponentName.setVisible(false);
    }

    public void saveValues() {
        try {
            int actual = Integer.valueOf(txtActual.getText());
            model.setActualValue(actual);
        } catch (NumberFormatException ex) {

        }
        try {
            int target = Integer.valueOf(txtTarget.getText());
            model.setTargetValue(target);
        } catch (NumberFormatException ex) {

        }
        
        model.setComponentName(txtComponentName.getText());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtTarget = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtActual = new javax.swing.JTextField();
        legendPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblTarget = new javax.swing.JLabel();
        lblActual = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtComponentName = new javax.swing.JTextField();
        lblComponentName = new javax.swing.JLabel();

        jLabel1.setText("Target");
        jLabel1.setFocusable(false);

        jLabel2.setText("Actual");
        jLabel2.setFocusable(false);

        legendPanel.setFocusable(false);

        jLabel3.setText("Press F8 to Edit");

        jLabel4.setText("Press Tab to Navigate");

        jLabel5.setText("Press F9 to Save");

        javax.swing.GroupLayout legendPanelLayout = new javax.swing.GroupLayout(legendPanel);
        legendPanel.setLayout(legendPanelLayout);
        legendPanelLayout.setHorizontalGroup(
            legendPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(legendPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(legendPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        legendPanelLayout.setVerticalGroup(
            legendPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(legendPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblTarget.setAlignmentX(0.5F);
        lblTarget.setFocusable(false);

        lblActual.setToolTipText("");
        lblActual.setAlignmentX(0.5F);
        lblActual.setFocusable(false);

        jLabel6.setText("Component Name");

        lblComponentName.setAlignmentX(0.5F);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(legendPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblComponentName)
                    .addComponent(lblActual)
                    .addComponent(lblTarget))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtComponentName, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtActual, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTarget, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTarget, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(lblTarget)))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(lblActual))
                    .addComponent(txtActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtComponentName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblComponentName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                .addComponent(legendPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblActual;
    private javax.swing.JLabel lblComponentName;
    private javax.swing.JLabel lblTarget;
    private javax.swing.JPanel legendPanel;
    private javax.swing.JTextField txtActual;
    private javax.swing.JTextField txtComponentName;
    private javax.swing.JTextField txtTarget;
    // End of variables declaration//GEN-END:variables


    private final class F8Action extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            setValues();
            enableTextField();
            txtTarget.requestFocus();
        }

    }
    
    private final class F9Action extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            saveValues();
            setValues();
            disableTextField();
        }

    }
}
