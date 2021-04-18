package edu.duke.ece651.risk.client;

public class GUIEventMessenger {
    GUIEventListener listener;
    String literalMessage;

    public GUIEventMessenger() {
        this.listener = null;
        this.literalMessage = null;
    }

    public void setGUIEventListener(GUIEventListener listener) {
        this.listener = listener;
    }

    public void setLiteralMessage(String literalMessage) {
        this.literalMessage = literalMessage;
        listener.onUpdateEvent(new GUIEvent(this, literalMessage));
    }
}
