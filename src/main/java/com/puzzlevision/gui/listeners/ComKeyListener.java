package com.puzzlevision.gui.listeners;

import com.puzzlevision.gui.PuzzleVisionMainGUI;
import com.puzzlevision.model.PuzzleVision;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.text.BadLocationException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Brendan on 3/23/2016.
 *
 * Sends data from the command window to the
 * Com port that is specified in the configuration.
 */
public class ComKeyListener extends KeyAdapter {
    private static final Logger logger = LogManager.getLogger(ComButtonListener.class);

    private static final Integer ENTER = 10;

    private PuzzleVisionMainGUI mainGUI;
    private PuzzleVision puzzleVision;

    public ComKeyListener(PuzzleVisionMainGUI mainGUI) {
        this.mainGUI = mainGUI;
        puzzleVision = mainGUI.getPuzzleVision();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        /* Unimplemented */
    }

    @Override
    public void keyPressed(KeyEvent e) {
        /* Unimplemented */
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        logger.info("User has typed in the com field: {}, Code: {}", e.getKeyChar(), e.getExtendedKeyCode());

        if(e.getExtendedKeyCode() == ENTER) {
            try {
                int line = mainGUI.getDeviceTextArea().getLineOfOffset(mainGUI.getDeviceTextArea().getCaretPosition()) - 1;
                int start = mainGUI.getDeviceTextArea().getLineStartOffset(line);
                int end = mainGUI.getDeviceTextArea().getLineEndOffset(line);

                String userComm = mainGUI.getDeviceTextArea().getText(start, (end - start));
                logger.info("User typed command: {}", userComm);

                // Com handler should have method getActiveComPort which returns an open
                // port. Or  I can getComPort(puzzleVision.getProperty("puzzlevision.com.port"));
               // puzzleVision.getComHandler()
            } catch (BadLocationException ex) {
                logger.error("Exception while trying to determine command line from Text Area", ex);
            }
        }
    }
}
