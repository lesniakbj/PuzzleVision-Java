package com.puzzlevision.gui.listeners;

import com.puzzlevision.model.PuzzleVision;
import com.puzzlevision.gui.PuzzleVisionMainGUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Brendan on 3/23/2016.
 */
public class ComKeyListener extends KeyAdapter {
    private static final Logger logger = LogManager.getLogger(ComButtonListener.class);

    private PuzzleVisionMainGUI mainGUI;
    private PuzzleVision puzzleVision;

    public ComKeyListener(PuzzleVisionMainGUI mainGUI) {
        this.mainGUI = mainGUI;
        puzzleVision = mainGUI.getPuzzleVision();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        super.keyTyped(e);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        /* Unimplemented */
    }

    @Override
    public void keyReleased(KeyEvent e) {
         /* Unimplemented */
    }
}
