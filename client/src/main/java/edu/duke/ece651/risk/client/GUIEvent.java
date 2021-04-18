package edu.duke.ece651.risk.client;

import java.util.EventObject;

public class GUIEvent extends EventObject {
    private final Object source;
    private final String literalMessage;

    /**
     * The constructor of GUIEvent.
     *
     * @param source the object to listen.
     * @param literalMessage the field to listen that is a literalMessage send from GUI to client.
     */
    public GUIEvent(Object source, String literalMessage) {
        super(source);
        this.source = source;
        this.literalMessage = literalMessage;
    }

    public String getLiteralMessage() {
        return literalMessage;
    }

    public Object getSource() {
        return source;
    }
}
