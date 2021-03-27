package edu.duke.ece651.risk.shared;

import java.util.HashMap;

public interface Territory extends java.io.Serializable {

  /**
   * Returns territory name.
   *
   * @return a String that represents the territory's name.
   */
  public String getName();

  /**
   * Returns the name of the owner of the current territory.
   *
   * @return a String that represents the owner's name of the territory.
   */
  public String getOwnerName();

  /**
   * Sets the owner of this territory to a given player name.
   *
   * @param playerName is the name of the owner to assign territory.
   */
  public void setOwnerName(String playerName);

  /**
   * Check if territory belongs to a given player.
   *
   * @param playerName is the name of the player to check ownership with
   * @return true if the two territories are adjacent, false if not.
   */
  public boolean isBelongTo(String playerName);

  /**
   * Get the neighbors of the Territory.
   *
   * @return a HashMap of Territory, which are neighbors of this Territory mapped
   *         to their names.
   */
  public HashMap<String, Territory> getMyNeighbors();

  /**
   * Add a given territory to the list of neghboring territories of the current
   * territory.
   *
   * @param neighbor is the territory to add.
   * @return true if the add is successful, false if not.
   */
  public boolean tryAddNeighbor(Territory neighbor);

  /**
   * Checks if territory is adjacent to a given territory.
   *
   * @param neighbor is the territory to check adjacency with.
   * @return true if the two territories are adjacent, false if not.
   */
  public boolean isAdjacentTo(Territory neighbor);

  /**
   * Check if a territory is reachable from the current territory
   *
   * @param toReach is the territory to check for a path of adjacent territories
   *                that belong to the same player.
   * @return true if reachable, false if not.
   */
  public boolean isReachableTo(Territory toReach);

  /**
   * Returns the troops in a territory.
   *
   * @return a HashMap with keys are the troop names, and values are the Troop
   *         objects.
   */
  public HashMap<String, Troop> getMyTroops();

  /**
   * Gets a specific troop with the troop name given.
   *
   * @return a Troop object.
   */
  public Troop getTroop(String troopName);

  /**
   * Gets the number of units in a specific troop with the troop name given.
   *
   * @return an int represents the number of units in the given troop.
   */
  public int getTroopNumUnits(String troopName);

  /**
   * Adds units to a specific troop in the territory.
   *
   * @param troopName is the name of the troop to add units to.
   * @param addNum    is the number of units to add to the given troop.
   * @return true if adding is successful, else return false.
   */
  public boolean tryAddTroopUnits(String troopName, int addNum);

  /**
   * Removes units from a specific troop in the territory.
   *
   * @param troopName is the name of the troop to remove units from.
   * @param removeNum is the number of units to remove to the given troop.
   * @return true if removal is successful, else return false.
   */
  public boolean tryRemoveTroopUnits(String troopName, int removeNum);

  /**
   * Sets the number of units to a specific troop in the territory.
   *
   * @param troopName is the name of the troop to set units.
   * @param setNum is the number of units to set to the given troop.
   * @return true if setting is successful, else return false.
   */

  public boolean trySetTroopUnits(String troopName, int setNum);

  /**
   * Sets number of units of troops in the territory. Checks if all number of
   * units are valid before setting.
   *
   * @param toSet is a HashMap with String keys are the Troops to set units and
   *              Integer values are the number of units to set to the Troop.
   * @return true if setting is successful is valid for all Troops, else return
   *         false and leave all Troops unchanged.
   */
  public boolean trySetNumUnits(HashMap<String, Integer> toSet);

  /**
   * Returns the total number of units of each Troop inside a territory.
   *
   * @return a HashMap<String, Integer> with keys are Troop names, and values are
   *         number of units in the corresponding troop.
   */
  public HashMap<String, Integer> getAllNumUnits();

  /**
   * Gets the size (food cost to move through) this territory.
   *
   * @return an int that represents the size of the territory.
   */
  public int getSize();

  /**
   * Gets the production rate HashMap of the territory.
   *
   * @return a HashMap that is the production rate of different types of
   *         resources. HashMap key is * the resource name, and value is the
   *         production rate.
   */
  public HashMap<String, Integer> getResProduction();
}
