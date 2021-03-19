package edu.duke.ece651.risk.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Disabled;

public class PlayerTest {
  @Test
  public void test_get_constructor() {
    try {
      ServerSocket ss = new ServerSocket(1111);
      Socket s = new Socket("localhost", 1111);
      String name = "Ned";
      Player p = new Player(s, name);
      assertEquals(name, p.getName());
      assertEquals(false, p.isReady());
      p.setNotReady();
      assertEquals(false, p.isReady());
    }
    catch (Exception e) {}    
  }

  @Disabled
  @Test
  @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
  public void test_run() {
    try {
      ServerSocket ss = new ServerSocket(1111);
      Socket s = new Socket("localhost", 1111);
      String name = "Ned";
      Player p = new Player(s, name);
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      p.isEnd = true;
      p.run();
      String msg = "-----ReadClientInput------";
      assertEquals(msg, bytes.toString());
    }
    catch (Exception e) {
    }
  }
  
  
}









