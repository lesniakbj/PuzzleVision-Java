package com.puzzlevision.gui.listeners;

import com.puzzlevision.com.ComHandler;
import com.puzzlevision.gui.PuzzleVisionMainGUI;
import com.puzzlevision.model.PuzzleVision;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Brendan on 3/23/2016.
 *
 * The ComButtonListener is a more generic listenern that
 * is attached to all of the buttons that exist on the
 * COM panel of the GUI. Once a button is pressed, it passes
 * the argument on to the PuzzleVision instance so that it
 * can run the command.
 */
public class ComButtonListener implements ActionListener {
    private static final Logger logger = LogManager.getLogger(ComButtonListener.class);

    private PuzzleVisionMainGUI mainGUI;
    private PuzzleVision puzzleVision;

    private String action;

    public ComButtonListener(PuzzleVisionMainGUI mainGUI) {
        this.mainGUI = mainGUI;
        puzzleVision = mainGUI.getPuzzleVision();
        if(!puzzleVision.hasComHandler()) {
            puzzleVision.setComHandler(new ComHandler());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the action that we are going to perform, and have
        // the Puzzle Vision instance handle that action. If things
        // need to update on the GUI, update them after performing
        // the action, or notify of error.
        action = e.getActionCommand().toLowerCase();

        if(puzzleVision.getComHandler().isComPortAvailable(mainGUI.getComPortName())) {
            logger.info("Setting the parameters for port, {}, and baud rate, {}.", mainGUI.getComPortName(), mainGUI.getBaudRate());
            PuzzleVision.setSystemProperty("puzzlevision.com.port", mainGUI.getComPortName());
            PuzzleVision.setSystemProperty("puzzlevision.com.baud", mainGUI.getBaudRate().toString());
        }

        if (!puzzleVision.handleAction(action)) {
            logger.error("Unable to handle action! [{}]", action);
        }
    }
}
