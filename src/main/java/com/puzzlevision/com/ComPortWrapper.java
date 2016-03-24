package com.puzzlevision.com;

import com.puzzlevision.com.exception.ComPortException;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Brendan on 3/23/2016.
 */
public class ComPortWrapper {
    private static final Logger logger = LogManager.getLogger(ComPortWrapper.class);

    private SerialPort comPort;
    private String portName;
    private boolean isOpen;

    private ComPortWrapper() {

    }

    public ComPortWrapper(String portName) {
        this.portName = portName;
        comPort = new SerialPort(portName);
        isOpen = true;

        if(!comPort.isOpened()) {
            isOpen = false;
            logger.info("COM Port not opened! Available for use!");
        }
    }

    public boolean connect() throws ComPortException {
        try {
            comPort.openPort();
        } catch(SerialPortException e) {
            logger.error("Error opening COM Port: " + portName);
            throw new ComPortException("Exception while trying to open port!");
        }

        logger.info("Connected to port " + comPort.getPortName());
        return true;
    }

    public String getPortName() {
        return portName;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public SerialPort getComPort() {
        return comPort;
    }

    public void setComPort(SerialPort comPort) {
        this.comPort = comPort;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    @Override
    public String toString() {
        return "COM Port: " + portName + ", Is Open: " + isOpen;
    }
}
