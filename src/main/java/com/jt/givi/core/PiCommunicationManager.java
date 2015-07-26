package com.jt.givi.core;

import com.jt.givi.model.Status;
import com.jt.givi.model.ValueContainer;
import com.pi4j.io.gpio.*;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataListener;
import com.pi4j.io.serial.SerialFactory;

import java.util.concurrent.TimeoutException;

/**
 * Created by superman on 7/24/2015.
 */
public class PiCommunicationManager {
    private static final String GET_VALUE_FORMAT = "%sGVL";
    private static final String RESET_FORMAT = "%sRST";
    private static final String ADJUST_FORMAT = "%sSET%s";
    private Serial serial;
    private GpioController gpio;
    private GpioPinDigitalOutput pin04;
    private int timeout;
    private boolean disablePi = false;

    public PiCommunicationManager(int timeout) {
        String skipPi = System.getProperty("skipPi");
        if (skipPi != null && skipPi.equalsIgnoreCase("true")) {
            disablePi = true;
        }
        if (!this.disablePi) {
            this.timeout = timeout;
            serial = SerialFactory.createInstance();
            serial.open(Serial.DEFAULT_COM_PORT, 9600);

            gpio = GpioFactory.getInstance();
            pin04 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, PinState.LOW);
        }
    }

    public void reset(int machineNo) {
        if (!disablePi) {
            pin04.high();
            String machineAddress = String.format("%03d", machineNo);
            serial.writeln(String.format(RESET_FORMAT, machineAddress));
            pin04.low();
        }
    }

    public void adjust(int machineNo, int actualValue) {
        if (!disablePi) {
            pin04.high();
            String machineAddress = String.format("%03d", machineNo);
            String value = String.format("%04d", actualValue);
            serial.writeln(String.format(ADJUST_FORMAT, machineAddress, value));
            pin04.low();
        }
    }

    public ValueContainer getValue(int machineNo)
            throws InterruptedException, TimeoutException {
        if (!disablePi) {
            pin04.high();

            Envelope envelope = new Envelope();
            Listener listener = new Listener(envelope);
            serial.addListener(listener);

            String machineAddress = String.format("%03d", machineNo);
            serial.writeln(String.format(GET_VALUE_FORMAT, machineAddress));
            int waitingTime = 0;
            while (!envelope.received) {
                waitingTime += 50;
                Thread.sleep(50);
                if (waitingTime > timeout) {
                    serial.removeListener(listener);
                    throw new TimeoutException();
                }
            }

            serial.removeListener(listener);
            pin04.low();
            ResponseParser parser = new ResponseParser(envelope.data);
            return new ValueContainer(
                    parser.machine(),
                    parser.value(),
                    parser.state());
        } else {
            return new ValueContainer(1, 100, Status.State.GREEN);
        }
    }


    private static class Envelope {
        boolean received = false;
        String data;
    }

    private static class Listener implements SerialDataListener {
        private Envelope envelope;

        public Listener(Envelope envelope) {
            this.envelope = envelope;
        }

        @Override
        public void dataReceived(SerialDataEvent serialDataEvent) {
            System.out.println("Received data: " + serialDataEvent.getData());
            envelope.data = serialDataEvent.getData();
            envelope.received = true;
        }
    }

    private static class ResponseParser {
        private String response;

        ResponseParser(String response) {
            this.response = response;
        }

        int machine() {
            return Integer.valueOf(response.substring(0, 3));
        }

//        String command() {
//            return response.substring(3, 6);
//        }

        int value() {
            return Integer.valueOf(response.substring(6, 10));
        }

        Status.State state() {
            String state = response.substring(10);
            if (state.equalsIgnoreCase("R")) {
                return Status.State.RED;
            } else if (state.equalsIgnoreCase("O")) {
                return Status.State.BLACK;
            } else if (state.equalsIgnoreCase("G")) {
                return Status.State.GREEN;
            } else if (state.equalsIgnoreCase("Y")) {
                return Status.State.YELLOW;
            } else {
                throw new IllegalArgumentException("State: " + state);
            }
        }
    }

}
