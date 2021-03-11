package edu.duke.ece651.risk.shared;

import java.util.HashMap;

public class BasicTerritory implements Territory {
  private HashMap<String, Integer> myUnits;
  private String name;

  public BasicTerritory(String name, HashMap<String Integer> units) {
    this.myUnits = units;
    this.name = name;
  }

  @Override
  public boolean tryAddUnit() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean tryRemoveUnit() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isAdjacent(Territory otherTerritory, WorldMap map) {
    // TODO Auto-generated method stub
    return false;
  }

}
