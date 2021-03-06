/*
 * This Java source file was generated by the Gradle 'init' task.
 */

package edu.duke.ece651.risk.server;

//import static org.mockito.Answers.values;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import edu.duke.ece651.risk.shared.ObjectIO;
import edu.duke.ece651.risk.shared.Territory;
import edu.duke.ece651.risk.shared.V1MapFactory;
import edu.duke.ece651.risk.shared.WorldMap;
import edu.duke.ece651.risk.shared.WorldMapFactory;
/**
 *listener is the server socket, for connections from clients
 *numPlayers is the number of plaer set from the server stdIn
 *theMap is the world map of the game, update on server and send to clients trhough ObjectIO
 *playerList is a list of players, when a player lose, the playerList will not change, just change the isEnd variable of the Player.
 *playerNames is a list of player names, this is needed in MapTextView
 *availableGroups used in the initialization phase, give players their available choices
 *soh is a helper class to check and execute orders collected from all "!isEnd" clients.
 */
public class App {
  private ServerSocket listener;
  private WorldMapFactory factory;
  private BufferedReader stdIn;
  private int numPlayers;
  private volatile WorldMap theMap;
  private volatile ArrayList<Player> playerList;
  private volatile ArrayList<String> playerNames;
  private volatile HashSet<Integer> availableGroups;
  private ServerOrderHelper soh;
  /**
   *a simple constructor
   */
  public App(ServerSocket listener, WorldMapFactory factory, BufferedReader in) {
    this.listener = listener;
    this.factory = factory;
    this.stdIn = in;
    this.playerList = new ArrayList<Player>();
    this.playerNames = new ArrayList<String>();
    this.availableGroups = new HashSet<Integer>();
    this.soh = new ServerOrderHelper();
  }
  /**
   *when a connection is made, a Player is created and a thread associated with it is run
   */
  public void acceptConnections() throws IOException {
    System.out.println("Please set the player number:");
    this.numPlayers = Integer.parseInt(stdIn.readLine());
    this.theMap = factory.makeWorldMap(numPlayers);
    System.out.println("wait for players to join... " + "0/" + numPlayers);
    for (int i = 0; i < numPlayers; i++) {
      Socket client = listener.accept();
      System.out.println("New player connected... " + (i + 1) + "/" + numPlayers);
      String name = "Player " + (i + 1);
      playerNames.add(name);
      availableGroups.add(i + 1);
      Player p = new Player(client, name);
      playerList.add(p);
      Thread t = new Thread(p);
      t.start();
    }
  }
  /**
   *select the group of territories sequently, the hashset availableGroups record it
   *then assign initialOwners in theMap
   */
  public void doInitialization() throws Exception {
    for (int i = 0; i < numPlayers; i++) {
      Player p = playerList.get(i);
      ObjectIO m = new ObjectIO(p.getName() + " ,please select your territory groups: ", i, theMap, availableGroups);
      p.out.writeObject(m);
      p.out.flush();
      p.out.reset();//very important to make theMap next time written in ObjectIO is updated...
      while (!p.isReady()) {
      }
      if (theMap.tryAssignInitOwner(Integer.parseInt(p.tmp.message), p.getName())) {
        System.out.println(p.getName() + " selected group " + p.tmp.message);
      }
      availableGroups.remove(Integer.parseInt(p.tmp.message));
      p.setNotReady();
    }
    /* try to make the last player auto choice but has a IO porblem...
    Player p = playerList.get(numPlayers-1);
    if (theMap.tryAssignInitOwner(availableGroups.iterator().next(), p.getName())) {
        System.out.println(p.getName() + " auto selected ");
      }
    */
  }
  
  /**
   *the first for loop: Server send message to all clients let they do placement,clients can do it simultaneously
   *the second while loop: wait until all clients finished placement orders
   *the third loop: update theMap (execute the placement orders)
   */
  public void doPlacement() throws Exception {
    int readyNum = 0;
    //send infomation to clients
    for (int i = 0; i < numPlayers; i++) {
      Player p = playerList.get(i);
      HashMap<String, Territory> tlist = theMap.getPlayerTerritories(p.getName());
      ArrayList<String> territoryNames = new ArrayList<String> (tlist.keySet());
        p.out.writeObject(new ObjectIO(p.getName(), p.availableUnitNum, theMap, territoryNames));
        p.out.flush();
        p.out.reset();
        p.setNotReady();
    }
    //check all clients placement order are collected
    while (readyNum < numPlayers) {
      readyNum = 0;
      for (int i = 0; i < numPlayers; i++) {
        if (playerList.get(i).isReady()) {
          readyNum++;
        }
      }
    }
    //update theMap
    for (int i = 0; i < numPlayers; i++) {
      Player p = playerList.get(i);
      HashMap<String,Integer> po=p.tmp.placeOrders;
      for (String t : po.keySet()) {
        if (theMap.getTerritory(t).trySetNumUnits(po.get(t))) {
          System.out.println(p.getName() + " placed " + po.get(t) + " on territory " + t);
        }
      }
    }
    }
    
