package com.puzzlevision.resource;

/**
 * Created by Brendan on 3/20/2016.
 *
 * An enumeration of the types of files that can
 * be loaded by the resource loader.
 */
public enum ResourceType {
    TXT("txt"),
    PROP("properties"),
    IMAGE("jpg");

    private String name;

    ResourceType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
