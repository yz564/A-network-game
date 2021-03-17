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
    
	@Override
	public String readActionName() {
      String prompt = "Choose an action.\n" +
        "Enter 'm' (without the quotes) if you want to move troops.\n" +
        "Enter 'a' (without the quotes) if you want to attack a neighboring territory.\n";
      String reprompt = "Invalid choice of action. Retry!";
      String choice = "";
      while (choice != "move" || choice != "attack") {
        try {
          choice = readClientInput(prompt);
          if (choice == "m" || choice == "move") {
            choice = "move";
          }
          else if (choice == "a" || choice == "attack") {
            choice = "attack";
          }
          break;
        }
        catch (IOException ioe) {
        }
        out.println(reprompt);
      }
      return choice;
	}

	@Override
	public String readTerritoryName(WorldMap map) {
      String prompt = "Enter the name of the territory you want to attack: ";
      String reprompt = "Invalid territory name choice. Please try again.";
      String choice = "";
      while (true) {
        try {
          choice = readClientInput(prompt);
          break;
        }
        catch (IOException ioe) {
        }
        out.println(reprompt);
      }
      return choice.toLowerCase();
	}

	@Override
	public int readNumUnits() {
      String prompt = "Enter the number of units to move.";
      String reprompt = "Input is not a positive integer. Retry!";
      String choice = "";
      int numUnits;
      while (true) {
        try {
          choice = readClientInput(prompt);
          numUnits = Integer.parseInt(choice);
          if (numUnits > 0) {
            break;
          }
        }
        catch (NumberFormatException nfe) {
        }
        catch (IOException ioe) {
        }
        out.println(reprompt);
      }
      return numUnits;
	}

	@Override
	public void printMap(MapTextView view, WorldMap worldMap, ArrayList<String> playerNames) {
		// TODO Auto-generated method stub
		
	}

}













