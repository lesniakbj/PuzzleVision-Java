package com.puzzlevision.com;

import com.puzzlevision.com.exception.ComPortException;
import jssc.SerialPortList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Brendan on 3/23/2016.
 *
 * The Com handler handles all the tasks of
 * gathering, iterating, and returning Com Ports.
 * It acts as a container and Manager.
 */
public class ComHandler {
    private static final Logger logger = LogManager.getLogger(ComHandler.class);

    private Map<String, ComPortWrapper> availablePorts;

    public ComHandler() {
        availablePorts = detectComPorts();
    }

    public Map<String, ComPortWrapper> getAvailablePorts() {
        return availablePorts;
    }

    public void setAvailablePorts(Map<String, ComPortWrapper> availablePorts) {
        this.availablePorts = availablePorts;
    }

    public boolean connectToPort(String port, Integer baud) {
        ComPortWrapper comPort = availablePorts.get(port);
        try {
            comPort.initialize(port, baud);
        } catch (ComPortException e) {
            logger.error("COM Port Exception on Initialization!", e);
            return false;
        }

        return true;
    }

    public boolean isComPortAvailable(String comPort) {
        return availablePorts.containsKey(comPort);
    }

    private Map<String, ComPortWrapper> detectComPorts() {
        String[] ports = SerialPortList.getPortNames();
        Map<String, ComPortWrapper> portList = new HashMap<>();

        for(String port : ports) {
            portList.put(port, new ComPortWrapper(port));
        }

        logger.info("Detected the following Serial Ports: \n{}", portList);
        return portList;
    }
}
