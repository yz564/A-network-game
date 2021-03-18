package edu.duke.ece651.risk.client;

import java.util.ArrayList;
import java.io.IOException;

import edu.duke.ece651.risk.shared.WorldMap;

/* Responsible for input and output exchange for a player.
 */
public interface ClientIO {

  /* Read an action (either move, attack or done) from a player.
   *  
   * @param playerName is the name of the player who will be 
   * prompted for action.  
   *  
   * @returns A string which is either:
   * "M" if a player wants to Move units from their current territory to some 
   * other own territory.
   * "A" if player wants to Attack enemy territory.
   * "D" if player is Done and does not want to either Move or Attack.
   *  
   * @returns "M", "A" or "D" depending on players choice.  
   */
  public String readActionName(String playerName);

  /* Read a territory name from a player.
   * @param prompt is the text message that a user sees asking to input 
   * territory name.
   * @returns territory name as a String.
   */
  public String readTerritoryName(String prompt);

  /* Read the number of units that a player wants to move.
   * @returns an integer inputted by a player.
   */
  public int readNumUnits(String prompt);

  /* Print a textual represntation of the RISK map.
   * @param view computes the string that represents
   * RISK map.
   * @param worldMap is the object that represents RISK map.
   * @param playerNames is the list of players playing the game. 
   */
  public void printMap(MapTextView view, WorldMap worldMap, ArrayList<String> playerNames);
}

