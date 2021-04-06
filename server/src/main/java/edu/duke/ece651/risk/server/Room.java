package edu.duke.ece651.risk.server;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Room implements Runnable {
  //private ServerSocket serverSocket;
  private int id;
  private int numPlayer;
  private volatile ArrayList<Player> playerList;
  private Game game;
  private Thread worker;
  private final AtomicBoolean running=new AtomicBoolean(false);
  private int interval = 100;
 
  public Room( int id, int numPlayer){
    //this.serverSocket=ss;
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

  /*
  public void start() {
    worker = new Thread(this);
    worker.start();
  }
  */
  public void stop() {
    playerList.clear();
    running.set(false);
  }
  
  @Override
  public void run(){
    System.out.println("a new room thread is running");
    while (true) {
      running.set(true);
        try{
          while (running.get() && playerList.size() == numPlayer) {
          System.out.println("room "+(id+1)+ " begins a game");
          game = new Game(numPlayer, playerList);
          game.doInitialization();
          game.doPlacement();
          while (true) {
            game.doOneTurn();
            game.doRefresh();
            if (game.checkWinner()) {
              playerList.clear();
              System.out.println("Room "+(id+1)+" game over, all player quit the room");
              break;
            }
          }
      }
    
    } catch (Exception e) {
Thread.currentThread().interrupt();
          System.out.println("Room "+(id+1)+" accidently game over, because an user force quit the game");
          playerList.clear();
        }
      }
  }

  
}










