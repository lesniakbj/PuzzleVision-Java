package com.puzzlevision.com;

import com.puzzlevision.model.PuzzleVision;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Brendan on 3/24/2016.
 *
 * Listener to handle RX-TX events that
 * happen when we communicate with a Serial Port.
 */
public class ComListener implements SerialPortEventListener {
    private static final Logger logger = LogManager.getLogger(ComListener.class);

    private static final int MESSAGE_SIZE = Integer.parseInt(PuzzleVision.getSystemProperty("puzzlevision.com.length"));

    private ComPortWrapper comPort;

    private int eventCount;
    private String dataReceived;

    public ComListener(ComPortWrapper comPort) {
        this.comPort = comPort;
        eventCount = 0;
        dataReceived = "";
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        eventCount++;

        int evtNum = serialPortEvent.getEventValue();
        if(serialPortEvent.isRXCHAR() && evtNum > 0) {
            try {
                dataReceived = comPort.getComPort().readString(MESSAGE_SIZE);
                logger.info("Data Received: {}", dataReceived);
            } catch(SerialPortException e) {
                logger.error("Error receiving data", e);
            }
        }
    }
}
