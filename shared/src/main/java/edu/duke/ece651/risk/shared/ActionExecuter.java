package edu.duke.ece651.risk.shared;

import java.util.Random;

public class ActionExecuter {
    /** The random generator for rolling dice in a fight. */
    private final Random rng;

    /** Constructs a ActionExecuter helper class with a given random seed. */
    public ActionExecuter(long seed) {
        this.rng = new Random(seed);
    }

    /** Constructs a ActionExecuter helper class with default random seed. */
    public ActionExecuter() {
        this.rng = new Random(1);
    }

    /**
     * Sends troops only from the src Territory, troops don't arrive dis Territory.
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
        Territory src = map.getTerritory(info.getSrcName());
        int sendNum = info.getUnitNum().get("level0");
        src.tryRemoveTroopUnits("Basic", sendNum);
    }

    /**
     * Moves troops from the src Territory to dis Territory.
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
        Territory src = map.getTerritory(info.getSrcName());
        Territory des = map.getTerritory(info.getDesName());
        int sendNum = info.getUnitNum().get("level0");
        src.tryRemoveTroopUnits("Basic", sendNum);
        des.tryAddTroopUnits("Basic", sendNum);
    }

    /**
     * Executes the attack order with given action info for attack.
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
        int attackerUnitNum = info.getUnitNum().get("level0");
        int defenderUnitNum = des.getTroopNumUnits("Basic");
        while (attackerUnitNum > 0 && defenderUnitNum > 0) {
            if (isAttackerWinFight()) {
                defenderUnitNum--;
            } else {
                attackerUnitNum--;
            }
        }
        if (attackerUnitNum > 0) { // attacker wins the combat in attack
            // des Territory changes owner and updates unit to attackerUnitNum
            des.trySetTroopUnits("Basic", attackerUnitNum);
            des.setOwnerName(info.getSrcOwnerName());
        } else { // defender wins the combat in attack
            // des Territory loses units to defenderUnitNum
            des.trySetTroopUnits("Basic", defenderUnitNum);
        }
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
    private boolean isAttackerWinFight() {
        return rollOneDice() > rollOneDice();
    }
}
