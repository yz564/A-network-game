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
        System.out.println("setClientEventListener " + listener);
    }

    public void setLiteralMessage(String literalMessage, String nextViewName) throws Exception {
        this.literalMessage = literalMessage;
        listener.onUpdateEvent(new ClientEvent(this, nextViewName, literalMessage));
    }

    public void setStatusBoolean(Boolean statusBoolean, String nextViewName) throws Exception {
        this.statusBoolean = statusBoolean;
        listener.onUpdateEvent(new ClientEvent(this, nextViewName, statusBoolean));
    }

    public void setMap(WorldMap map, String nextViewName) throws Exception {
        this.map = map;
        listener.onUpdateEvent(new ClientEvent(this, nextViewName, map));
    }

    public Boolean getStatus() {
        return statusBoolean;
    }
}
