package edu.duke.ece651.risk.server;

import java.io.IOException;
import java.net.Socket;

import org.junit.jupiter.api.Test;

public class ServerTest {
  @Test
  public void test_server() {
    ClientHelper ch = new ClientHelper();
    Thread t = new Thread(ch);
    t.start();
    System.out.println("New thread created");
    String[] args={"test"};
    try{
      Server.main(args);
    }catch(IOException e){}
    
  }

  private class ClientHelper implements Runnable{
    @Override
    public void run(){
      try{
      Thread.sleep(100);
      while (true) {
        Socket server = new Socket("localhost", 3333);
        break;
      }
      System.out.println("thread dead");
    } catch (Exception e) {
    }
    }
    
  }

}










