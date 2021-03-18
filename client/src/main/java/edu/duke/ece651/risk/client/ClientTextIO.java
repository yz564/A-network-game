package edu.duke.ece651.risk.client;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;

import edu.duke.ece651.risk.shared.WorldMap;

public class ClientTextIO implements ClientIO {
  private final BufferedReader inputReader;
  private final PrintStream out;

  /* Constructs ClientTextIO object
   * @param inputReader is the stream that takes input from a player.
   * @param out is the stream that prints out text to a player.    
   */
  public ClientTextIO (BufferedReader inputReader, PrintStream out) {
    this.inputReader = inputReader;
    this.out = out;
  }
    
  
  /* Reads input from a player.
   * @param prompt is the string outputted to the user asking for an input.  
   * @returns user input as a String object.
   */
  private String readClientInput(String prompt) throws IOException {
    out.println(prompt);
    String clientInput = inputReader.readLine();
    return clientInput;
  }

  /* Whether or not an action choice inputted by user is a valid action.
   * @param choice is a string inputted by player in order to select an action.
   * @returns true is choice is either "M" for move, "A" for attack, or "D"
   * for done, otherwise false.  
   */
  boolean isValidAction(String choice) {
    return choice.equals("M") || choice.equals("A") || choice.equals("D");
  }
  
	@Override
	public String readActionName(String playerName) {
      String prompt = "You are the " + playerName +
        " player. What would you like to do?\n" +
        "(M)ove\n" +
        "(A)ttack\n" +
        "(D)one\n";
      String choice = "";
      while (true) {
        try {
          choice = readClientInput(prompt).toUpperCase();
          if (isValidAction(choice)) {
            break;
          }
          else {
            throw new IOException("Action must either be \"M\" for move, \"A\" for attack or \"D\" for done.\n");
          }
        }
        catch (IOException ioe) {
          out.println(ioe.getMessage());
        }
        catch (NullPointerException npe) {
          out.println(npe.getMessage());
        }
        out.println("Invalid choice of action. Retry!");
      }
      return choice;
	}

	@Override
	public String readTerritoryName(String prompt) {
      String choice = "";
      while (true) {
        try {
          choice = readClientInput(prompt);
          out.println("Entered territory name is `"+ choice + "`\n");
          if (choice == null) {
            throw new IOException("Invalid territory name.");
          }
          break;
        }
        catch (IOException ioe) {
          out.println(ioe.getMessage());
        }
        catch (NullPointerException npe) {
          out.println(npe.getMessage());
        }
      }
      return choice;
	}

	@Override
	public int readNumUnits(String prompt) {
      String choice = "";
      int numUnits = 0;
      while (true) {
        try {
          choice = readClientInput(prompt);
          numUnits = Integer.parseInt(choice);
          if (numUnits > 0) {
            break;
          }
          else {
            throw new IOException("Number of units must be positive!\n");
          }
        }
        catch (NumberFormatException nfe) {
          out.println(nfe.getMessage());
        }
        catch (IOException ioe) {
          out.println(ioe.getMessage());
        }
        catch (NullPointerException npe) {
          out.println(npe.getMessage());
        }
        out.println("Input is not a positive integer. Retry!");
      }
      return numUnits;
	}

	@Override
	public void printMap(MapTextView view, WorldMap worldMap, ArrayList<String> playerNames) {
      MapTextView textView = new MapTextView(playerNames);
      out.println(textView.displayMap(worldMap));
	}

}
