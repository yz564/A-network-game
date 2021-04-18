package edu.duke.ece651.risk.client;

public class GUIEventMessenger {
    GUIEventListener listener;
    int message = 0;

    public void addSGUIEventListener(GUIEventListener listener) {
        this.listener = listener;
    }

    public void setMessage(int message) {
        this.message = message;
        listener.onUpdateEvent(new GUIEvent(this, message));
    }
}
