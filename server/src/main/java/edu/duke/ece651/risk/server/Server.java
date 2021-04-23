package edu.duke.ece651.risk.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class Server {
  private int portNumber;
  private ServerSocket serverSocket;
  private HashMap<String,String> accounts;
  private ArrayList<Room> roomList;
  private HashSet<String> onlineUserNames;

  public Server(int num,ServerSocket ss){
    this.portNumber = num;
    this.serverSocket=ss;
    this.accounts=new HashMap<String,String>();
    this.roomList=new ArrayList<Room>();
    this.onlineUserNames = new HashSet<String>();
    
  }

  public synchronized void  addOnlineUserNames(String name) {
    this.onlineUserNames.add(name);
  }

  public synchronized void removeOnlineUserNames(String name) {
    this.onlineUserNames.remove(name);
  }
  
public  Boolean isUserOnline(String name) {
    return onlineUserNames.contains(name);
  }
  
  
  public void createRooms()throws IOException{
    for(int i=0; i<4; i++){
      Room room = new Room( i, i + 2);
      roomList.add(room);
      Thread t=new Thread(room);
      t.start();
    }
    System.out.println("4 rooms created");
  }

  public void createAccounts(){
    accounts.put("Yisong","ys");
    accounts.put("Aman","a");
    accounts.put("JY","jy");
    accounts.put("Yutong","yt");
    accounts.put("Yang","y");
    for (int i = 1; i <= 100; i++) {
      accounts.put("Bots"+i, String.valueOf(i));
    }
  }

  public void acceptConnection()throws IOException{
    Socket client=serverSocket.accept();
    User user=new User(client,accounts,roomList,this);
    Thread t= new Thread(user);
    t.start();
    System.out.println("a client connected");
  }
  public static void main(String[] args) throws IOException {
    //BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    int portNumber=3333;
    try (var listener = new ServerSocket(portNumber)) {
      System.out.println("The server is running...");
      Server server=new Server(portNumber, listener);
      server.createAccounts();
      server.createRooms();
      while(true){
        server.acceptConnection();
        if (args.length>0 && args[0].equals("test")){
          break;
          }
      }
    }catch(Exception e){}
    
  }
  
}








