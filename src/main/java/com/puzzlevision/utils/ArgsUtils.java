package com.puzzlevision.utils;

import com.puzzlevision.model.PuzzleVision;

/**
 * Created by Brendan on 3/21/2016.
 */
public class ArgsUtils {
    public static String getArgumentMessage(PuzzleVision puzzleVision, String argument) {
        if(isHelpOption(argument)) {
            return puzzleVision.getMessageProperties().getProperty("message.help") + "\n" +
                    puzzleVision.getMessageProperties().getProperty("message.options");
        }

        if(isVisionTestOption(argument)) {
            return puzzleVision.getMessageProperties().getProperty("message.vision.test");
        }

        return puzzleVision.getMessageProperties().getProperty("message.unsupported");
    }

    private static boolean isHelpOption(String argument) {
        return argument.equalsIgnoreCase(Strings.CONFIG_HELP) || argument.equalsIgnoreCase(Strings.CONFIG_HELP_LONG);
    }

    private static boolean isVisionTestOption(String argument) {
        return argument.equalsIgnoreCase(Strings.CONFIG_TEST) || argument.equalsIgnoreCase(Strings.CONFIG_TEST_LONG);
    }
}
