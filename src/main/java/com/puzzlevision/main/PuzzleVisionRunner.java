package com.puzzlevision.main;

import com.puzzlevision.model.PuzzleVision;
import com.puzzlevision.utils.Strings;
import com.puzzlevision.gui.PuzzleVisionMainGUI;
import com.puzzlevision.utils.ArgsUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.*;

/**
 * Created by Brendan on 3/20/2016.
 *
 * This is the main class of the puzzlevision Project. It
 * instantiates any classes it needs and then performs the
 * analysis on the images, returning the shape of the puzzle
 * piece.
 */
public class PuzzleVisionRunner {
    private static final Logger logger = LogManager.getLogger("PuzzleVisionRunner");

    // The base program and any properties that are passed
    // in from the calling program.
    private static PuzzleVision puzzleVision;
    private static Map<String, String> runtimeProperties;

    public static void main(String[] args) {
        // Initialize a new PuzzleVision instance
        runtimeProperties = new HashMap<String, String>();
        puzzleVision = new PuzzleVision();

        // Load the puzzle vision default properties.
        puzzleVision.loadSystemProperties(Strings.PROPS_SYSTEM);
        puzzleVision.loadMessageProperties(Strings.PROPS_MESSAGES);

        gatherArguments(args);
        parseArguments();
    }

    private static void parseArguments() {
        // Test parameter passed
        if (hasTestParameter()) {
            logger.info("Testing getting a property from the system properties:");
            logger.info(puzzleVision.getSystemProperties().getProperty("puzzlevision.com-port"));
        }

        // Gui parameter passed
        if (hasGUIParameter()) {
            logger.info("Running GUI module...");
            PuzzleVisionMainGUI puzzleGUI = new PuzzleVisionMainGUI(puzzleVision);
        }
    }

    private static void gatherArguments(String[] args) {
        // First check the arguments passed in to see what we are
        // doing for system configuration. Get any messages that are
        // associated with those arguments.
        if (args.length != 0) {
            for (String arg : args) {
                logger.info("Argument Passed: " + arg);
                runtimeProperties.put(arg, ArgsUtils.getArgumentMessage(puzzleVision, arg));
            }
        }
    }

    private static boolean hasTestParameter() {
        return runtimeProperties.containsKey("-t") || runtimeProperties.containsKey("-test");
    }

    private static boolean hasGUIParameter() {
        return runtimeProperties.containsKey("-g") || runtimeProperties.containsKey("-gui");
    }
}

