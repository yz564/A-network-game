package edu.duke.ece651.risk.shared;

import java.util.HashMap;
import java.util.Random;

public class ActionExecuter {
    /** The random generator for rolling dice in a fight. */
    private final Random rng;

    /** The ActionCostCalculator that calculates resource costs for each kind of action. */
    private ActionCostCalculator costCal = new ActionCostCalculator();

    /** Constructs a ActionExecuter helper class with a given random seed. */
    public ActionExecuter(long seed) {
        this.rng = new Random(seed);
    }

    /** Constructs a ActionExecuter helper class with default random seed. */
    public ActionExecuter() {
        this.rng = new Random(1);
    }

    /**
     * Sends troops only from the src Territory, and Troops don't arrive dis Territory.
     *
     * <p>Info of src, dis, and troop to send is in ActionInfo info argument.
     *
     * <p>Validity of this action should be checked by rule checker, so this method doesn't throw
     * error.
     *
     * @param map a WorldMap object where the action is implemented.
     * @param info a ActionInfo object that contains the information of src, dis, and troop to send.
     */
    public void sendTroops(WorldMap map, ActionInfo info) {
        String srcName = info.getSrcName();
        Territory src = map.getTerritory(srcName);
        HashMap<String, Integer> sendNumUnit = info.getNumUnits();
        for (String troopName : sendNumUnit.keySet()) {
            src.tryRemoveTroopUnits(troopName, sendNumUnit.get(troopName));
        }
    }

    /**
     * Do this before doing executeAttack for executing all attack orders in the end together. Sends
     * troops from the src Territory, and deducts costs from the player who issued the corresponding
     * attack order.
     *
     * @param map a WorldMap object where the action is implemented.
     * @param info a ActionInfo object that contains the information of src, dis, and troop to send.
     */
    public void executePreAttack(WorldMap map, ActionInfo info) {
        // deducts costs
        PlayerInfo srcOwnerInfo = map.getPlayerInfo(info.getSrcOwnerName());
        HashMap<String, Integer> resCosts = costCal.calculateAttackCost(info, map);
        for (String resType : resCosts.keySet()) {
            int updateAmt = (-1) * resCosts.get(resType);
            srcOwnerInfo.updateOneResTotal(resType, updateAmt);
        }
        // sends troops
        sendTroops(map, info);
    }

    /**
     * Moves troops from the src Territory to dis Territory, and deducts the cost of moving troop.
     *
     * <p>Info of src, dis, and troop to send is in ActionInfo info argument.
     *
     * <p>Validity of this action should be checked by rule checker, so this method doesn't throw
     * error.
     *
     * @param map a WorldMap object where the action is implemented.
     * @param info a ActionInfo object that contains the information of src, dis, and troop to send.
     */
    public void executeMove(WorldMap map, ActionInfo info) {
        // move units
        Territory src = map.getTerritory(info.getSrcName());
        Territory des = map.getTerritory(info.getDesName());
        HashMap<String, Integer> moveNumUnit = info.getTerritoryActionInfo().getUnitNum();
        for (String troopName : moveNumUnit.keySet()) {
            src.tryRemoveTroopUnits(troopName, moveNumUnit.get(troopName));
            des.tryAddTroopUnits(troopName, moveNumUnit.get(troopName));
        }
        // deducts costs
        PlayerInfo srcOwnerInfo = map.getPlayerInfo(info.getSrcOwnerName());
        HashMap<String, Integer> resCost = costCal.calculateMoveCost(info, map);
        for (String resType : resCost.keySet()) {
            srcOwnerInfo.updateOneResTotal(resType, (-1) * resCost.get(resType));
        }
    }

