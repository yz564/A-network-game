package edu.duke.ece651.risk.shared;

import java.util.HashMap;

/** Information used in a move or an attack action. */
public class ActionInfo implements java.io.Serializable {

    /** A generated serial version UID for this class. */
    private static final long serialVersionUID = 4297826252791846347L;

    /** The owner's name of the source Territory. */
    private final String srcOwnerName;

    /** The action type of the action. */
    private final String actionType;

    /** The detailed move or attach action info. */
    private TerritoryActionInfo territoryActionInfo;

    /** The detailed upgrade tech action info. */
    private UpgradeTechActionInfo upgradeTechActionInfo;

    /** The detailed upgrade unit action info. */
    private UpgradeUnitActionInfo upgradeUnitActionInfo;

    /**
     * Default constructor of ActionInfo.
     *
     * @param srcOwnerName is the owner's name of the source Territory.
     * @param actionType is a String represents the action type of the action.
     */
    public ActionInfo(String srcOwnerName, String actionType) {
        this.srcOwnerName = srcOwnerName;
        this.actionType = actionType;
    }

    /**
     * Constructors an ActionInfo with given TerritoryActionInfo.
     *
     * @param srcOwnerName is the owner's name of the source Territory.
     * @param actionType is a String represents the action type of the action.
     * @param territoryActionInfo is a TerritoryActionInfo that contains detailed info of move or
     *     attack action.
     */
    public ActionInfo(
            String srcOwnerName, String actionType, TerritoryActionInfo territoryActionInfo) {
        this.srcOwnerName = srcOwnerName;
        this.actionType = actionType;
        this.territoryActionInfo = territoryActionInfo;
    }

    /**
     * Constructors an ActionInfo with given UpgradeTechActionInfo.
     *
     * @param srcOwnerName is the owner's name of the source Territory.
     * @param actionType is a String represents the action type of the action.
     * @param upgradeTechActionInfo is an UpgradeTechActionInfo that contains detailed information
     *     of upgrade tech level action.
     */
    public ActionInfo(
            String srcOwnerName, String actionType, UpgradeTechActionInfo upgradeTechActionInfo) {
        this.srcOwnerName = srcOwnerName;
        this.actionType = actionType;
        this.upgradeTechActionInfo = upgradeTechActionInfo;
    }

    /**
     * Constructors an ActionInfo with given UpgradeUnitActionInfo.
     *
     * @param srcOwnerName is the owner's name of the source Territory.
     * @param actionType is a String represents the action type of the action.
     * @param upgradeUnitActionInfo is an UpgradeUnitActionInfo that contains detailed information
     *     of upgrade unit action.
     */
    public ActionInfo(
            String srcOwnerName, String actionType, UpgradeUnitActionInfo upgradeUnitActionInfo) {
        this.srcOwnerName = srcOwnerName;
        this.actionType = actionType;
        this.upgradeUnitActionInfo = upgradeUnitActionInfo;
    }

    /**
     * Getter of srcOwnerName field.
     *
     * @return a String represents owner's name of the source Territory.
     */
    public String getSrcOwnerName() {
        return this.srcOwnerName;
    }

    /**
     * Getter of actionType field.
     *
     * @return a String that represents the action's type.
     */
    public String getActionType() {
        return this.actionType;
    }

    /**
     * Getter of territoryActionInfo field.
     *
     * @return a TerritoryActionInfo that contains detailed info of move or * attack action.
     */
    public TerritoryActionInfo getTerritoryActionInfo() {
        return territoryActionInfo;
    }

    /**
     * Getter of upgradeTechActionInfo field.
     *
     * @return an UpgradeTechActionInfo that contains detailed information of upgrade tech level
     *     action.
     */
    public UpgradeTechActionInfo getUpgradeTechActionInfo() {
        return upgradeTechActionInfo;
    }

    /**
     * Getter of upgradeUnitActionInfo field.
     *
     * @return an UpgradeTechActionInfo that contains detailed information of upgrade unit action.
     */
    public UpgradeUnitActionInfo getUpgradeUnitActionInfo() {
        return upgradeUnitActionInfo;
    }
}
