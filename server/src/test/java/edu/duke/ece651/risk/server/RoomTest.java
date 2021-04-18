package edu.duke.ece651.risk.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.risk.shared.ObjectIO;

public class RoomTest {
  @Test
  public void test_room() {
      try{
      ClientHelper ch = new ClientHelper();
    Thread t = new Thread(ch);
    t.start();
    ClientHelper2 ch2 = new ClientHelper2();
    Thread t2 = new Thread(ch2);
    t2.start();
    ServerSocket ss=new ServerSocket(8888);
    Socket s=ss.accept();
    Socket s2=ss.accept();
    HashMap<String,String>accounts=new HashMap<String,String>();
    ArrayList<Room> rooms=new ArrayList<Room>();
    accounts.put("Yisong","ys");
    accounts.put("Aman","a");
    accounts.put("JY","jy");
    accounts.put("Yutong","yt");
    accounts.put("Yang","y");
    for(int i=0; i<1; i++){
      Room room = new Room( i, i + 2);
      rooms.add(room);
      Thread tt=new Thread(room);
      tt.start();
    }
    var listener = new ServerSocket(5555);
    Server server=new Server(5555, listener);
    User p1=new User(s,accounts,rooms,server);
    User p2=new User(s2,accounts,rooms,server);
    Thread tt1 = new Thread(p1);
    Thread tt2 = new Thread(p2);
    tt1.start();
    tt2.start();
    Thread.sleep(1000);
      }catch(Exception e){}
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
        this.socket = new Socket("localhost", 8888);
        this.out= new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        break;
      }
      tempObj = (ObjectIO) in.readObject();
      out.writeObject(new ObjectIO("Yang"));
      out.flush();
      out.reset();
      tempObj = (ObjectIO) in.readObject();
      out.writeObject(new ObjectIO("y"));
      out.flush();
      out.reset();
      tempObj = (ObjectIO) in.readObject();
      out.writeObject(new ObjectIO("1",1));
      out.flush();
      out.reset();
      tempObj = (ObjectIO) in.readObject();
      out.writeObject(new ObjectIO());
      out.flush();
      out.reset();
      tempObj = (ObjectIO) in.readObject();
      tempObj = (ObjectIO) in.readObject();
      out.writeObject(new ObjectIO("1",1));
      out.flush();
      out.reset();
      tempObj = (ObjectIO) in.readObject();
      tempObj = (ObjectIO) in.readObject();
      tempObj = (ObjectIO) in.readObject();
      HashMap<String, Integer> po = new HashMap<String, Integer>();
      po.put("Fuqua", 1);
      po.put("Gross Hall", 2);
      po.put("FFRC", 0);
      po.put("Pratt", 0);
      po.put("Perkins Library",10);
      po.put("Wilson Gym", 3);
      po.put("Cameron Stadium", 5);
      po.put("Wallace Stadium", 1);
      ObjectIO myIO=new ObjectIO(); 
      myIO.placeOrders=po;
      out.writeObject(myIO);
      out.flush();
      out.reset();
      tempObj = (ObjectIO) in.readObject();
      }catch(Exception e){}
    }
}
  private class ClientHelper2 implements Runnable{
    private Socket socket;
  private ObjectInputStream in;
  private ObjectOutputStream out;
    private ObjectIO tempObj;
    @Override
    public void run(){
      try{
      Thread.sleep(300);
      while (true) {
        this.socket = new Socket("localhost", 8888);
        this.out= new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        break;
      }
      tempObj = (ObjectIO) in.readObject();
      out.writeObject(new ObjectIO("JY"));
      out.flush();
      out.reset();
      tempObj = (ObjectIO) in.readObject();
      out.writeObject(new ObjectIO("jy"));
      out.flush();
      out.reset();
      tempObj = (ObjectIO) in.readObject();
      out.writeObject(new ObjectIO("1",1));
      out.flush();
      out.reset();
      tempObj = (ObjectIO) in.readObject();
      out.writeObject(new ObjectIO());
      out.flush();
      out.reset();
      tempObj = (ObjectIO) in.readObject();
      tempObj = (ObjectIO) in.readObject();
      Thread.sleep(400);
      out.writeObject(new ObjectIO("1",1));
      out.flush();
      out.reset();
      tempObj = (ObjectIO) in.readObject();
      out.writeObject(new ObjectIO("2",2));
      out.flush();
      out.reset();
      tempObj = (ObjectIO) in.readObject();
      tempObj = (ObjectIO) in.readObject();
      tempObj = (ObjectIO) in.readObject();
      HashMap<String, Integer> po = new HashMap<String, Integer>();
      po.put("Law", 1);
      po.put("LSRC", 2);
      po.put("Bryan Center", 0);
      po.put("Duke Clinics", 0);
      po.put("Duke Hospital",10);
      po.put("Duke Garden", 3);
      po.put("Duke Chapel", 5);
      po.put("Student Housing", 1);
      ObjectIO myIO=new ObjectIO(); 
      myIO.placeOrders=po;
      out.writeObject(myIO);
      out.flush();
      out.reset();
      tempObj = (ObjectIO) in.readObject();
      }catch(Exception e){}
}
  }
}