  /**
   *three loops which are similar to those in the doPlacement
   *difference:
   *need to check the Player isEnd or not before sending info to clients, if isEnd, set tmp.id=-1,let the client stop to collect action orders
   *a serverOrderHelper is used to check and execute orders
   */
  public void doOneTurn() throws IOException {
    int readyNum = 0;
    //sent info to clients
    for (int i = 0; i < numPlayers; i++) {
      Player p = playerList.get(i);
      HashMap<String, Territory> tlist = theMap.getPlayerTerritories(p.getName());
      if (tlist.size() == 0) {
        p.isEnd = true;
        p.ready = true;
      }
      if (!p.isEnd) {
        p.out.writeObject(new ObjectIO(p.getName(), i, theMap, playerNames));
        p.out.flush();
        p.out.reset();
        p.setNotReady();//set notready after writeObject,  and let the players thread readObject then set ready agian
      } else {
        p.out.writeObject(new ObjectIO(p.getName() + ", you are watching the game ", -1, theMap, playerNames));
        p.out.flush();
        p.out.reset();
      }
    }
    // wait until all clients done
    while (readyNum < numPlayers) {
      readyNum = 0;
      for (int i = 0; i < numPlayers; i++) {
        if (playerList.get(i).isReady()) {
          readyNum++;
        }
      }
    }
    // check and execute
    for (int i = 0; i < numPlayers; i++) {
      soh.collectOrders(playerList.get(i).tmp);
    }
    System.out.println("Move Error: " + soh.tryResolveMoveOrders(theMap));
    System.out.println("Attack Error: " + soh.tryResolveAttackOrders(theMap));
    soh.clearAllOrders();
  }
  /**
   *add 1 unit to each territory after one turn, also check the player isEnd
   */
  public void doRefresh() {
    for (int i = 0; i < numPlayers; i++) {
      Player p = playerList.get(i);
      if (!p.isEnd) {
        HashMap<String, Territory> tlist = theMap.getPlayerTerritories(p.getName());
        if (tlist.size() == 0) {
          p.isEnd = true;
          p.ready = true;
        } else {
          for (String tname : tlist.keySet()) {
            Territory t = tlist.get(tname);
            t.tryAddUnits(1);
            /*t.tryAddUnits(-2);
            if (t.getNumUnits() < 0) {
              t.tryAssignOwner("Player 1");
              } *///the setting is for quick check the game's result

          }
        }
      }
    }
  }
  /**
   *return true if there is only one player has territories
   *also send the winner message to each watching clients
   */
  public Boolean checkWinner() throws Exception {
    int count = 0;
    int winnerID = 0;
    for (int i = 0; i < numPlayers; i++) {
      Player p = playerList.get(i);
      if (!p.isEnd) {
        HashMap<String, Territory> tlist = theMap.getPlayerTerritories(p.getName());
        if (tlist.size() == 0) {
          p.isEnd = true;
          p.ready = true;
        } else {
          count++;
          winnerID=i;
        }
      }
    }
    //broadcast the winner message
    if (count == 1) {
      String info = "Winner is " + playerList.get(winnerID).getName();
      System.out.println(info);
      for (int i = 0; i < numPlayers; i++) {
        Player p = playerList.get(i);
        if (i == winnerID) {
          p.out.writeObject(new ObjectIO(info, -3, theMap, playerNames));//tmp.id=-3 indicates the player is the winner.
        }
        else {
          p.out.writeObject(new ObjectIO(info, -2, theMap, playerNames));//tmp.id=-2 indicates the client received thay need to print whe winner name.
        }
        p.out.flush();
        p.out.reset();
      }
      return true;
    }
    return false;
  }
  /** enter point of the server
   *new a App game and let the main thread to call methods in App class
   *when the game over, the server will exit normally. 
   */
  public static void main(String[] args) throws IOException {
    WorldMapFactory factory = new V1MapFactory();
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("Please set the server port number: (default is 3333 by hitting Enter)");
    String tmp = input.readLine();
    int portNumber = 0;
    if (tmp.equals("")) {
      portNumber = 3333;
    } else {
      portNumber = Integer.parseInt(tmp);
    }
    try (var listener = new ServerSocket(portNumber)) {
      System.out.println("The server is running...");
      App game = new App(listener, factory, input);
      game.acceptConnections();// player threads are created at here
      game.doInitialization();
      game.doPlacement();
      System.out.println("Initialization finished");
      while (true) {
        game.doOneTurn();
        game.doRefresh();
        if (game.checkWinner()) {
          System.exit(0);//server exits
        }
      }
    } catch (Exception e) {
    }
  }
}






