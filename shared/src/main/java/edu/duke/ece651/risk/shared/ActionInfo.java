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

    /** The detailed cloaking action info. */
    private CloakingActionInfo cloakingActionInfo;

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
     * Constructs an ActionInfo with given CloakingActionInfo.
     *
     * @param srcOwnerName is the owner's name of the source Territory.
     * @param actionType is a String represents the action type of the action.
     * @param cloakingActionInfo is a CloakingActionInfo that contains detailed information of
     *     cloaking action.
     */
    public ActionInfo(
            String srcOwnerName, String actionType, CloakingActionInfo cloakingActionInfo) {
        this.srcOwnerName = srcOwnerName;
        this.actionType = actionType;
        this.cloakingActionInfo = cloakingActionInfo;
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
     * @return a TerritoryActionInfo that contains detailed info of move or attack action.
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

    /**
     * Getter of cloakingActionInfo field.
     *
     * @return a CloakingActionInfo that contains detailed information of * cloaking action.
     */
    public CloakingActionInfo getCloakingActionInfo() {
        return cloakingActionInfo;
    }

    /**
     * Getter of srcName field in either TerritoryActionInfo or UpgradeUnitActionInfo or
     * CloakingActionInfo objects.
     *
     * @return a String that represents the source territory name, null for other action types.
     */
    public String getSrcName() {
        if (territoryActionInfo != null) {
            return territoryActionInfo.getSrcName();
        }
        if (upgradeUnitActionInfo != null) {
            return upgradeUnitActionInfo.getSrcName();
        }
        if (cloakingActionInfo != null) {
            return cloakingActionInfo.getTargetTerritoryName();
        }
        return null;
    }

    /**
     * Getter of srcName field in TerritoryActionInfo objects.
     *
     * @return a String that represents the destination territory name, null for other action types.
     */
    public String getDesName() {
        if (territoryActionInfo != null) {
            return territoryActionInfo.getDesName();
        }
        return null;
    }

    /**
     * Getter of uniNum in TerritoryActionInfo and combines the oldUnitLevel and numToUpgrade fields
     * in UpgradeUnitActionInfo objects.
     *
     * @return a HashMap where key is String representing the troop name and value is int
     *     representing the number of units of the troop to perform action on, null for other action
     *     types.
     */
    public HashMap<String, Integer> getNumUnits() {
        if (territoryActionInfo != null) {
            return territoryActionInfo.getUnitNum();
        }
        if (upgradeUnitActionInfo != null) {
            HashMap<String, Integer> numUnits = new HashMap<String, Integer>();
            numUnits.put(
                    upgradeUnitActionInfo.getOldUnitLevel(),
                    upgradeUnitActionInfo.getNumToUpgrade());
            numUnits.put(upgradeUnitActionInfo.getNewUnitLevel(), 0);
            return numUnits;
        }
        return null;
    }

    /**
     *
     */    
    public int getNumSpyUnits() {
          if (territoryActionInfo != null) {
              return territoryActionInfo.getSpyUnitNum();
          }
          if (upgradeUnitActionInfo != null) {
              return upgradeUnitActionInfo.getNumToUpgrade();
          }
          return 0;
      }
    
      /**
       * Getter of oldUnitLevel field in UpgradeUnitActionInfo objects.
       *
       * @return a String that represents the old level troop name to upgrade units from, null for
       *     other action types.
       */
    public String getOldUnitLevel() {
        if (upgradeUnitActionInfo != null) {
            return upgradeUnitActionInfo.getOldUnitLevel();
        }
        return null;
    }

    /**
     * Getter of newUnitLevel field in UpgradeUnitActionInfo objects.
     *
     * @return a String that represents the new level troop name to upgrade units to, null for other
     *     action types.
     */
    public String getNewUnitLevel() {
        if (upgradeUnitActionInfo != null) {
            return upgradeUnitActionInfo.getNewUnitLevel();
        }
        return null;
    }

    /**
     * Getter of newTechLevel field in UpgradeTechActionInfo objects.
     *
     * @return a int that represents the new Tech level to upgrade to, 0 for other action types.
     */
    public int getNewTechLevel() {
        if (upgradeTechActionInfo != null) {
            return upgradeTechActionInfo.getNewTechLevel();
        }
        return 0;
    }

    public int getTotalNumUnits() {
        int total = 0;
        if (territoryActionInfo != null || upgradeUnitActionInfo != null) {
            for (int numUnit : getNumUnits().values()) {
                total += numUnit;
            }
        }
        return total;
    }
}
