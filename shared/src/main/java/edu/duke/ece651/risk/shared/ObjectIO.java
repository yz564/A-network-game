package edu.duke.ece651.risk.shared;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *In order to be used in writeObject, this class must implements serializable
 *All members also must be serializable
 */
public class ObjectIO implements java.io.Serializable{
  public String message;
  public int id;
  public WorldMap map;
  public HashSet<Integer> groups;
  public ArrayList<String> playerNames;
  
  public ObjectIO(){
    this.message="";
    this.id = 0;
    this.map=null;
    this.groups = new HashSet<Integer>();
    this.playerNames = new ArrayList<String>();
  }
  
  public ObjectIO(String message){
    this.message=message;
    this.id = 0;
    this.map=null;
    this.groups = new HashSet<Integer>();
    this.playerNames = new ArrayList<String>();
  }

  public ObjectIO(String message, int num){
    this.message=message;
    this.id = num;
    this.map=null;
    this.groups = new HashSet<Integer>();
    this.playerNames = new ArrayList<String>();
  }

  public ObjectIO(String message, int id, WorldMap map){
    this.message=message;
    this.id = id;
    this.map=map;
    this.groups = new HashSet<Integer>();
    this.playerNames = new ArrayList<String>();
  }

  public ObjectIO(String message, int id, WorldMap map, HashSet<Integer> groups){
    this.message=message;
    this.id = id;
    this.map=map;
    this.groups = groups;
    this.playerNames = new ArrayList<String>();
  }
  public ObjectIO(String message, int id, WorldMap map, ArrayList<String> name){
    this.message=message;
    this.id = id;
    this.map=map;
    this.groups = new HashSet<Integer>();
    this.playerNames = name;
  }
}







