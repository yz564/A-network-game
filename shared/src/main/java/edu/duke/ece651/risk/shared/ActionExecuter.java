package edu.duke.ece651.risk.shared;

import java.util.Random;

public class ActionExecuter {
    /**
     * The random generator for rolling dice in a fight.
     */
    final private Random rng;

    /**
     * Constructes a ActionExecuter helper class with a given random seed.
     */
    public ActionExecuter(long seed) {
        this.rng = new Random(seed);
    }

    /**
     * Constructes a ActionExecuter helper class with default random seed.
     */
    public ActionExecuter() {
        this.rng = new Random(1);
    }

    /**
     * Sends troops only from the src Territory, troops don't arrive dis Territory.
     * 
     * Info of src, dis, and troop to send is in ActionInfo info argument.
     * 
     * Validity of this action should be checked by rule checker, so this method
     * doesn't throw error.
     * 
     * @param map  a WorldMap object where the action is implemented.
     * @param info a ActionInfo object that contains the information of src, dis,
     *             and troop to send.
     */
    public void sendTroops(WorldMap map, ActionInfo info) {
        Territory src = map.getTerritory(info.getSrcName());
        int sendNum = info.getUnitNum();
        src.trySetNumUnits(src.getNumUnits() - sendNum);
    }

    /**
     * Moves troops from the src Territory to dis Territory.
     * 
     * Info of src, dis, and troop to send is in ActionInfo info argument.
     * 
     * Validity of this action should be checked by rule checker, so this method
     * doesn't throw error.
     * 
     * @param map  a WorldMap object where the action is implemented.
     * @param info a ActionInfo object that contains the information of src, dis,
     *             and troop to send.
     */
    public void executeMove(WorldMap map, ActionInfo info) {
        Territory src = map.getTerritory(info.getSrcName());
        Territory des = map.getTerritory(info.getDesName());
        int sendNum = info.getUnitNum();
        src.trySetNumUnits(src.getNumUnits() - sendNum);
        des.trySetNumUnits(des.getNumUnits() + sendNum);
    }

    public void executeAttack(WorldMap map, ActionInfo info) {
        Territory src = map.getTerritory(info.getSrcName());
        Territory des = map.getTerritory(info.getDesName());
        int attackerUnitNum = info.getUnitNum();
        int defenderUnitNum = des.getNumUnits();
        while (attackerUnitNum > 0 && defenderUnitNum > 0) {
            if (isAttackerWinFight()) {
                defenderUnitNum--;
            } else {
                attackerUnitNum--;
            }
        }
        if (attackerUnitNum > 0) { // attacker wins the combat in attack
            // des Territory changes owner and updates unit to attackerUnitNum
            des.trySetNumUnits(attackerUnitNum);
            des.tryAssignOwner(src.getOwnerName());
        } else { // defender wins the combat in attack
            // des Territory loses units to defenderUnitNum
            des.trySetNumUnits(defenderUnitNum);
        }
    }

    private int rollOneDice() {
        return rng.nextInt(20);
    }

    private boolean isAttackerWinFight() {
        return rollOneDice() > rollOneDice();
    }
}
