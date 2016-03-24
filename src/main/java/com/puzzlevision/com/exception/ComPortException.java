package com.puzzlevision.com.exception;

import java.io.IOException;

/**
 * Created by Brendan on 3/23/2016.
 */
public class ComPortException extends IOException {

    public ComPortException(String msg) {
        super(msg);
    }
}
