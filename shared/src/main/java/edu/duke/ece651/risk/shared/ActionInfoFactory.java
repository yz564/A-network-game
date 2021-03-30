package edu.duke.ece651.risk.shared;

import java.util.HashMap;

public class ActionInfoFactory {

    /**
     * Creates an adapted ActionInfo for move action.
     *
     * @param srcOwnerName is the owner's name of this action.
     * @param srcName is the source Territory name of the action.
     * @param desName is the destination Territory name of the action.
     * @param unitNum is the number of units in tech type of troop assigned to the action..
     * @return an adapted ActionInfo for move action.
     */
    public ActionInfo createMoveActionInfo(
            String srcOwnerName, String srcName, String desName, HashMap<String, Integer> unitNum) {
        TerritoryActionInfo info = new TerritoryActionInfo(srcName, desName, unitNum);
        return new ActionInfo(srcOwnerName, "move", info);
    }

    /**
     * Creates an adapted ActionInfo for attack action.
     *
     * @param srcOwnerName is the owner's name of this action.
     * @param srcName is the source Territory name of the action.
     * @param desName is the destination Territory name of the action.
     * @param unitNum is the number of units in tech type of troop assigned to the action..
     * @return an adapted ActionInfo for attack action.
     */
    public ActionInfo createAttackActionInfo(
            String srcOwnerName, String srcName, String desName, HashMap<String, Integer> unitNum) {
        TerritoryActionInfo info = new TerritoryActionInfo(srcName, desName, unitNum);
        return new ActionInfo(srcOwnerName, "attack", info);
    }

    /**
     * Creates an adapted ActionInfo for upgrade tech action.
     *
     * @param srcOwnerName is the owner's name of this action.
     * @param newTechLevel is an Integer represents the new tech level for upgrade tech level field.
     * @return an adapted ActionInfo for upgrade tech action.
     */
    public ActionInfo createUpgradeTechActionInfo(String srcOwnerName, Integer newTechLevel) {
        UpgradeTechActionInfo info = new UpgradeTechActionInfo(newTechLevel);
        return new ActionInfo(srcOwnerName, "upgrade tech", info);
    }

    /**
     * Creates an adapted ActionInfo for upgrade unit action.
     *
     * @param srcOwnerName is the owner's name of this action.
     * @param srcName is the source Territory name of the action.
     * @param oldUnitLevel is an int represents the old unit level for the upgrade unit action.
     * @param newUnitLevel is an int represents the new unit level to the upgrade unit action.
     * @param numToUpgrade is an int represents the number of units to upgrade from the old level to
     *     the new level.
     * @return an adapted ActionInfo for upgrade unit action.
     */
    public ActionInfo createUpgradeUnitActionInfo(
            String srcOwnerName,
            String srcName,
            Integer oldUnitLevel,
            Integer newUnitLevel,
            Integer numToUpgrade) {
        UpgradeUnitActionInfo info =
                new UpgradeUnitActionInfo(srcName, oldUnitLevel, newUnitLevel, numToUpgrade);
        return new ActionInfo(srcOwnerName, "upgrade unit", info);
    }
}
