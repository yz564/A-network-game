package edu.duke.ece651.risk.shared;

public interface Territory {
  /*
   * Represents an interface for classes that would 
   * represent territories in a RISK Map.
   */

  /*
   * Adds a unit (e.g. a soldier) to the territory.
   *
   * @returns true if add is successful, false if not.
   */
  boolean tryAddUnit();

  /*
   * Remove a unit (e.g. a soldier) from the territory.
   * 
   * @returns true if unit is removed is successful, false if not.
   */
  boolean tryRemoveUnit();

  /*
   * Returns whether or not an arbitrary territory shares a boundary 
   * with this territory (aka neighbor). 
   *
   * @param otherTerritory is the territory that we want to know whether or 
   * not shares a boundary with this territorythe neighobor.
   *
   * @param map is the map that contains otherTerritory.
   *
   * @returns true if otherTerritory shares a boundary with this territory, 
   * false if not.
   */
  boolean isAdjacent(Territory otherTerritory, WorldMap map);  
}
