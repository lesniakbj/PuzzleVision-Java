package com.puzzlevision.model;

import com.puzzlevision.com.ComCommandsType;
import com.puzzlevision.com.ComHandler;
import com.puzzlevision.resource.ResourceLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Brendan on 3/21/2016.
 *
 * This is the main PuzzleVision class, which is
 * designed to coordinate the running of an analysis,
 * and the communication of a a device that is running
 * the physical hardware through Serial Comms. This class
 * will have a ComHandler and ImageAnalyzer so that it can
 * perform each of the tasks required.
 */
public class PuzzleVision {
    private static final Logger logger = LogManager.getLogger("PuzzleVision");

    // Only one set of properties for the entire puzzle vision.
    // There should only ever be one puzzle vision instance.
    private static Properties systemProperties;
    private static Properties messageProperties;

    private ComHandler comHandler;

    public PuzzleVision() {
        systemProperties = new Properties();
        messageProperties = new Properties();
    }

    public static String getSystemProperty(String prop) {
        return getProperty(prop, systemProperties);
    }

    public static String getMessageProperty(String prop) {
        return getProperty(prop, systemProperties);
    }

    private static String getProperty(String prop, Properties props) {
        return props.getProperty(prop);
    }

    public static void setSystemProperty(String s, String comPortName) {
        setProperty(s, comPortName, systemProperties);
    }

    public static void setMessageProperty(String s, String comPortName) {
        setProperty(s, comPortName, systemProperties);
    }

    private static void setProperty(String prop, String val, Properties props) {
        props.setProperty(prop, val);
    }

    public Properties getMessageProperties() {
        return messageProperties;
    }

    public void setMessageProperties(Properties props) {
        messageProperties = props;
    }

    public Properties getSystemProperties() {
        return systemProperties;
    }

    public void setSystemProperties(Properties props) {
        systemProperties = props;
    }

    public ComHandler getComHandler() {
        return comHandler;
    }

    public void setComHandler(ComHandler comHandler) {
        this.comHandler = comHandler;
    }

    public boolean handleAction(String action) {
        if(!hasComHandler()) {
            logger.error("Can't handle action! No COM Handler!");
            return false;
        }

        if(action.equalsIgnoreCase(ComCommandsType.SET.toString())) {
            logger.info("Setting COM properties!");
            return true;
        }

        if(action.equalsIgnoreCase(ComCommandsType.CONNECT.toString())) {
            boolean didConnect = false;
            String port = systemProperties.getProperty("puzzlevision.com.port");
            Integer baud = Integer.parseInt(systemProperties.getProperty("puzzlevision.com.baud"));
            logger.info("Connection request received! Trying to connect with {} and {}", port, baud);

            if(comHandler.isComPortAvailable(port)) {
                logger.info("Attempting to connect to port {} with baud rate of {}", port, baud);
                didConnect = comHandler.connectToPort(port, baud);
                logger.info("State of com ports:\n" + comHandler.getAvailablePorts().toString());
            }

            return didConnect;
        }

        return false;
    }

    public void loadSystemProperties(String path) {
        logger.info("Loading system properties from '" + path + "'");
        loadProperties(systemProperties, path);
    }

    public void loadMessageProperties(String path) {
        logger.info("Loading message properties from '" + path + "'");
        loadProperties(messageProperties, path);
    }

    private void loadProperties(Properties props, String path) {
        try (InputStream fileStream = ResourceLoader.loadProperties(path)) {
            props.load(fileStream);
        } catch (IOException e) {
            logger.error("Invalid properties file!", e);
        }
    }

    public boolean hasComHandler() {
        return comHandler != null;
    }
}
