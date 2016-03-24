package com.puzzlevision.gui.listeners;

import com.puzzlevision.com.ComHandler;
import com.puzzlevision.model.PuzzleVision;
import com.puzzlevision.gui.PuzzleVisionMainGUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Brendan on 3/23/2016.
 */
public class ComButtonListener implements ActionListener {
    private static final Logger logger = LogManager.getLogger(ComButtonListener.class);

    private PuzzleVisionMainGUI mainGUI;
    private PuzzleVision puzzleVision;

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
        String action = e.getActionCommand().toLowerCase();
        if (!puzzleVision.handleAction(action)) {
            logger.error("Unable to handle action! [{}]", action);
            return;
        }
    }
}
