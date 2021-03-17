package edu.duke.ece651.risk.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import edu.duke.ece651.risk.shared.ObjectIO;

public class Player implements Runnable{
  private Socket client;
  private int id;
  private String name;
  private Boolean isEnd;
  public ObjectInputStream in;
  public ObjectOutputStream out;
  private volatile Boolean ready; //volatile: always updates this variable before being accessed by other threads
  public volatile int availableUnitNum;
  public volatile int unitNum;
  public ObjectIO tmp;

  public Player(Socket client, int id,String name){
    this.client=client;
    this.id=id;
    this.name = name;
    this.isEnd=false;
    this.ready = false;
    this.availableUnitNum=30;
    this.unitNum = 1;
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
        if ((tmp=(ObjectIO)in.readObject())!=null){
          System.out.println("-----ReadClientInput-----");
          ready=true;
        }
      }
      catch(Exception e){}
    }
  }
}






