package edu.duke.ece651.risk.client;

import java.util.EventObject;

public class ClientEvent extends EventObject {
    private final Object source;
    private final String literalMessage;
    private final Boolean statusBoolean;

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
        this.statusBoolean=null;
    }
  public ClientEvent(Object source, Boolean statusBoolean) {
        super(source);
        this.source = source;
        this.literalMessage = null;
        this.statusBoolean=statusBoolean;
    }

    public String getLiteralMessage() {
        return literalMessage;
    }

  public Boolean getStatusBoolean() {
        return statusBoolean;
    }

    public Object getSource() {
        return source;
    }
}







