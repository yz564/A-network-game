package edu.duke.ece651.risk.shared;

import java.util.HashMap;

/**
 * @author group7
 */
public interface V2Territory extends java.io.Serializable {
    /**
     * Adds units to the territory.
     *
     * @param toAdd is a HashMap with String keys are the Troops to add units and Integer values are
     *              the number of units to add to the Troop.
     * @return true if add is successful, false if not.
     */
    public boolean tryAddUnits(HashMap<String, Integer> toAdd);

    /**
     * Removes units from the territory.
     *
     * @param toRemove is a HashMap with String keys are the Troops to remove units and Integer values
     *                 are the number of units to remove from the Troop.
     * @return true if unit is removed is successful, false if not.
     */
    public boolean tryRemoveUnits(HashMap<String, Integer> toRemove);

    /**
     * Returns the total number of units of each Troop inside a territory.
     *
     * @return a HashMap<String, Integer> with keys are Troop names, and values are number of units in
     * the corresponding troop.
     */
    public HashMap<String, Integer> getNumUnits();

    /**
     * Returns territory name.
     *
     * @return a String that represents the territory's name.
     */
    public String getName();

    /**
     * Sets number of units present in a territory.
     *
     * @param toSet is a HashMap with String keys are the Troops to set units and Integer values
     *              are the number of units to set to the Troop.
     * @return true if the units are successfully set, false otherwise.
     */
    public boolean trySetNumUnits(HashMap<String, Integer> toSet);

    /**
     * Checks if territory is adjacent to a given territory.
     *
     * @param neighbor is the territory to check adjacency with.
     * @return true if the two territories are adjacent, false if not.
     */
    public boolean isAdjacentTo(Territory neighbor);

    /**
     * Add a given territory to the list of neghboring territories of the current territory.
     *
     * @param neighbor is the territory to add.
     * @return true if the add is successful, false if not.
     */
    public boolean tryAddNeighbor(Territory neighbor);

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
     * @return true if assignment is successful, false if not.
     */
    public boolean tryAssignOwner(String playerName);

    /**
     * Get the neighbors of the Territory.
     *
     * @return a HashMap of Territory, which are neighbors of this Territory mapped to their names.
     */
    public HashMap<String, Territory> getMyNeighbors();

    /**
     * Check if a territory is reachable from the current territory
     *
     * @param toReach is the territory to check for a path of adjacent territories that belong to the
     *                same player.
     * @return true if reachable, false if not.
     */
    public boolean isReachableTo(Territory toReach);
}
