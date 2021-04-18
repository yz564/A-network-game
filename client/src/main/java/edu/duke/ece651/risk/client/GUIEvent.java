package edu.duke.ece651.risk.client;

import java.util.EventObject;

public class GUIEvent extends EventObject {
    private Object source;
    private int message;

    /**
     * The constructor of GUIEvent.
     *
     * @param source the object to listen.
     * @param message the field to listen.
     */
    public GUIEvent(Object source, int message) {
        super(source);
        this.source = source;
        this.message = message;
    }

    public int getMessage() {
        return message;
    }
}
