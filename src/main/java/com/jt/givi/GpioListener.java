/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jt.givi;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 *
 * @author superman
 */
public class GpioListener implements GpioPinListenerDigital {

    private Controller controller;
    private Pin pin;
    
    public GpioListener(final Controller controller, final Pin pin) {
        this.controller = controller;
        this.pin = pin;
    }
    
    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        // display pin state on console
        if (event.getState().equals(PinState.HIGH)) {
            System.out.println("GPIO high");
            controller.increaseActual(pin);
        }
    }
    
}
