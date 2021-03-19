package edu.duke.ece651.risk.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.risk.shared.ObjectIO;

public class PlayerTest {
  @Test
  public void test_get_constructor() {
    try{ 
      ServerSocket ss = new ServerSocket(6666);
      Socket c = new Socket("localhost", 6666);
      String name = "Ned";
      Player p = new Player(c, name);
      assertEquals(name, p.getName());
      assertEquals(false, p.isReady());
      p.setNotReady();
      assertEquals(false, p.isReady());
      Thread t = new Thread(p);
      p.watch = false;
      p.isEnd = true;
      t.start();
      p.isEnd = true;
    }
    catch (Exception e) {}    
 } 

   
}









