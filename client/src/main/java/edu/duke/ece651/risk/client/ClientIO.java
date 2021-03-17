package edu.duke.ece651.risk.client;

import java.util.ArrayList;
import java.io.IOException;

import edu.duke.ece651.risk.shared.WorldMap;

/* Responsible for input and output exchange for a player.
 */
public interface ClientIO {

  /* Read an action (either move or attack) from a player.
   * @returns A string which is either "move" if player wants to move units 
   * to one of their other territories, or "attack" if player wants to attack 
   * an enemy territory.
   */
  public String readActionName();

  /* Read a territory name from a player.
   * @param map is the map that must contain the territory input 
   * by the player.
   * @returns A string containing territory name.
   */
  public String readTerritoryName(WorldMap map);

  /* Read the number of units that a player wants to move.
   * @returns an integer inputted by a player.
   */
  public int readNumUnits();

  /* Print a textual represntation of the RISK map.
   * @param view computes the string that represents
   * RISK map.
   * @param worldMap is the object that represents RISK map.
   * @param playerNames is the list of players playing the game. 
   */
  public void printMap(MapTextView view, WorldMap worldMap, ArrayList<String> playerNames);
}













