package edu.duke.ece651.risk.shared;

import java.util.HashMap;

public interface V2Territory extends java.io.Serializable {
    /**
     * Gets the production rate HashMap of the territory.
     *
     * @return a HashMap that is the production rate of different types of resources. HashMap key is
     *     * the resource name, and value is the production rate.
     */
    public HashMap<String, Integer> getResProduction();

    /**
     * Adds units to the territory.
     *
     * @param toAdd is a HashMap with String keys are the Troops to add units and Integer values are
     *     the number of units to add to the Troop.
     */
    public void addUnits(HashMap<String, Integer> toAdd);

    /**
     * Removes units from the territory.
     *
     * @param toRemove is a HashMap with String keys are the Troops to remove units and Integer
     *     values are the number of units to remove from the Troop.
     */
    public void removeUnits(HashMap<String, Integer> toRemove);

    /**
     * Returns the total number of units of each Troop inside a territory.
     *
     * @return a HashMap<String, Integer> with keys are Troop names, and values are number of units
     *     in the corresponding troop.
     */
    public HashMap<String, Integer> getAllNumUnits();

    /**
     * Gets the number of units in a specific troop with the troop name given.
     *
     * @return an int represents the number of units in the given troop.
     */
    public int getTroopNumUnits(String troopName);

    /**
     * Returns territory name.
     *
     * @return a String that represents the territory's name.
     */
    public String getName();

    /**
     * Returns the troops in a territory.
     *
     * @return a HashMap with keys are troop name, and values are number of units in the troop.
     */
    public HashMap<String, Troop> getMyTroops();

    /**
     * Sets number of units present in a territory.
     *
     * @param toSet is a HashMap with String keys are the Troops to set units and Integer values are
     *     the number of units to set to the Troop.
     */
    public void setNumUnits(HashMap<String, Integer> toSet);

    /**
     * Checks if territory is adjacent to a given territory.
     *
     * @param neighbor is the territory to check adjacency with.
     * @return true if the two territories are adjacent, false if not.
     */
    public boolean isAdjacentTo(V2Territory neighbor);

    /**
     * Add a given territory to the list of neghboring territories of the current territory.
     *
     * @param neighbor is the territory to add.
     * @return true if the add is successful, false if not.
     */
    public boolean tryAddNeighbor(V2Territory neighbor);

    /**
     * Returns the name of the owner of the current territory.
     *
     * @return a String that represents the owner's name of the territory.
     */
    public String getOwnerName();

    /**
     * Check if territory belongs to a given player.
     *
     * @param playerName is the name of the player to check ownership with
     * @return true if the two territories are adjacent, false if not.
     */
    public boolean isBelongTo(String playerName);

    /**
     * Assigns the owner of this territory to a given player name.
     *
     * @param playerName is the name of the owner to assign territory.
     */
    public void putOwnerName(String playerName);

    /**
     * Get the neighbors of the Territory.
     *
     * @return a HashMap of Territory, which are neighbors of this Territory mapped to their names.
     */
    public HashMap<String, V2Territory> getMyNeighbors();

    /**
     * Check if a territory is reachable from the current territory
     *
     * @param toReach is the territory to check for a path of adjacent territories that belong to
     *     the same player.
     * @return true if reachable, false if not.
     */
    public boolean isReachableTo(V2Territory toReach);
}
