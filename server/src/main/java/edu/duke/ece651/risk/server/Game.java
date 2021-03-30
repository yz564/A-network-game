package edu.duke.ece651.risk.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import edu.duke.ece651.risk.shared.ObjectIO;
import edu.duke.ece651.risk.shared.Territory;
import edu.duke.ece651.risk.shared.V1MapFactory;
import edu.duke.ece651.risk.shared.WorldMap;
import edu.duke.ece651.risk.shared.WorldMapFactory;


public class Game {
  private int numPlayers;
  private volatile ArrayList<Player> playerList;
  private WorldMapFactory factory;
  private volatile WorldMap theMap;
  private volatile ArrayList<String> playerNames;
  private volatile HashSet<Integer> availableGroups;
  private ServerOrderHelper soh;
  /**
   *a simple constructor
   */
  public Game(int num, ArrayList<Player> playerList) {
    this.numPlayers=num;
    this.playerList = playerList;
    this.playerNames = new ArrayList<String>();
    this.availableGroups = new HashSet<Integer>();
    for(int i=0; i<num; i++){
      playerNames.add(playerList.get(i).getName());
      availableGroups.add(i + 1);
    }
    this.factory = new V1MapFactory();
    this.theMap=factory.makeWorldMap(numPlayers);
    this.soh = new ServerOrderHelper();
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
        if (theMap.getTerritory(t).trySetTroopUnits("Basic",po.get(t))) {
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
            t.tryAddTroopUnits("Basic",1);
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

}

