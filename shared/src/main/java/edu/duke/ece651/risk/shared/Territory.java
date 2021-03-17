package edu.duke.ece651.risk.shared;

import java.util.HashMap;

/**
 * Represents an interface for classes that would represent territories in a
 * RISK Map.
 *
 * Each Territory contains a number of units that could be used to either attack
 * or defend in a combat.
 */
public interface Territory {
  /**
   * Adds units to the territory.
   * 
   * @param toAdd is the number of units to add.
   * 
   * @return true if add is successful, false if not.
   */
  public boolean tryAddUnits(int toAdd);

  /**
   * Remove units from the territory.
   * 
   * @param toRemove is the number of units to remove.
   *
   * @return true if unit is removed is successful, false if not.
   */
  public boolean tryRemoveUnits(int toRemove);

  /**
   * Returns the total number of units inside a territory.
   */
  public int getNumUnits();

  /**
   * Returns territory name.
   */
  public String getName();

  /**
   * Set number of units present in a territory.
   * 
   * @param numUnits is the number of units that is set for this terriroty..
   */
  public boolean trySetNumUnits(int numUnits);

  /**
   * Check if territory is adjacent to a given territory
   * 
   * @param neighbor is the territory to check adjacency with
   * 
   * @return true if the two territories are adjacent, false if not.
   */
  public boolean isAdjacentTo(Territory neighbor);

  /**
   * Add a given territory to the list of neghboring territories of the current
   * territory
   * 
   * @param neighbor is the territory to add
   * 
   * @return true if the add is successful, false if not.
   */
  public boolean tryAddNeighbor(Territory neighbor);

  /**
   * Returns the name of the owner of the current territory.
   */
  public String getOwnerName();

  /**
   * Check if territory belongs to a given player
   * 
   * @param playerName is the name of the player to check ownership with
   * 
   * @return true if the two territories are adjacent, false if not.
   */
  public boolean isBelongTo(String playerName);

  /**
   * Assigns the owner of the current territory to a given player name
   * 
   * @param playerName is the name of the owner
   *
   * @return true if assignment is succesfful, false if not
   */
  public boolean tryAssignOwner(String playerName);

  /**
   * Get the neighbors of the Territory.
   * 
   * @return a HashMap of Territory, which are neighbors of this Territory mapped
   *         to their names.
   */
  public HashMap<String, Territory> getMyNeighbors();

  /**
   * Check if a territory is reachable from the current territory
   * 
   * @param toReach is the territory to check for a path of adjacent territories
   *                that belong to the same player.
   * @return true if reachable, false if not.
   */
  public boolean isReachableTo(Territory toReach);
}













