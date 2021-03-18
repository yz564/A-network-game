package edu.duke.ece651.risk.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import edu.duke.ece651.risk.shared.ObjectIO;

/**
 * client: the socket connected with the client
 * name: the player's name
 * isEnd to chech is the player lose 
 * in and out are ObjectIOstream
 * ready is important to control the player's thread, basic logic is writeObject once and then must readObject once...because of the blocking issue
 * availableUnitNum: the total unit number a player have at the beginning of placement
 * watch: a status indicates whether sending message to clients or not 
 * tmp: used to store the most recently ObjectIO read in the player's thread.
 */
public class Player implements Runnable{
  private Socket client;
  private String name;
  public volatile Boolean isEnd;
  public ObjectInputStream in;
  public ObjectOutputStream out;
  public volatile Boolean ready; //volatile: always updates this variable before being accessed by other threads
  public volatile int availableUnitNum;
  public Boolean watch;
  public ObjectIO tmp;
  /**
   * A simple constructor
   */
  public Player(Socket client,String name){
    this.client=client;
    this.name = name;
    this.isEnd=false;
    this.ready = false;
    this.availableUnitNum=30;
  }

  public String getName() {
    return name;
  }
  public Boolean isReady() {
    return ready;
  }

  public void setNotReady(){
      this.ready=false;
  }
  /**
   *must override run(), otherwise the thread will terminate soon.
   */
  @Override
  public void run(){
    try{
    this.in=new ObjectInputStream(client.getInputStream());
    this.out=new ObjectOutputStream(client.getOutputStream());
    }
    catch (IOException e) {
    }
    while(!isEnd){
      try{
        System.out.println("-----waitClientInput------");
        if ((tmp=(ObjectIO)in.readObject())!=null){ //blocked when waiting for a Object sent from the client
          System.out.println("-----ReadClientInput------");
          ready=true; //let the main thread know this player's thread is ready for sending next object.
        }
      }
      catch(Exception e){}
    }
    
    while (watch) {
    }
  }
}











