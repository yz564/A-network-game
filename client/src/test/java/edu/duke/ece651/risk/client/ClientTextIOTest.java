package edu.duke.ece651.risk.client;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.PrintStream;
import java.io.Reader;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.duke.ece651.risk.shared.V1MapFactory;
import edu.duke.ece651.risk.shared.WorldMap;
import edu.duke.ece651.risk.shared.WorldMapFactory;

public class ClientTextIOTest {

  public ClientTextIO getClientTextIOObject(String input, ByteArrayOutputStream bytes) {
    BufferedReader inputReader = new BufferedReader(new StringReader(input));
    PrintStream out = new PrintStream(bytes, true);
    ClientTextIO ctio = new ClientTextIO(inputReader, out);
    return ctio;
  }

  @Test
  public void test_read_action_name() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    String move = "m\n";
    String attack = "a\n";
    String done = "d\n";
    String nullAction = "";
    ClientTextIO ctio_move = getClientTextIOObject(move, bytes);
    bytes.reset();
    ClientTextIO ctio_attack = getClientTextIOObject(attack, bytes);
    bytes.reset();
    ClientTextIO ctio_done = getClientTextIOObject(done, bytes);
    bytes.reset();
    assertEquals("M", ctio_move.readActionName("Green"));
    assertEquals("A", ctio_attack.readActionName("Blue"));
    assertEquals("D", ctio_done.readActionName("Red"));
  }

  @Test
  public void test_read_action_error_handling() {
    String prompt = "You are the Green player. What would you like to do?\n"
      + "(M)ove\n"
      + "(A)ttack\n"
      + "(D)one\n";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    ClientTextIO ctio = getClientTextIOObject("f\na\n", bytes);
    ctio.readActionName("Green");
    String expected = prompt + "\n"
      + "Action must either be \"M\" for move, \"A\" for attack or \"D\" for done.\n\n"
      + "Invalid choice of action. Retry!\n" + prompt + "\n";
    assertEquals(expected, bytes.toString());
  }
  
  @Test
  public void test_read_territory_name() {
    String territoryName = "Narnia";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    ClientTextIO ctio = getClientTextIOObject(territoryName, bytes);
    assertEquals(territoryName, ctio.readTerritoryName("Enter territory name:\n"));
    
    BufferedReader inputReader = new BufferedReader(new StringReader("Haryana"));
    bytes.reset();
    PrintStream output = new PrintStream(bytes, true);
    ClientTextIO ctio2 = new ClientTextIO(inputReader, output);
    //assertEquals("Invalid territory name.", bytes.toString());
    assertEquals("Haryana", ctio2.readTerritoryName("Enter territory name:\n"));
  }


  @Test
  public void test_read_num_units() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    ClientTextIO ctio = getClientTextIOObject("5", bytes);
    assertEquals(5, ctio.readNumUnits("Enter number of units:\n"));

    ctio = getClientTextIOObject("-3\n2", bytes);
    assertEquals(2, ctio.readNumUnits("Enter number of units:\n"));

    ctio = getClientTextIOObject("-3\n0\n-2\n-100\n5\n", bytes);
    assertEquals(5, ctio.readNumUnits("Enter number of units:\n"));

    ctio = getClientTextIOObject("abcd\n-3\n4", bytes);
    assertEquals(4, ctio.readNumUnits("Enter number of units:\n"));

    ctio = getClientTextIOObject("6abcd\n8", bytes);
    assertEquals(8, ctio.readNumUnits("Enter number of units:\n"));

    ctio = getClientTextIOObject("\n\n\n204", bytes);
    assertEquals(204, ctio.readNumUnits("Enter number of units:\n"));
  }

  @Test
  public void test_print_map() {
    BufferedReader inputReader = new BufferedReader(new StringReader("Random input"));
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes, true);
    ClientTextIO ctio = new ClientTextIO(inputReader, output);

    ArrayList<String> playerNames = new ArrayList<String>();
    playerNames.add("Green player");
    playerNames.add("Blue player");
    playerNames.add("Red player");
    MapTextView view = new MapTextView(playerNames);

    WorldMapFactory mf = new V1MapFactory();
    WorldMap map = mf.makeTestWorldMap();

    map.tryAssignInitOwner(1, playerNames.get(0));
    map.tryAssignInitOwner(2, playerNames.get(1));
    map.tryAssignInitOwner(3, playerNames.get(2));

    String expected = "";
    expected = expected + "Green player:\n" + "-------------\n"
        + "    0 units in Narnia (next to: Elantris, Midkemia)\n"
        + "    0 units in Midkemia (next to: Elantris, Narnia, Oz, Roshar)\n"
        + "    0 units in Oz (next to: Mordor, Scadrial, Midkemia, Gondor)\n";
    expected = expected + "Blue player:\n" + "-------------\n"
        + "    0 units in Elantris (next to: Narnia, Scadrial, Midkemia, Roshar)\n"
        + "    0 units in Scadrial (next to: Elantris, Mordor, Hogwarts, Midkemia, Oz, Roshar)\n"
        + "    0 units in Roshar (next to: Elantris, Hogwarts, Scadrial)\n";
    expected = expected + "Red player:\n" + "-------------\n"
        + "    0 units in Mordor (next to: Hogwarts, Scadrial, Gondor, Oz)\n"
        + "    0 units in Hogwarts (next to: Mordor, Scadrial, Roshar)\n"
        + "    0 units in Gondor (next to: Mordor, Oz)\n";
    expected = expected + "\n";
    ctio.printMap(view, map, playerNames);
    assertEquals(expected, bytes.toString());
  }
}
