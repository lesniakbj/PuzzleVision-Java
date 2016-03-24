package com.puzzlevision.model;

import com.puzzlevision.com.ComCommandsType;
import com.puzzlevision.com.ComHandler;
import com.puzzlevision.gui.PuzzleVisionMainGUI;
import com.puzzlevision.resource.ResourceLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Brendan on 3/21/2016.
 */
public class PuzzleVision {
    private static final Logger logger = LogManager.getLogger("PuzzleVision");

    private Properties systemProperties;
    private Properties messageProperties;

    private ComHandler comHandler;

    public PuzzleVision() {
        systemProperties = new Properties();
        messageProperties = new Properties();
    }

    public void setMessageProperties(Properties props) {
        this.messageProperties = props;
    }

    public void setSystemProperties(Properties props) {
        this.systemProperties = props;
    }

    public void setComHandler(ComHandler comHandler) {
        this.comHandler = comHandler;
    }

    public Properties getMessageProperties() {
        return messageProperties;
    }

    public Properties getSystemProperties() {
        return systemProperties;
    }

    public ComHandler getComHandler() {
        return comHandler;
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
            String baud = systemProperties.getProperty("puzzlevision.baud.rate");
            logger.info("Connection request received! Trying to connect with {} and {}", port, baud);

            if(comHandler.isComPortAvailable(port)) {
                logger.info("Attempting to connect to port!");
                didConnect = comHandler.connectToPort(port, baud);
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
