package edu.duke.ece651.risk.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * In order to be used in writeObject, this class must implements serializable
 * All members also must be serializable
 */
public class ObjectIO implements java.io.Serializable {

  private static final long serialVersionUID = -5874503891285235624L;
  public String message;
  public HashMap<String, String> userTextInputs;
  public int id;
  public WorldMap map;
  public HashSet<Integer> groups;
  public ArrayList<String> playerNames;
  public ArrayList<ActionInfo> attackOrders;
  public ArrayList<ActionInfo> moveOrders;
  /**
   * Orders to place initial units at the beginning of the game. HashMap has
   * String key represents the territory name to palce units. Integer vakue
   * reoresents the number of units to place on the key territory.
   */
  public HashMap<String, Integer> placeOrders;

  public ObjectIO() {
    this.message = "";
    this.id = 0;
    this.map = null;
    this.groups = new HashSet<Integer>();
    this.playerNames = new ArrayList<String>();
    this.attackOrders = new ArrayList<ActionInfo>();
    this.moveOrders = new ArrayList<ActionInfo>();
    this.placeOrders = new HashMap<String, Integer>();
  }

  public ObjectIO(String message) {
    this.message = message;
    this.id = 0;
    this.map = null;
    this.groups = new HashSet<Integer>();
    this.playerNames = new ArrayList<String>();
  }

  public ObjectIO(HashMap<String, String> inputs) {
    this.message = null;
    this.userTextInputs = inputs;
    this.id = 0;
    this.map = null;
    this.groups = new HashSet<Integer>();
    this.playerNames = new ArrayList<String>();
  }

  public ObjectIO(String message, int num) {
    this.message = message;
    this.id = num;
    this.map = null;
    this.groups = new HashSet<Integer>();
    this.playerNames = new ArrayList<String>();
  }

  public ObjectIO(String message, int id, WorldMap map) {
    this.message = message;
    this.id = id;
    this.map = map;
    this.groups = new HashSet<Integer>();
    this.playerNames = new ArrayList<String>();
  }

  public ObjectIO(String message, int id, WorldMap map, HashSet<Integer> groups) {
    this.message = message;
    this.id = id;
    this.map = map;
    this.groups = groups;
    this.playerNames = new ArrayList<String>();
  }

  public ObjectIO(String message, int id, WorldMap map, ArrayList<String> name) {
    this.message = message;
    this.id = id;
    this.map = map;
    this.groups = new HashSet<Integer>();
    this.playerNames = name;
  }
}
