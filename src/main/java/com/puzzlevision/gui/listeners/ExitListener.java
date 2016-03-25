package com.puzzlevision.gui.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by Brendan on 3/24/2016.
 *
 * Listener that is attached to the main window.
 * Specifically listening for Exit Events so we can
 * clean up when the window closes.
 */
public class ExitListener implements WindowListener {
    private static final Logger logger = LogManager.getLogger(ExitListener.class);
    @Override
    public void windowOpened(WindowEvent e) {
        /* UNIMPLEMENTED */
    }

    @Override
    public void windowClosing(WindowEvent e) {
        logger.info("Exit event received! Saving all work for next session.");
    }

    @Override
    public void windowClosed(WindowEvent e) {
        /* UNIMPLEMENTED */
    }

    @Override
    public void windowIconified(WindowEvent e) {
        /* UNIMPLEMENTED */
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        /* UNIMPLEMENTED */
    }

    @Override
    public void windowActivated(WindowEvent e) {
        /* UNIMPLEMENTED */
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        /* UNIMPLEMENTED */
    }
}
