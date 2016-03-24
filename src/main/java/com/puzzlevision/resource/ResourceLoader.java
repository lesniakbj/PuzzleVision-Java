package com.puzzlevision.resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.lang.ClassLoader;

/**
 * Created by Brendan on 3/20/2016.
 *
 * This static class is responsible for
 * loading resource in the project. It cannot
 * be instantiated, and should just load the
 * requested resource for the project.
 */
public class ResourceLoader {
    private static final Logger logger = LogManager.getLogger("ResourceLoader");
    private static final ClassLoader resourceLoader = ResourceLoader.class.getClassLoader();

    private static InputStream loadResourceFile(String path) throws NullPointerException {
        logger.info("Attempting to load resource from file");
        InputStream inputStream = resourceLoader.getResourceAsStream(path);
        if(inputStream == null) {
            throw new NullPointerException("Invalid file path!");
        }

        return inputStream;
    }

    private static InputStream getValidFile(String path, ResourceType type) throws IllegalArgumentException, NullPointerException {
        logger.info("Looking for a valid file of " + type.toString() + " in path '" + path + "'");
        String[] parts = path.split("\\.");
        if(isValidFile(parts, type.toString())) {
            return loadResourceFile(path);
        } else {
            throw new IllegalArgumentException("Please pass a valid " + type.toString() + " file!");
        }
    }

    public static InputStream loadProperties(String path) throws IllegalArgumentException {
        return getValidFile(path, ResourceType.PROP);
    }

    private static boolean isValidFile(String[] fileParts, String type) {
        return  (fileParts.length >= 2) &&
                (fileParts[fileParts.length - 1].equalsIgnoreCase(type));
    }
}