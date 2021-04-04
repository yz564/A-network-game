/*
 * This Java source file was generated by the Gradle 'init' task.
 */
/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.risk.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

import edu.duke.ece651.risk.shared.ObjectIO;

/**
 * in and out are objectIOStream tmp stores the most recent ObjectIO read by the
 * client (sent from the server)
 */
public class App{
  private Socket server;
  private ObjectInputStream in;
  private ObjectOutputStream out;
  private ObjectIO tmp;
  private BufferedReader stdIn;
  private ArrayList<Player> players;
  private int currentRoomId;
  private HashSet<Integer> joinedRoomId;

  //private String serverAdd;

  public App() {
    this.server = null;
    this.in = null;
    this.out = null;
    this.tmp = null;
    this.stdIn = null;
    this.players = null;
    this.joinedRoomId = null;
  }

  public Player getPlayer() {
    return players.get(currentRoomId);
  }

  public String trySelectTerritory(String info) throws Exception{
    return players.get(currentRoomId).tryInitialization(info);
  }

  public String tryConnect(String serverAdd) {
    try {
      this.server = new Socket(serverAdd, 3333);
      this.out = new ObjectOutputStream(server.getOutputStream());
      this.in = new ObjectInputStream(server.getInputStream());
      this.tmp = null;
      this.initializeApp(server, in, out, tmp);
    }
    catch (Exception e){
      return "Server address does not exist!";
    }
    return null;
  }

  public void initializeApp(Socket server, ObjectInputStream in, ObjectOutputStream out, ObjectIO tmp) {
    this.server = server;
    this.in = in;
    this.out = out;
    this.tmp = tmp;
    this.stdIn = new BufferedReader(new InputStreamReader(System.in));
    this.players = new ArrayList<Player>();
    for (int i = 0; i < 4; i++) {
      players.add(new Player(i,in, out,stdIn));
    }
    this.joinedRoomId = new HashSet<Integer>();
  }

  public App(Socket server, ObjectInputStream in, ObjectOutputStream out, ObjectIO tmp) {
    initializeApp(server, in, out, tmp);
  }


  public void checkIn() {
    if (!joinedRoomId.contains(currentRoomId)) {//if not joined before, new a player and thread
      Player p = players.get(currentRoomId);
      Thread t = new Thread(p);
      t.start();
      players.set(currentRoomId, p);
      joinedRoomId.add(currentRoomId);
    }
  }

  public void requestLeave() throws Exception{
    out.writeObject(new ObjectIO("/leave"));
    out.flush();
    out.reset();
  }

  /**
   *every time after join a room, call this method
   */  
  public void runOnePlayer() throws Exception {
    String tmpS;
    checkIn();
    //if Player.wait is true, the System.in read at here. Otherwise, the System.in read in player thread
    
    players.get(currentRoomId).setWait(true);
    while (true) {
      if (players.get(currentRoomId).isWait()) {
        players.get(currentRoomId).ready = true;//once arrive here (let the main thread listen instead of let the player thread listen), set player.ready = true, tell the player to set player.wait=false.
        if ((tmpS = stdIn.readLine()) != null) {//arrive here only when player.wait is true
          if (tmpS.equals("/leave")) { //if request leave, tell the server and go back join room page
            requestLeave();
            break;
          }
          players.get(currentRoomId).updateInput(tmpS); //if not request leave, pass what the client get from system.in to the player.tmpS
        }
      }
    }
  }
  
  public ObjectIO receiveMessage() throws Exception {
    return (ObjectIO) in.readObject();
  }
  
  public void sendMessage(ObjectIO info) throws Exception{
    out.writeObject(info);
    out.flush();
    out.reset();
  }
  
  public Boolean tryLogin(String userName, String password) throws Exception {
    receiveMessage();
    sendMessage(new ObjectIO(userName));
    receiveMessage();
    sendMessage(new ObjectIO(password));
    tmp = receiveMessage();
    return tmp.id == 0;
  }
  
  public Boolean tryJoinRoom(int roomId) throws Exception {
    receiveMessage();
    sendMessage(new ObjectIO("", roomId));
    currentRoomId = roomId - 1;
    tmp = receiveMessage();
    return tmp.id == 0;
  }
  /*
  @Override
  public void run() {
    try (var socket = new Socket(serverAdd, 3333)){
            this.server = socket;
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
          } catch (Exception e) {
    }
    while (true) {
    }
  }*/

  /**
   * the enter point of the client. after connecting with the server, new App, and
   * call its method to communicate with the server(game).
   */
  public static void main(String[] args) throws Exception {
    System.out.println("Please enter server address: (default is localhost by hitting Enter)");
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    String ServerAddress = stdIn.readLine();
    if (ServerAddress.equals("")) {
      ServerAddress = "localhost";
    }
    int portNumber = 3333;
    try (var server = new Socket(ServerAddress, portNumber)) {
      ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
      ObjectInputStream in = new ObjectInputStream(server.getInputStream());
      ObjectIO tmp = null;
      App client = new App(server, in, out, tmp);
      String userName = "";
      String password = "";
        while (!client.tryLogin(userName, password)) {
          System.out.println("userName:");
          userName = stdIn.readLine();
          System.out.println("password:");
          password = stdIn.readLine();
        }
        while (true) {
        int roomId = 0;
        while (!client.tryJoinRoom(roomId)) {
          System.out.println("join a room:");
          roomId = Integer.parseInt(stdIn.readLine());
        }
        client.runOnePlayer();
      }
    } //catch (Exception e) {
    //}
  }
}


