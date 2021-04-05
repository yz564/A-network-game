package edu.duke.ece651.risk.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import edu.duke.ece651.risk.shared.ObjectIO;
import org.junit.jupiter.api.Test;

public class UserTest {
  @Test
  public void test_User() {
    try{
      ClientHelper ch = new ClientHelper();
    Thread t = new Thread(ch);
    t.start();
    System.out.println("New thread created");
    ClientHelper ch2 = new ClientHelper();
    Thread t2 = new Thread(ch2);
    t2.start();
    System.out.println("New thread created");
    ClientHelper ch3 = new ClientHelper();
    Thread t3 = new Thread(ch3);
    t3.start();
    System.out.println("New thread created");
    ServerSocket ss=new ServerSocket(3333);
    Socket s=ss.accept();
    Socket s2=ss.accept();
    Socket s3=ss.accept();
    HashMap<String,String>accounts=new HashMap<String,String>();
    ArrayList<Room> rooms=new ArrayList<Room>();
    accounts.put("Yisong","ys");
    accounts.put("Aman","a");
    accounts.put("JY","jy");
    accounts.put("Yutong","yt");
    accounts.put("Yang","y");
    for(int i=0; i<4; i++){
      Room room = new Room( i, i + 2);
      rooms.add(room);
      Thread tt=new Thread(room);
      tt.start();
    }
    User u = new User(s, accounts, rooms);
    u.getName();
    u.getIn();
    u.getOut();
    u.getObj();
    Thread tt = new Thread(u);
    tt.start();
    User u2 = new User(s2, accounts, rooms);
    Thread tt2 = new Thread(u2);
    tt2.start();
    User u3 = new User(s3, accounts, rooms);
    Thread tt3 = new Thread(u3);
    tt3.start();
  } catch (Exception e) {
  }
  }

  private class ClientHelper implements Runnable{
    private Socket socket;
  private ObjectInputStream in;
  private ObjectOutputStream out;
    private ObjectIO tempObj;
    @Override
    public void run(){
      try{
      Thread.sleep(100);
      while (true) {
        this.socket = new Socket("localhost", 3333);
        this.out= new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        break;
      }
      tempObj = (ObjectIO) in.readObject();
      out.writeObject(new ObjectIO("Yang"));
      out.flush();
      out.reset();
      tempObj = (ObjectIO) in.readObject();
      out.writeObject(new ObjectIO("yy"));
      out.flush();
      out.reset();
      tempObj = (ObjectIO) in.readObject();
      out.writeObject(new ObjectIO("Yang"));
      out.flush();
      out.reset();
      tempObj = (ObjectIO) in.readObject();
      out.writeObject(new ObjectIO("y"));
      out.flush();
      out.reset();
      tempObj = (ObjectIO) in.readObject();
      out.writeObject(new ObjectIO("0",0));
      out.flush();
      out.reset();
      tempObj = (ObjectIO) in.readObject();
      out.writeObject(new ObjectIO("1",1));
      out.flush();
      out.reset();
      out.writeObject(new ObjectIO(" ",1));
      out.flush();
      out.reset();
      out.writeObject(new ObjectIO("/leave",1));
      out.flush();
      out.reset();
      tempObj = (ObjectIO) in.readObject();
      out.writeObject(new ObjectIO("1",1));
      out.flush();
      out.reset();
      System.out.println("thread dead");
    } catch (Exception e) {
    }
    }
}
}






