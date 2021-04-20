package edu.duke.ece651.risk.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ActionExecuter {
    /** The random generator for rolling dice in a fight. */
    private final Random rng;

    /** The ActionCostCalculator that calculates resource costs for each kind of action. */
    private final ActionCostCalculator costCal = new ActionCostCalculator();

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
        HashMap<String, Integer> resCost = costCal.calculateAttackCost(info, map);
        deductCost(map, info, resCost);
        // sends troops
        sendTroops(map, info);
    }

    /**
     * Deducts resources cost for an action.
     *
     * @param map the WorldMap to deduct the cost from.
     * @param info the action info that contains the source owner name from whom to deduct the cost.
     * @param resCost the resource cost to deduct.
     */
    private void deductCost(WorldMap map, ActionInfo info, HashMap<String, Integer> resCost) {
        PlayerInfo srcOwnerInfo = map.getPlayerInfo(info.getSrcOwnerName());
        for (String resType : resCost.keySet()) {
            srcOwnerInfo.updateOneResTotal(resType, (-1) * resCost.get(resType));
        }
    }

    /**
     * Executes research cloaking action for a player who is the owner of the given action.
     *
     * @param map a WorldMap on which the research cloaking action should be implemented.
     * @param info an ActionInfo that contains the information of the owner of this action.
     */
    public void executeResearchCloaking(WorldMap map, ActionInfo info) {
        // player research cloaking
        String playerName = info.getSrcOwnerName();
        map.getPlayerInfo(playerName).setIsCloakingResearched(true);
        // deducts costs
        HashMap<String, Integer> resCost = costCal.calculateResearchCloakingCost(info, map);
        deductCost(map, info, resCost);
    }

    /**
     * Executes cloaking action on a territory.
     *
     * @param map a WorldMap on which the cloaking action should be implemented.
     * @param info an ActionInfo that contains the information of target territory name.
     */
    public void executeCloaking(WorldMap map, ActionInfo info) {
        // do cloaking on a territory
        String territoryName = info.getSrcName();
        map.getTerritory(territoryName).setCloakingTurns(3);
        // deducts costs
        HashMap<String, Integer> resCost = costCal.calculateCloakingCost(info, map);
        deductCost(map, info, resCost);
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
        HashMap<String, Integer> resCost = costCal.calculateMoveCost(info, map);
        deductCost(map, info, resCost);
    }

    /**
     * Executes the move spy action.
     *
     * @param map the WorldMap object where the action is implemented.
     * @param info a ActionInfo object that contains the information of src, dis, and number of spy
     *     to move.
     */
    public void executeMoveSpy(WorldMap map, ActionInfo info) {
        // move spy
        Territory src = map.getTerritory(info.getSrcName());
        Territory des = map.getTerritory(info.getDesName());
        int numSpy = info.getNumSpyUnits();
        src.tryRemoveSpyTroopUnits(info.getSrcOwnerName(), numSpy);
        des.tryAddSpyTroopUnits(info.getSrcOwnerName(), numSpy);
        // deducts costs
        HashMap<String, Integer> resCost = costCal.calculateMoveSpyCost(info, map);
        deductCost(map, info, resCost);
    }

    /**
     * Upgrades the tech level for the source owner player, and deducts the resources from the
     * player.
     *
     * @param map a WorldMap object where the action is implemented.
     * @param info a ActionInfo object that contains source owner name and the new tech level to
     *     upgrade to.
     */
    public void executeUpgradeTech(WorldMap map, ActionInfo info) {
        // deducts costs
        HashMap<String, Integer> resCost = costCal.calculateUpgradeTechCost(info, map);
        deductCost(map, info, resCost);
        // do the upgrade
        String srcOwnerName = info.getSrcOwnerName();
        map.getPlayerInfo(srcOwnerName).setTechLevel(info.getNewTechLevel());
    }

    /**
     * Upgrades the units required by the source owner player, and deducts the resources form the
     * player.
     *
     * @param map a WorldMap object where the action is implemented.
     * @param info a ActionInfo object that contains source owner name and the info of the upgrade
     *     units order.
     */
    public void executeUpgradeUnit(WorldMap map, ActionInfo info) {
        // do the upgrade
        String srcName = info.getSrcName();
        String oldUnitLevel = info.getOldUnitLevel();
        String newUnitLevel = info.getNewUnitLevel();
        int numToUpgrade = info.getUpgradeUnitActionInfo().getNumToUpgrade();
        map.getTerritory(srcName).tryAddTroopUnits(newUnitLevel, numToUpgrade);
        map.getTerritory(srcName).tryRemoveTroopUnits(oldUnitLevel, numToUpgrade);
        // deducts costs
        HashMap<String, Integer> resCost = costCal.calculateUpgradeUnitCost(info, map);
        deductCost(map, info, resCost);
    }

    /**
     * Upgrades the spy units.
     *
     * @param map a WorldMap object where the action is implemented.
     * @param info a ActionInfo object that contains source owner name and the info of the upgrade
     *     units order.
     */
    public void executeUpgradeSpyUnit(WorldMap map, ActionInfo info) {
        // upgrade the spy
        String srcName = info.getSrcName();
        String oldUnitLevel = info.getOldUnitLevel();
        int numToUpgrade = info.getNumSpyUnits();
        map.getTerritory(srcName).tryAddSpyTroopUnits(info.getSrcOwnerName(), numToUpgrade);
        map.getTerritory(srcName).tryRemoveTroopUnits(oldUnitLevel, numToUpgrade);
        // deducts costs
        HashMap<String, Integer> resCost = costCal.calculateUpgradeSpyUnitCost(info, map);
        deductCost(map, info, resCost);
    }

    /**
     * Adds the patent progress to the player who ordered the research patent action.
     *
     * @param map a WorldMap object where the action is implemented.
     * @param info a ActionInfo object that contains source owner name and the info of the territory
     *     that involved in the patent research action.
     */
    public void executeResearchPatent(WorldMap map, ActionInfo info) {
        // increase patent progress
        String playerDomain = map.getPlayerInfo(info.getSrcOwnerName()).getResearchDomain();
        ArrayList<String> territoryNames = info.getTargetTerritoryNames();
        int total = 0;
        // calculate the sum of each target territory's generated gross progress.
        for (String territoryName : territoryNames) {
            V2Territory territory = (V2Territory) map.getTerritory(territoryName);
            int gross = 0;
            // sum(number of units in each level troop * (bonus of each level troop + 1))
            for (String troopName : territory.getMyTroops().keySet()) {
                Troop troop = territory.getTroop(troopName);
                gross = gross + (troop.getBonus() + 1) * troop.getNumUnits();
            }
            gross = gross * territory.getPatentResearchRate().get(playerDomain);
            total = total + gross;
        }
        // adjust the speed of research patent.
        // TODO: find appropriate speed
        total = total / 30;
        map.getPlayerInfo(info.getSrcOwnerName()).addPatentProgress(total);
        // deducts cost
        HashMap<String, Integer> resCost = costCal.calculateResearchPatentCost(info, map);
        deductCost(map, info, resCost);
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
        boolean attackerHoldsPower = true;
        while (getTotalUnitNum(attackerUnits) > 0 && getTotalUnitNum(defenderUnits) > 0) {
            String attacker = null;
            String defender = null;
            if (attackerHoldsPower) {
                attacker = findHighestLevelTroop(attackerUnits);
                defender = findLowestLevelTroop(defenderUnits);
                attackerHoldsPower = false;
            } else {
                attacker = findLowestLevelTroop(attackerUnits);
                defender = findHighestLevelTroop(defenderUnits);
                attackerHoldsPower = true;
            }
            if (isAttackerWinFight(attacker, defender)) {
                defenderUnits.put(defender, defenderUnits.get(defender) - 1);
            } else {
                attackerUnits.put(attacker, attackerUnits.get(attacker) - 1);
            }
        }
        if (getTotalUnitNum(attackerUnits) > 0) { // attacker wins the combat in attack
            // des Territory changes owner, and updates unit to attackerUnitNum, and resets cloaking
            // turn to 0.
            des.trySetNumUnits(attackerUnits);
            des.setOwnerName(info.getSrcOwnerName());
            des.setCloakingTurns(0);
        } else { // defender wins the combat in attack
            // des Territory loses units to defenderUnitNum
            des.trySetNumUnits(defenderUnits);
        }
    }

    /**
     * Finds the highest level troop name tht has unit in a given HashMap that is troop name to
     * number of units in the troop.
     *
     * @param units a HashMap that is troop name to the number of units in the troop.
     * @return a String that represents the highest level troop.
     */
    public String findHighestLevelTroop(HashMap<String, Integer> units) {
        Integer highestLevel = -1;
        String highestTroopName = null;
        HashMap<String, Troop> troopInfo =
                (new V2Territory("", 0, 0, 0, 0, 0, 0, 0, 0)).getMyTroops();
        for (String troopName : units.keySet()) {
            if (units.get(troopName) > 0
                    && troopInfo.get(troopName).getTechLevelReq() > highestLevel) {
                highestLevel = troopInfo.get(troopName).getTechLevelReq();
                highestTroopName = troopName;
            }
        }
        return highestTroopName;
    }

    /**
     * Finds the lowest level troop name tht has unit in a given HashMap that is troop name to
     * number of units in the troop.
     *
     * @param units a HashMap that is troop name to the number of units in the troop.
     * @return a String that represents the highest level troop.
     */
    public String findLowestLevelTroop(HashMap<String, Integer> units) {
        Integer lowestLevel = 7;
        String lowestTroopName = null;
        HashMap<String, Troop> troopInfo =
                (new V2Territory("", 0, 0, 0, 0, 0, 0, 0, 0)).getMyTroops();
        for (String troopName : units.keySet()) {
            if (units.get(troopName) > 0
                    && troopInfo.get(troopName).getTechLevelReq() < lowestLevel) {
                lowestLevel = troopInfo.get(troopName).getTechLevelReq();
                lowestTroopName = troopName;
            }
        }
        return lowestTroopName;
    }

    /**
     * Gets the totally number of units in a HashMap that is troop name mapped to number of unit in
     * the troop.
     *
     * @param troops a HashMap that is troop name to the number of units in the troop.
     * @return an int that represents the total number of units.
     */
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
        HashMap<String, Troop> troopInfo =
                (new V2Territory("", 0, 0, 0, 0, 0, 0, 0, 0)).getMyTroops();
        int attackerBonus = troopInfo.get(attacker).getBonus();
        int defenderBonus = troopInfo.get(defender).getBonus();
        return rollOneDice() + attackerBonus > rollOneDice() + defenderBonus;
    }
}
