package edu.duke.ece651.risk.client;

public class GUIEventMessenger {
    GUIEventListener listener;
    String literalMessage;
    int roomId;

    public GUIEventMessenger() {
        this.listener = null;
        this.literalMessage = null;
        this.roomId = 0;
    }

    public void setGUIEventListener(GUIEventListener listener) {
        this.listener = listener;
        System.out.println("setGUIEventListener "+listener);
    }

    public void setWaitOthers(String literalMessage) {
        this.literalMessage = literalMessage;
        System.out.println("setWaitOthers: "+literalMessage);
        listener.onUpdateEvent(new GUIEvent(this, literalMessage));
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
        System.out.println("setRoomId: "+roomId);
        listener.onUpdateEvent(new GUIEvent(this, roomId));
    }
}












