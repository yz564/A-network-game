package edu.duke.ece651.risk.client;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.PrintStream;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import edu.duke.ece651.risk.shared.V1MapFactory;
import edu.duke.ece651.risk.shared.WorldMap;
import edu.duke.ece651.risk.shared.WorldMapFactory;

public class ClientTextIOTest {

  public ClientTextIO getClientTextIOObject(String input) {
    BufferedReader inputReader = new BufferedReader(new StringReader(input));
    ClientTextIO ctio = new ClientTextIO(inputReader, System.out);
    return ctio;
  }

  @Test
  public void test_read_action_name() {
    String move = "m\n";
    String attack = "a\n";
    String done = "d\n";
    ClientTextIO ctio_move = getClientTextIOObject(move);
    ClientTextIO ctio_attack = getClientTextIOObject(attack);
    ClientTextIO ctio_done = getClientTextIOObject(done);
    String prompt = "Hi!";
    assertEquals("M", ctio_move.readActionName("Green", prompt));
    assertEquals("A", ctio_attack.readActionName("Blue", prompt));
    assertEquals("D", ctio_done.readActionName("Red", prompt));
  }

  @Test
  public void test_read_territory_name() {
    String territoryName = "Narnia";
    ClientTextIO ctio = getClientTextIOObject(territoryName);
    assertEquals(territoryName, ctio.readTerritoryName("Enter territory name:\n"));

    BufferedReader inputReader = new BufferedReader(new StringReader("Haryana"));
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes, true);
    ClientTextIO ctio2 = new ClientTextIO(inputReader, output);
    // assertEquals("Invalid territory name.", bytes.toString());
    assertEquals("Haryana", ctio2.readTerritoryName("Enter territory name:\n"));
  }

  @Test
  public void test_read_num_units() {
    ClientTextIO ctio = getClientTextIOObject("5");
    assertEquals(5, ctio.readNumUnits("Enter number of units:\n"));

    ctio = getClientTextIOObject("-3\n2");
    assertEquals(2, ctio.readNumUnits("Enter number of units:\n"));

    ctio = getClientTextIOObject("-3\n-1\n-2\n-100\n5\n");
    assertEquals(5, ctio.readNumUnits("Enter number of units:\n"));

    ctio = getClientTextIOObject("abcd\n-3\n4");
    assertEquals(4, ctio.readNumUnits("Enter number of units:\n"));

    ctio = getClientTextIOObject("6abcd\n8");
    assertEquals(8, ctio.readNumUnits("Enter number of units:\n"));

    ctio = getClientTextIOObject("\n\n\n204");
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
