package edu.duke.ece651.risk.shared;

import java.util.HashMap;
import java.util.HashSet;

/**
 * BasicTerritory is a simple territory that contains a single troop of units
 * inside it and has a name.
 * <p>
 * It also contains a hashset of neighboring territories that it is adjacent to
 * and its owner's name.
 */
public class BasicTerritory implements Territory {

    private static final long serialVersionUID = -8815409601117401416L;
    private Troop myTroop; // change this to HashMap<String(troop name: level1 level2 level3 ....), Troop(Troop object)>
    private String territoryName;
    private HashMap<String, Territory> myNeighbors;
    private String myOwnerName;

    /**
     * Makes initial troops for a new territory.
     *
     * @return a HashMap with String key as the Troop name, and Troop object value.
     */
    static HashMap<String, Troop> makeTroops() {
        HashMap<String, Troop> myTroops = new HashMap<String, Troop>();
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
     * Construct a BasicTerritory object.
     *
     * @param name  is the name to assign to the territory.
     * @param toAdd is the Troop to add the the territory.
     */
    public BasicTerritory(String name, Troop toAdd) {
        this.myTroop = toAdd;
        this.territoryName = name;
        this.myNeighbors = new HashMap<String, Territory>();
        this.myOwnerName = null;
    }

    /**
     * Construct a BasicTerritory object.
     *
     * @param name     is the name to assign to the territory.
     * @param numUnits is the number of units to add to the territory.
     */
    public BasicTerritory(String name, int numUnits) {
        this(name, new BasicTroop(numUnits));
    }

    @Override
    public boolean tryAddUnits(int toAdd) {
        return myTroop.tryAddUnits(toAdd);
    }

    @Override
    public boolean tryRemoveUnits(int toRemove) {
        return myTroop.tryRemoveUnits(toRemove);
    }

    @Override
    public int getNumUnits() {
        return myTroop.getNumUnits();
    }

    @Override
    public String getName() {
        return territoryName;
    }

    /**
     * Return the troop present inside the territory.
     */
    public Troop getTroop() {
        return myTroop;
    }

    @Override
    public boolean trySetNumUnits(int numUnits) {
        return myTroop.trySetNumUnits(numUnits);
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o.getClass().equals(getClass())) {
            BasicTerritory other = (BasicTerritory) o;
            return this.myTroop.equals(other.getTroop()) && this.territoryName.equals(other.getName());
        }
        return false;
    }

    @Override
    public String toString() {
        return "Territory " + this.territoryName + " contains the following troop:\n" + myTroop.toString();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean isAdjacentTo(Territory neighbor) {
        return this.myNeighbors.containsKey(neighbor.getName());
    }

    @Override
    public boolean tryAddNeighbor(Territory neighbor) {
        if (this.myNeighbors.put(neighbor.getName(), neighbor) != null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String getOwnerName() {
        return myOwnerName;
    }

    @Override
    public boolean isBelongTo(String playerName) {
        if (myOwnerName != null) {
            return this.myOwnerName.equals(playerName);
        } else {
            return false;
        }
    }

    @Override
    public boolean tryAssignOwner(String playerName) {
        if (playerName != null) {
            this.myOwnerName = playerName;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public HashMap<String, Territory> getMyNeighbors() {
        return this.myNeighbors;
    }

    @Override
    public boolean isReachableTo(Territory toReach) {
        HashMap<String, Territory> reachable = new HashMap<String, Territory>();
        int size = reachable.size();
        reachable.put(this.territoryName, this);
        while (size != reachable.size()) {
            size = reachable.size();
            HashSet<String> names = new HashSet<String>(reachable.keySet());
            for (String name : names) {
                HashMap<String, Territory> neighbors = reachable.get(name).getMyNeighbors();
                for (String neighborName : neighbors.keySet()) {
                    if (neighbors.get(neighborName).isBelongTo(this.myOwnerName)) {
                        reachable.put(neighborName, neighbors.get(neighborName));
                    }
                }
            }
        }
        if (reachable.containsValue(toReach)) {
            return true;
        } else {
            return false;
        }
    }

}
