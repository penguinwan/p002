/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jt.givi;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;

/**
 *
 * @author superman
 */
public class Launcher {

    private static final int DEBOUNCE_INTERVAL_MILISECOND = 50;

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

        final Frame frame = new Frame();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                frame.setVisible(true);
            }
        });

        final Controller controller = new Controller(frame.getTableModel());
        System.out.println("controller table model " + frame.getTableModel());
        final GpioController gpio = GpioFactory.getInstance();

        final GpioPinDigitalInput pin04 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
        GpioListener pin04Listener = new GpioListener(controller, RaspiPin.GPIO_04);
        pin04.setDebounce(DEBOUNCE_INTERVAL_MILISECOND);
        pin04.addListener(pin04Listener);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        final Runnable fileWriter = new Runnable() {
            public void run() {
                try {
                    System.out.println("start printing file");
                    File file = new File("/media/share/log.csv");
                    FileWriter fileWriter = new FileWriter(file, true);
                    BufferedWriter bw = new BufferedWriter(fileWriter);
                    bw.write(controller.dumpModel());
                    bw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        final ScheduledFuture<?> fileWriterHandle
                = scheduler.scheduleAtFixedRate(fileWriter, 10, 10, TimeUnit.SECONDS);
    }

}
