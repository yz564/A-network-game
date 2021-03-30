package edu.duke.ece651.risk.shared;

public class UpgradeUnitActionInfo implements java.io.Serializable {
    /** The source territory name where the units to upgrade. */
    private String srcName;

    /** The old unit level for the upgrade unit action. */
    private String oldUnitLevel;

    /** The new unit level to the upgrade unit action. */
    private String newUnitLevel;

    /** The number of units to upgrade from the old level to the new level. */
    private Integer numToUpgrade;

    /** Default constructor of the UpgradeUnitActionInfo. */
    public UpgradeUnitActionInfo() {
        this.srcName = null;
        this.oldUnitLevel = null;
        this.newUnitLevel = null;
        this.numToUpgrade = null;
    }

    /**
     * Constructs a UpgradeUnitActionInfo.
     *
     * @param srcName is a String that represents the source territory where the units to upgrade.
     * @param oldUnitLevel is a String represents the old unit level for the upgrade unit action.
     * @param newUnitLevel is a String represents the new unit level to the upgrade unit action.
     * @param numToUpgrade is a String represents the number of units to upgrade from the old level to
     *     the new level.
     */
    public UpgradeUnitActionInfo(
            String srcName, String oldUnitLevel, String newUnitLevel, Integer numToUpgrade) {
        this.srcName = srcName;
        this.oldUnitLevel = oldUnitLevel;
        this.newUnitLevel = newUnitLevel;
        this.numToUpgrade = numToUpgrade;
    }

    /**
     * Getter of the source territory name srcName String.
     *
     * @return a String that represents the source territory where the units to upgrade.
     */
    public String getSrcName() {
        return srcName;
    }

    /**
     * Setter of the source territory name srcName String.
     *
     * @param srcName a String that represents the source territory where the units to upgrade.
     */
    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }

    /**
     * Getter of the oldUnitLevel filed.
     *
     * @return a String represents the old unit level for the upgrade unit action.
     */
    public String getOldUnitLevel() {
        return oldUnitLevel;
    }

    /**
     * Setter of the oldUnitLevel field.
     *
     * @param oldUnitLevel a String represents the old unit level for the upgrade unit action.
     */
    public void setOldUnitLevel(String oldUnitLevel) {
        this.oldUnitLevel = oldUnitLevel;
    }

    /**
     * Getter of the newUnitLevel field.
     *
     * @return a String represents the new unit level to the upgrade unit action.
     */
    public String getNewUnitLevel() {
        return newUnitLevel;
    }

    /**
     * Setter of the newUnitLevel field.
     *
     * @param newUnitLevel a String represents the new unit level to the upgrade unit action.
     */
    public void setNewUnitLevel(String newUnitLevel) {
        this.newUnitLevel = newUnitLevel;
    }

    /**
     * Getter of the numToUpgrade field.
     *
     * @return an int represents the number of units to upgrade from the old level to * the new
     *     level.
     */
    public Integer getNumToUpgrade() {
        return numToUpgrade;
    }

    /**
     * Setter of the numToUpgrade field.
     *
     * @param numToUpgrade an int represents the number of units to upgrade from the old level to *
     *     the new level.
     */
    public void setNumToUpgrade(Integer numToUpgrade) {
        this.numToUpgrade = numToUpgrade;
    }
}
