package edu.duke.ece651.risk.shared;

import java.util.ArrayList;

/*
 * Represents an interface for classes that would 
 * represent the world map in a RISK game.
 *
 * The World Map contains a number of territories and 
 * the initial grouping of territories for a given number of players.
 */
public interface WorldMap {

  /*
   * Adds a new territory with a given name on the world map.
   * Territory is created with no troops, neighbors or owner.
   * 
   * @param toAdd is the name of the territory to add
   */
  public void tryAddTerritory(String toAdd);

  /*
   * Returns the territory on map with the given name
   * 
   * @param name is the name of the territory to retrieve
   */
  public Territory getTerritory(String name);

  /*
   * Adds to a territory to an initial grouping
   * 
   * @param toAdd is the name of the territory to add
   * @param group is the initial group number to add to
   * 
   * @returns true if add is successful, false if not.
   */
  public void tryAddInitGroup(int group, String toAdd);

  /*
   * Returns a list of territory names in a given initial group
   * 
   * @param group is the initial grouping number to retrieve from
   */
  public ArrayList<String> getInitGroup(int group);

  /*
   * Assigns the owner of a given initial grouping of territories
   * 
   * @param group is the initial grouping number
   * @param playerName is the name of the owner  
   */
  public void tryAssignInitOwner(int group, String playerName);
}
