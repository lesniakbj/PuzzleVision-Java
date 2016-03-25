package com.puzzlevision.com;

import com.puzzlevision.com.exception.ComPortException;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Brendan on 3/23/2016.
 *
 * A wrapper for the COM ports, allows
 * me to add some functionality and meta
 * data to the ports without having to
 * extend the class.
 *
 * Favor composition over inheritance.
 */
public class ComPortWrapper {
    private static final Logger logger = LogManager.getLogger(ComPortWrapper.class);

    private static final Integer DATA_BITS = 8;
    private static final Integer STOP_BITS = 1;
    private static final Integer PARITY = 0;

    private SerialPort comPort;
    private String portName;
    private Integer baudRate;
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

    public ComPortWrapper(String portName, Integer baudRate) {
        this(portName);
        try {
            setBaudRate(this.baudRate);
        } catch(ComPortException e) {
            logger.error("Failed to create ", this);
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
        return comPort.getPortName();
    }

    public boolean isOpen() {
        return comPort.isOpened();
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
        // There is no way to assign a port name,
        // so create a new instance that is assigned
        // to this name.
        try {
            comPort.closePort();
        } catch (SerialPortException e) {
            logger.error("Error closing port on name change.");
        }
        comPort = new SerialPort(portName);
    }

    @Override
    public String toString() {
        return "COM Port: " + portName + ", Is Open: " + isOpen;
    }

    public void setBaudRate(Integer baudRate) throws ComPortException {
        this.baudRate = baudRate;
        try {
            comPort.setParams(baudRate, DATA_BITS, STOP_BITS, PARITY);
        } catch (SerialPortException e) {
            logger.error("Could not change port parameters! [Baud Rate]", e);
            throw new ComPortException("Failed to change COM parameters");
        }
    }

    public void setFlowControl() throws ComPortException {
        try {
            comPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
                    SerialPort.FLOWCONTROL_RTSCTS_OUT);
        } catch (SerialPortException e) {
            logger.error("Could not set flow control to defaults [Flow Control]", e);
            throw new ComPortException("Failed to set Flow Control mode!");
        }
    }

    public void initialize(String port, Integer baud) throws ComPortException {
        connect();
        setBaudRate(baud);
        setFlowControl();
        try {
            addComEventListener();
        } catch (SerialPortException e) {
            logger.error("Error adding event listener to COM port {}, {}", this, e);
        }
    }

    private void addComEventListener() throws SerialPortException {
        comPort.addEventListener(new ComListener(this));
    }
}
