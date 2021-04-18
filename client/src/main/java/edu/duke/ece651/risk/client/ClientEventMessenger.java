package edu.duke.ece651.risk.client;

import edu.duke.ece651.risk.shared.WorldMap;

import java.io.IOException;

public class ClientEventMessenger {
    ClientEventListener listener;
    String literalMessage;
    Boolean statusBoolean;
    WorldMap map;

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
        this.statusBoolean = statusBoolean;
        listener.onUpdateEvent(new ClientEvent(this, statusBoolean));
    }

    public void setMap(WorldMap map) throws IOException {
        this.map = map;
        listener.onUpdateEvent(new ClientEvent(this, map));
    }
}
