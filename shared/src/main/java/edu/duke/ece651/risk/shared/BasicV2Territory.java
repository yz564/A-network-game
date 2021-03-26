package edu.duke.ece651.risk.shared;

import java.util.HashMap;

/** @author group7 */
public class BasicV2Territory implements V2Territory {
    private static final long serialVersionUID = -8815409601117401416L;
    private HashMap<String, Troop> myTroops;
    private final String territoryName;
    private HashMap<String, Territory> myNeighbors;
    private String myOwnerName;
    private final HashMap<String, Integer> resProduction;

    /**
     * Makes initial troops for a new territory. Pass makeTroops() in constructor of V2 territory.
     *
     * @return a HashMap with String key as the Troop name, and Troop object value.
     */
    private static HashMap<String, Troop> makeTroops() {
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

    /** Construct a BasicTerritory object. */
    public BasicV2Territory(
            String name, HashMap<String, Integer> resProduction, HashMap<String, Troop> myTroops) {
        this.territoryName = name;
        this.myTroops = myTroops;
        this.myNeighbors = new HashMap<String, Territory>();
        this.myOwnerName = null;
        this.resProduction = resProduction;
    }

    /**
     * Construct a BasicTerritory object with default Troops made by makeTroops().
     *
     * @param name is the name to assign to the territory.
     * @param resProduction is the production rate of different types of resources. HashMap key is
     *     the resource name, and value is the production rate.
     */
    public BasicV2Territory(String name, HashMap<String, Integer> resProduction) {
        this(name, resProduction, makeTroops());
    }

    @Override
    public void addUnits(HashMap<String, Integer> toAdd) {
        for (String troopName : toAdd.keySet()) {
            int addNum = toAdd.get(troopName);
            myTroops.get(troopName).tryAddUnits(addNum);
        }
    }

    @Override
    public void removeUnits(HashMap<String, Integer> toRemove) {
        for (String troopName : toRemove.keySet()) {
            int removeNum = toRemove.get(troopName);
            myTroops.get(troopName).tryRemoveUnits(removeNum);
        }
    }

    @Override
    public void setNumUnits(HashMap<String, Integer> toSet) {
        for (String troopName : toSet.keySet()) {
            int setNum = toSet.get(troopName);
            myTroops.get(troopName).trySetNumUnits(setNum);
        }
    }

    @Override
    public HashMap<String, Integer> getAllNumUnits() {
        HashMap<String, Integer> allNumUnits = new HashMap<String, Integer>();
        for (String troopName : myTroops.keySet()) {
            allNumUnits.put(troopName, myTroops.get(troopName).getNumUnits());
        }
        return allNumUnits;
    }

    @Override
    public String getName() {
        return territoryName;
    }

    @Override
    public HashMap<String, Troop> getMyTroops() {
        return myTroops;
    }

    @Override
    public boolean isAdjacentTo(Territory neighbor) {
        return false;
    }

    @Override
    public boolean tryAddNeighbor(Territory neighbor) {
        return false;
    }

    @Override
    public String getOwnerName() {
        return null;
    }

    @Override
    public boolean isBelongTo(String playerName) {
        return false;
    }

    @Override
    public boolean tryAssignOwner(String playerName) {
        return false;
    }

    @Override
    public HashMap<String, Territory> getMyNeighbors() {
        return null;
    }

    @Override
    public boolean isReachableTo(Territory toReach) {
        return false;
    }
}
