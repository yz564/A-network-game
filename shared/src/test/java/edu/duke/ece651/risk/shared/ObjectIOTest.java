package edu.duke.ece651.risk.shared;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class ObjectIOTest {
  @Test
  public void test_constructors() {
    ObjectIO o1 = new ObjectIO();
    ObjectIO o2 = new ObjectIO("Test message");
    ObjectIO o3 = new ObjectIO("Test message", 1);
    WorldMapFactory factory = new V1MapFactory();
    WorldMap map = factory.makeTestWorldMap();
    ObjectIO o4 = new ObjectIO("Test message", 1, map);
    HashSet<Integer> groups = new HashSet<Integer>();
    groups.add(1);
    groups.add(2);
    groups.add(3);
    ObjectIO o5 = new ObjectIO("Test message", 1, map, groups);
    ArrayList<String> name = new ArrayList<String>();
    name.add("Player 1");
    name.add("Player 2");
    name.add("Player 3");
    ObjectIO o6 = new ObjectIO("Test message", 1, map, name);
  }

}
