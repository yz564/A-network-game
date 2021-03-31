package edu.duke.ece651.risk.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import edu.duke.ece651.risk.shared.ObjectIO;

public class Room implements Runnable {
  private ServerSocket serverSocket;
  private int id;
  private int numPlayer;
  private volatile ArrayList<Player> playerList;
  private Game game;

  public Room(ServerSocket ss, int id, int numPlayer){
    this.serverSocket=ss;
    this.id=id;
    this.numPlayer=numPlayer;
    this.playerList = new ArrayList<Player>();
  }

  public Player acceptPlayer(User user) throws Exception{
    Player p = new Player(user.getName(),user.getIn(),user.getOut());
     playerList.add(p);
     Thread t = new Thread(p);
     t.start();
     return p;
  }

  public int getNum() {
    return numPlayer;
  }

  public int getCurrentNum() {
    return playerList.size();
  }
  
  @Override
  public void run(){
    while (true) {
      while (playerList.size() == numPlayer) {
        try {
          System.out.println("room "+(id+1)+ " begins a game");
          game = new Game(numPlayer, playerList);
          game.doInitialization();
          game.doPlacement();
          while (true) {
            game.doOneTurn();
            game.doRefresh();
            if (game.checkWinner()) {
              playerList.clear();
              break;
            }
          }
        } catch (Exception e) {
        }
      }
    }
  }

  
}

