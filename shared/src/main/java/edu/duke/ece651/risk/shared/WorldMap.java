package edu.duke.ece651.risk.shared;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents an interface for classes that would 
 * represent the world map in a RISK game.
 *
 * The World Map contains a number of territories and 
 * the initial grouping of territories for a given number of players.
 */
public interface WorldMap extends java.io.Serializable{

  /**
   * Return the territory on map with the given name
   * 
   * @param name is the name of the territory to retrieve
   */
  public Territory getTerritory(String name);

  /**
   * Adds to a territory to an initial grouping
   * 
   * @param toAdd is the name of the territory to add
   * @param group is the initial group number to add to
   * 
   * @return true if add is successful, false if not.
   */
  public boolean tryAddInitGroup(int group, String toAdd);

  /**
   * Returns a list of territory names in a given initial group
   * 
   * @param group is the initial grouping number to retrieve from
   */
  public ArrayList<String> getInitGroup(int group);

  /**
   * Assigns the owner of a given initial grouping of territories
   * 
   * @param group is the initial grouping number
   * @param playerName is the name of the owner  
   * 
   * @return true if assignment is successful, false if not.
   */
  public boolean tryAssignInitOwner(int group, String playerName);

  /**
   * Returns a hashmap of territories mapped to its name belonging to a given player
   * 
   * @param playerName is the name of the owner  
   */
  public HashMap<String, Territory> getPlayerTerritories(String playerName);

  /**
   * Change the owner of a given territory
   * 
   * @param territoryName is the name of the territory to change ownership
   * @param playerName is the name of the owner  
   * 
   * @return true if change is successful, false if not.
   */
  public boolean tryChangeOwner(String territoryName, String playerName);
}











