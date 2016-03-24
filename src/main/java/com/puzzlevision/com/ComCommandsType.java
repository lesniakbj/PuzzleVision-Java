package com.puzzlevision.com;

/**
 * Created by Brendan on 3/23/2016.
 */
public enum ComCommandsType {
    // General command types
    PORT_LIST("port list"),
    SET("set"),

    // Connection / Data command types
    CONNECT("connect"),
    TRANSMIT("transmit"),
    RECEIVE("receive");

    private String name;

    ComCommandsType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
