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
    }

    public void setWaitOthers(String literalMessage) {
        this.literalMessage = literalMessage;
        listener.onUpdateWaitOthers(new GUIEvent(this, literalMessage));
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
        listener.onUpdateJoinRoom(new GUIEvent(this, roomId));
    }
}












