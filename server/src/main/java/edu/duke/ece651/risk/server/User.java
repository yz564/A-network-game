package edu.duke.ece651.risk.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import edu.duke.ece651.risk.shared.ObjectIO;

public class User implements Runnable{
  private Socket clientSocket;
  private volatile String inputName;
  private volatile String inputPassword;
  private final HashMap<String, String> accounts;
  private final ArrayList<Room> roomList;
  private ObjectInputStream in;
  private ObjectOutputStream out;
  private volatile ObjectIO tempObj;
  private Boolean leave;
  private volatile int currentRoomId;
  private volatile ArrayList<Player> players;
  private volatile HashSet<Integer> joinedRoomId;
 
  public User (Socket client, HashMap<String,String> accounts, ArrayList<Room> rooms){
    this.clientSocket=client;
    this.inputName="";
    this.inputPassword="";
    this.accounts=accounts;
    this.roomList=rooms;
    this.leave=false;
    this.players = new ArrayList<Player>();
    this.joinedRoomId = new HashSet<Integer>();
    for(int i=0; i<rooms.size();i++){
      players.add(new Player("default"));
    }
  }

  public String getName(){
    return this.inputName;
  }

  public ObjectInputStream getIn(){
    return in;
  }

  public ObjectOutputStream getOut(){
    return out;
  }

  public ObjectIO getObj() {
    return tempObj;
  }
  
  public void logIn() throws Exception{
     myWrite(new ObjectIO("user name:"));
      if ((tempObj = (ObjectIO) in.readObject()) != null) {
        this.inputName = tempObj.message;
      }
      myWrite(new ObjectIO("password:"));
      if ((tempObj = (ObjectIO) in.readObject()) != null) {
        this.inputPassword = tempObj.message;
      }
  }
  
  public Boolean checkAccount() {
    return inputPassword.equals(accounts.get(inputName));
  }

  public Boolean tryJoinRoom() throws Exception{
    logIn();
    while(!checkAccount()){
      myWrite(new ObjectIO("username/password is wrong,try again",-1));
      logIn();
    }
    myWrite(new ObjectIO("Logged in",0));
     myWrite(new ObjectIO("join a room (number):"));
     int id=-1;
     if ((tempObj = (ObjectIO) in.readObject()) != null) {
       id=tempObj.id - 1;
     }
     if (joinedRoomId.contains(id)) {
       currentRoomId = id;
       System.out.println(inputName + " try to rejoin the room " + (currentRoomId+1));
       return true;
     }
    if (id < roomList.size()) {
      
      Room theRoom = roomList.get(id);
      if (theRoom.getCurrentNum() < theRoom.getNum()) {
        players.set(id,theRoom.acceptPlayer(this));
        currentRoomId=id;
        return true;
      }
    }
    return false;
  }

  public void myWrite(ObjectIO info) throws IOException{
    out.writeObject(info);
    out.flush();
    out.reset();
  }

  @Override
  public void run(){
    try{
      this.in = new ObjectInputStream(clientSocket.getInputStream());
      this.out= new ObjectOutputStream(clientSocket.getOutputStream());
      while (true) {
          if (tryJoinRoom()){
            System.out.println(inputName + " joined the room " + (currentRoomId+1));
            joinedRoomId.add(currentRoomId);
            myWrite(new ObjectIO("successful join the room",0));
            while (!leave){
              if((tempObj=(ObjectIO)in.readObject())!=null){
                if (tempObj.message.equals("/leave")) {
                  System.out.println(inputName + " leaved the room " + (currentRoomId+1));
                  break;
                }
                players.get(currentRoomId).updateInput(tempObj);
              }
            }
          }
          else {
            System.out.println(inputName + " failed on joining the room");
            myWrite(new ObjectIO("failed on joining the room, retry",-1));
          }
      }
    }catch(Exception e){}

  }
  
}