    /**
     * Executes the attack order with given action info for attack. Before calling this function,
     * troops are removed from src territory and costs are deducted in executePreAttack() already.
     *
     * <p>Info of src, dis, and troop to send is in ActionInfo info argument.
     *
     * <p>Validity of this action should be checked by rule checker, so this method doesn't throw
     * error.
     *
     * @param map a WorldMap object where the action is implemented.
     * @param info a ActionInfo object that contains the information of src, dis, and troop to send.
     */
    public void executeAttack(WorldMap map, ActionInfo info) {
        Territory des = map.getTerritory(info.getDesName());
        HashMap<String, Integer> attackerUnits = info.getNumUnits();
        HashMap<String, Integer> defenderUnits = des.getAllNumUnits();
        while (getTotalUnitNum(attackerUnits) > 0 && getTotalUnitNum(defenderUnits) > 0) {
            String attacker = findLowestLevelTroop(attackerUnits);
            String defender = findHighestLevelTroop(defenderUnits);
            if (isAttackerWinFight(attacker, defender)) {
                defenderUnits.put(defender, defenderUnits.get(defender) - 1);
            } else {
                attackerUnits.put(attacker, attackerUnits.get(attacker) - 1);
            }
        }
        if (getTotalUnitNum(attackerUnits) > 0) { // attacker wins the combat in attack
            // des Territory changes owner and updates unit to attackerUnitNum
            des.trySetNumUnits(attackerUnits);
            des.setOwnerName(info.getSrcOwnerName());
        } else { // defender wins the combat in attack
            // des Territory loses units to defenderUnitNum
            des.trySetNumUnits(defenderUnits);
        }
        // deducts costs
        PlayerInfo srcOwnerInfo = map.getPlayerInfo(info.getSrcOwnerName());
        HashMap<String, Integer> resCost = costCal.calculateAttackCost(info, map);
        for (String resType : resCost.keySet()) {
            srcOwnerInfo.updateOneResTotal(resType, (-1) * resCost.get(resType));
        }
    }

    public String findHighestLevelTroop(HashMap<String, Integer> defenderUnits) {
        Integer highestLevel = -1;
        String highestTroopName = null;
        HashMap<String, Troop> troopInfo = (new V2Territory("", 0, 0, 0)).getMyTroops();
        for (String troopName : defenderUnits.keySet()) {
            if (defenderUnits.get(troopName) > 0
                    && troopInfo.get(troopName).getTechLevelReq() > highestLevel) {
                highestLevel = troopInfo.get(troopName).getTechLevelReq();
                highestTroopName = troopName;
            }
        }
        return highestTroopName;
    }

    public String findLowestLevelTroop(HashMap<String, Integer> attackerUnits) {
        Integer lowestLevel = 7;
        String lowestTroopName = null;
        HashMap<String, Troop> troopInfo = (new V2Territory("", 0, 0, 0)).getMyTroops();
        for (String troopName : attackerUnits.keySet()) {
            if (attackerUnits.get(troopName) > 0
                    && troopInfo.get(troopName).getTechLevelReq() < lowestLevel) {
                lowestLevel = troopInfo.get(troopName).getTechLevelReq();
                lowestTroopName = troopName;
            }
        }
        return lowestTroopName;
    }

    public int getTotalUnitNum(HashMap<String, Integer> troops) {
        int totalUnitNum = 0;
        for (int num : troops.values()) {
            totalUnitNum = totalUnitNum + num;
        }
        return totalUnitNum;
    }

    /***
     * Rolls a 20-sided dice, and returns the result number.
     *
     * @return an int that is between 0 to 19.
     */
    private int rollOneDice() {
        return rng.nextInt(20);
    }

    /***
     * Rolls two 20-sided dice (one for the attacker, one for the defender) If the
     * attacker has higher roll (not equal), return true.
     *
     * @return true if the attacker wins the fight, false otherwise.
     */
    private boolean isAttackerWinFight(String attacker, String defender) {
        HashMap<String, Troop> troopInfo = (new V2Territory("", 0, 0, 0)).getMyTroops();
        int attackerBonus = troopInfo.get(attacker).getBonus();
        int defenderBonus = troopInfo.get(defender).getBonus();
        return rollOneDice() + attackerBonus > rollOneDice() + defenderBonus;
    }
}
