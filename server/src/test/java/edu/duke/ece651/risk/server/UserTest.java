package edu.duke.ece651.risk.server;

import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.Test;

public class UserTest {
  @Test
  public void test_User() {
    try{
      ClientHelper ch = new ClientHelper();
    Thread t = new Thread(ch);
    t.start();
    ServerSocket ss=new ServerSocket(3333);
    ss.accept();
    }catch(Exception e){}
    
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






