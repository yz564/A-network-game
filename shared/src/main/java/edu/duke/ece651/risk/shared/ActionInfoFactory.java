package edu.duke.ece651.risk.shared;

import java.util.ArrayList;
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

    public ActionInfo createMoveSpyActionInfo(
            String srcOwnerName, String srcName, String desName, int spyUnitNum) {
        TerritoryActionInfo info = new TerritoryActionInfo(srcName, desName, spyUnitNum);
        return new ActionInfo(srcOwnerName, "move spy", info);
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
     * @param oldUnitLevel is a String represents the old unit level for the upgrade unit action.
     * @param newUnitLevel is a String represents the new unit level to the upgrade unit action.
     * @param numToUpgrade is an int represents the number of units to upgrade from the old level to
     *     the new level.
     * @return an adapted ActionInfo for upgrade unit action.
     */
    public ActionInfo createUpgradeUnitActionInfo(
            String srcOwnerName,
            String srcName,
            String oldUnitLevel,
            String newUnitLevel,
            Integer numToUpgrade) {
        UpgradeUnitActionInfo info =
                new UpgradeUnitActionInfo(srcName, oldUnitLevel, newUnitLevel, numToUpgrade);
        return new ActionInfo(srcOwnerName, "upgrade unit", info);
    }

    public ActionInfo createUpgradeSpyUnitActionInfo(
            String srcOwnerName, String srcName, Integer numToUpgrade) {
        UpgradeUnitActionInfo info =
                new UpgradeUnitActionInfo(srcName, "level0", "spy", numToUpgrade);
        return new ActionInfo(srcOwnerName, "upgrade spy unit", info);
    }

    /**
     * Creates an adapted ActionInfo for research cloaking.
     *
     * @param srcOwnerName is the owner's name of this action.
     * @return an adapted ActionInfo for research cloaking.
     */
    public ActionInfo createResearchCloakingActionInfo(String srcOwnerName) {
        return new ActionInfo(srcOwnerName, "research cloaking");
    }

    /**
     * Creates an adapted ActionInfo for cloaking action.
     *
     * @param srcOwnerName is the owner's name of this action.
     * @param targetTerritoryName is a String that represents the target territory, on which to do
     *     cloaking.
     * @return an adapted ActionInfo for cloaking action.
     */
    public ActionInfo createCloakingActionInfo(String srcOwnerName, String targetTerritoryName) {
        CloakingActionInfo info = new CloakingActionInfo(targetTerritoryName);
        return new ActionInfo(srcOwnerName, "cloaking", info);
    }

    /**
     * Create an adapted ActionInfo for research patent action.
     *
     * @param srcOwnerName is the owner's name of this action.
     * @param targetTerritoryNames is the given list of territories that player required to
     *     implement research patent.
     * @return an adapted ActionInfo for research patent action.
     */
    public ActionInfo creatResearchPatentActionInfo(
            String srcOwnerName, ArrayList<String> targetTerritoryNames) {
        ResearchPatentActionInfo info = new ResearchPatentActionInfo(targetTerritoryNames);
        return new ActionInfo(srcOwnerName, "research patent", info);
    }
}
