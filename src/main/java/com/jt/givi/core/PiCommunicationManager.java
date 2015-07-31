package com.jt.givi.core;

import com.jt.givi.model.Status;
import com.jt.givi.model.ValueContainer;
import com.pi4j.io.gpio.*;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataListener;
import com.pi4j.io.serial.SerialFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by superman on 7/24/2015.
 */
public class PiCommunicationManager {
    private static final Logger logger = LoggerFactory.getLogger(PiCommunicationManager.class);
    private static final String GET_VALUE_FORMAT = "%sGVL";
    private static final String RESET_FORMAT = "%sRST";
    private static final String ADJUST_FORMAT = "%sSET%s";
    private Serial serial;
    private GpioController gpio;
    private GpioPinDigitalOutput pin04;
    private int timeout;
    private int sendDelay;
    private boolean disablePi = false;
    private BlockingQueue<Envelope> dataQueue;

    public PiCommunicationManager(int timeout, int sendDelay) {
        String skipPi = System.getProperty("skipPi");
        if (skipPi != null && skipPi.equalsIgnoreCase("true")) {
            disablePi = true;
        }
        if (!this.disablePi) {
            this.timeout = timeout;
            this.sendDelay = sendDelay;
            this.dataQueue = new LinkedBlockingQueue<>();

            Listener dataListener = new Listener(this.dataQueue);
            serial = SerialFactory.createInstance();
            serial.addListener(dataListener);

            logger.info("Opening serial default port={} baudrate={}", Serial.DEFAULT_COM_PORT, String.valueOf(9600));
            serial.open(Serial.DEFAULT_COM_PORT, 9600);

            gpio = GpioFactory.getInstance();
            logger.info("Provisioning GPIO pin 04...");
            pin04 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, PinState.LOW);
        }
    }

    public void reset(int machineNo) throws InterruptedException {
        logger.info("Reseting machine...");
        if (!disablePi) {
            logger.debug("Setting GPIO pin 04 to high...");
            pin04.high();
            String machineAddress = String.format("%03d", machineNo);
            logger.debug("Serial writing [{}]", String.format(RESET_FORMAT, machineAddress));
            serial.writeln(String.format(RESET_FORMAT, machineAddress));
            Thread.sleep(sendDelay);
            logger.debug("Setting GPIO pin 04 to low...");
            pin04.low();
        }
    }

    public void adjust(int machineNo, int actualValue) throws InterruptedException {
        logger.info("Adjusting machine...");
        if (!disablePi) {
            logger.debug("Setting GPIO pin 04 to high...");
            pin04.high();
            String machineAddress = String.format("%03d", machineNo);
            String value = String.format("%04d", actualValue);
            logger.debug("Serial writing [{}]", String.format(ADJUST_FORMAT, machineAddress, value));
            serial.writeln(String.format(ADJUST_FORMAT, machineAddress, value));
            Thread.sleep(sendDelay);
            logger.debug("Setting GPIO pin 04 to low...");
            pin04.low();
        }
    }

    public ValueContainer getValue(int machineNo)
            throws InterruptedException, TimeoutException {
        logger.info("Requesting machine value...");
        if (!disablePi) {
            logger.debug("Setting GPIO pin 04 to high...");
            pin04.high();

            String machineAddress = String.format("%03d", machineNo);
            logger.debug("Serial writing [{}]", String.format(GET_VALUE_FORMAT, machineAddress));
            serial.writeln(String.format(GET_VALUE_FORMAT, machineAddress));
            Thread.sleep(sendDelay);
            logger.debug("Clearing data queue...");
            dataQueue.clear();
            logger.debug("Setting GPIO pin 04 to low...");
            pin04.low();

            Envelope envelope = dataQueue.poll(timeout, TimeUnit.MILLISECONDS);
            if(envelope != null) {
                logger.info("Processing data... {}", envelope.data);
                ResponseParser parser = new ResponseParser(envelope.data);
                return new ValueContainer(
                        parser.machine(),
                        parser.value(),
                        parser.state());
            } else {
                return ValueContainer.EMPTY;
            }
        } else {
            return new ValueContainer(1, 100, Status.State.GREEN);
        }
    }


    private static class Envelope {
        boolean received = false;
        String data;
    }

    private static class Listener implements SerialDataListener {
        private BlockingQueue dataQueue;
        private String current = "";
        private boolean isBegin = false;

        public Listener(BlockingQueue dataQueue) {
            this.dataQueue = dataQueue;
        }

        @Override
        public void dataReceived(SerialDataEvent serialDataEvent) {
            logger.debug("Received data={}", serialDataEvent.getData());
            try {
                String received = serialDataEvent.getData();
                if (received.indexOf("S") != -1) {
                    logger.debug("Data begin...");
                    isBegin = true;
                }

                if (isBegin) {
                    current += received;
                    if (received.indexOf("E") != -1) {
                        Envelope envelope = new Envelope();
                        envelope.data = current.substring(current.indexOf("S"), (current.indexOf("E") + 1));
                        envelope.received = true;
                        dataQueue.put(envelope);

                        current = "";
                        isBegin = false;
                        logger.debug("End receiving, put to queue.");
                    } else {
                        logger.debug("Appending received data, so far {}", current);
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static class ResponseParser {
        private String response;

        ResponseParser(String response) {
            this.response = response;
        }

        int machine() {
            String strmachine = response.substring(1, 4);
            logger.debug("machine={}", strmachine);
            return Integer.valueOf(response.substring(1, 4));
        }

//        String command() {
//            return response.substring(3, 6);
//        }

        int value() {
            //String strValue = response.substring(6, 10);
            //logger.debug("value={}", strValue);
            return Integer.valueOf(response.substring(7, 11));
        }

        Status.State state() {
            String state = response.substring(11, 12);
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
