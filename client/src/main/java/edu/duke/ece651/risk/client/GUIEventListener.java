package edu.duke.ece651.risk.client;

import java.util.EventListener;

public interface GUIEventListener extends EventListener {
    /**
     * Self defined method that client should execute when the GUI Messenger has been changed.
     *
     * @param ge GUIEvent object
     */
    public void onGUIEvent(GUIEvent ge);
}
