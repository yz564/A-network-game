package edu.duke.ece651.risk.shared;

import java.util.HashMap;
import java.util.HashSet;

public class V2Territory extends AbstractTerritory {
  private static final long serialVersionUID = -8815409601117401416L;
  private final HashMap<String, Integer> resProduction;
  private final int size;
  private int cloakingTurns;


  /**
   * Makes initial troops for a new territory. Pass makeTroops() in constructor of V2 territory.
   *
   * @return a HashMap with String key as the Troop name, and Troop object value.
   */
  static HashMap<String, Troop> makeTroops() {
    HashMap<String, Troop> myTroops = new HashMap<>();
    myTroops.put("level0", new LevelTroop("level0", 0, 0, 0, 0));
    myTroops.put("level1", new LevelTroop("level1", 0, 1, 1, 3));
    myTroops.put("level2", new LevelTroop("level2", 0, 3, 2, 11));
    myTroops.put("level3", new LevelTroop("level3", 0, 5, 3, 30));
    myTroops.put("level4", new LevelTroop("level4", 0, 8, 4, 55));
    myTroops.put("level5", new LevelTroop("level5", 0, 11, 5, 90));
    myTroops.put("level6", new LevelTroop("level6", 0, 15, 6, 140));
    return myTroops;
  }

  /**
   * Construct a V2Territory object.
   *
   * @param name is the name to assign to the territory.
   * @param troops are the troops that are present in this territory. HashMap key is troop name, and
   *     value is the Troop objects representing troops in this territory.
   * @param resources is the production rate of different types of resources. HashMap key is the
   *     resource name, and value is the production rate.
   * @param size is an integer represents the size of the territory.
   */
  public V2Territory(
      String name, HashMap<String, Troop> troops, HashMap<String, Integer> resources, int size) {
    super(name, troops);
    this.resProduction = resources;
    this.size = size;
    this.cloakingTurns = 0;
  }

  /**
   * Construct a V2Territory object with default Troops made by makeTroops().
   *
   * @param name is the name to assign to the territory.
   * @param resources is the production rate of different types of resources. HashMap key is the
   *     resource name, and value is the production rate.
   * @param size is an integer represents the size of the territory.
   */
  public V2Territory(String name, HashMap<String, Integer> resources, int size) {
    this(name, makeTroops(), resources, size);
  }

  /**
   * Construct a V2Territory object with default Troops made by makeTroops(), and given food and
   * tech production rate.
   *
   * @param name is the name to assign to the territory.
   * @param foodProduction is the int represents the foodProduction rate of this territory.
   * @param techProduction is the int represents the techProduction rate of this territory.
   * @param size is an integer represents the size of the territory.
   */
  public V2Territory(String name, Integer foodProduction, Integer techProduction, int size) {
    this(name, new HashMap<>(), size);
    resProduction.put("food", foodProduction);
    resProduction.put("tech", techProduction);
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
  public HashMap<String, Integer> getResProduction() {
    return this.resProduction;
  }

  @Override
  public int getMoveCost(Territory neighbor) {
    return size + neighbor.getSize();
  }

  /**
   * Helper function for findAllMinMoveCost() Find the territory with the minimum cost that has not
   * been visited yet
   *
   * @param visited is the HashSet of territory names that have already been visited
   * @param moveCost is the HashMap of moving cost from current territory mapped to destination
   *     territory names
   * @return String of the territory name with minimum moving cost
   */
  private String findMinCostTerritory(HashSet<String> visited, HashMap<String, Integer> moveCost) {
    String minCostTerritory = null;
    int minCost = Integer.MAX_VALUE;
    for (String name : moveCost.keySet()) {
      int currCost = moveCost.get(name);
      if (!visited.contains(name) && currCost < minCost) {
        minCost = currCost;
        minCostTerritory = name;
      }
    }
    return minCostTerritory;
  }

  /**
   * Find all the minimum moving cost from the current territory to each of the reachable
   * territories, where reachable is defined as connected by adjacent territories belonging to the
   * same owner as the current territory.
   *
   * @return a HashMap where key is String representing the territory name and value is int
   *     representing the minimum moving cost
   */
  private HashMap<String, Integer> findAllMinMoveCost() {
    HashMap<String, Territory> reachable = findReachableTerritories();
    HashMap<String, Integer> moveCost = new HashMap<String, Integer>();
    HashSet<String> visited = new HashSet<String>();
    // HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
    // Initialize moveCost to inf, visited to false
    for (String reachableName : reachable.keySet()) {
      moveCost.put(reachableName, Integer.MAX_VALUE);
      // visited.put(reachableName, false);
    }
    // Start with this Territory
    moveCost.put(this.territoryName, 0);
    // visited.add(this.territoryName);
    // int numVisited = visited.size();
    while (visited.size() < reachable.keySet().size()) {
      String currName = findMinCostTerritory(visited, moveCost);
      // visited.put(currName, true);
      visited.add(currName);
      Territory curr = reachable.get(currName);
      HashMap<String, Territory> neighbors = curr.getReachableNeighbors();
      for (String neighborName : neighbors.keySet()) {
        if (!visited.contains(neighborName)) {
          int newCost = moveCost.get(currName) + curr.getMoveCost(neighbors.get(neighborName));
          if (newCost < moveCost.get(neighborName)) {
            moveCost.put(neighborName, newCost);
          }
        }
      }
    }
    return moveCost;
  }

  @Override
  public int findMinMoveCost(Territory toReach) {
    return findAllMinMoveCost().get(toReach.getName());
  }


  @Override
  public int getCloakingTurns() {
    return this.cloakingTurns;
  }

  @Override
  public void setCloakingTurns(int cloakingTurns) {
    this.cloakingTurns = cloakingTurns;
  }

  @Override
  public void reduceCloakingTurns() {
    this.cloakingTurns -= 1;
  }

}


