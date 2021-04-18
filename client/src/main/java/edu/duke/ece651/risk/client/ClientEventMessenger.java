package edu.duke.ece651.risk.client;

import java.io.IOException;

public class ClientEventMessenger {
    ClientEventListener listener;
    String literalMessage;
    Boolean statusBoolean;

    public ClientEventMessenger() {
        this.listener = null;
        this.literalMessage = null;
        this.statusBoolean = false;
    }

    public void setClientEventListener(ClientEventListener listener) {
        this.listener = listener;
    }

    public void setLiteralMessage(String literalMessage) throws IOException {
        this.literalMessage = literalMessage;
        listener.onUpdateEvent(new ClientEvent(this, literalMessage));
    }

    public void setStatusBoolean(Boolean statusBoolean) throws IOException {
        // this.statusBoolean = literalMessage;
        listener.onUpdateEvent(new ClientEvent(this, statusBoolean));
    }
}
