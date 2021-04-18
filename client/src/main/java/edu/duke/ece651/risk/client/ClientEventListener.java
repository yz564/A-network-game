package edu.duke.ece651.risk.client;
import java.util.EventListener;

public interface ClientEventListener extends EventListener {
  
  public void onUpdateEvent(ClientEvent ce);
  
}












