package edu.duke.ece651.risk.client;

import edu.duke.ece651.risk.shared.WorldMap;

import java.util.EventObject;

public class ClientEvent extends EventObject {
    private final Object source;
    private final String literalMessage;
    private final Boolean statusBoolean;
    private final WorldMap map;

    /**
     * The constructor of ClientEvent.
     *
     * @param source the object to listen.
     * @param literalMessage the field to listen that is a literalMessage send from client to GUI.
     */
    public ClientEvent(Object source, String literalMessage) {
        super(source);
        this.source = source;
        this.literalMessage = literalMessage;
        this.statusBoolean = null;
        this.map = null;
    }

    public ClientEvent(Object source, Boolean statusBoolean) {
        super(source);
        this.source = source;
        this.literalMessage = null;
        this.statusBoolean = statusBoolean;
        this.map = null;
    }

    public ClientEvent(Object source, WorldMap map) {
        super(source);
        this.source = source;
        this.literalMessage = null;
        this.statusBoolean = null;
        this.map = map;
    }

    public String getLiteralMessage() {
        return literalMessage;
    }

    public Boolean getStatusBoolean() {
        return statusBoolean;
    }

    public WorldMap getMap() {
        return map;
    }

    public Object getSource() {
        return source;
    }
}
