package edu.duke.ece651.risk.shared;

public class UpgradeUnitActionInfo implements java.io.Serializable {
    /** The source territory name where the units to upgrade. */
    private String srcName;

    /** The old unit level for the upgrade unit action. */
    private int oldUnitLevel;

    /** The new unit level to the upgrade unit action. */
    private int newUnitLevel;

    /** The number of units to upgrade from the old level to the new level. */
    private int numToUpgrade;

    /**
     * Constructs a UpgradeUnitActionInfo.
     *
     * @param srcName is a String that represents the source territory where the units to upgrade.
     * @param oldUnitLevel is an int represents the old unit level for the upgrade unit action.
     * @param newUnitLevel is an int represents the new unit level to the upgrade unit action.
     * @param numToUpgrade is an int represents the number of units to upgrade from the old level to
     *     the new level.
     */
    public UpgradeUnitActionInfo(
            String srcName, int oldUnitLevel, int newUnitLevel, int numToUpgrade) {
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
     * @return an int represents the old unit level for the upgrade unit action.
     */
    public int getOldUnitLevel() {
        return oldUnitLevel;
    }

    /**
     * Setter of the oldUnitLevel field.
     *
     * @param oldUnitLevel an int represents the old unit level for the upgrade unit action.
     */
    public void setOldUnitLevel(int oldUnitLevel) {
        this.oldUnitLevel = oldUnitLevel;
    }

    /**
     * Getter of the newUnitLevel field.
     *
     * @return an int represents the new unit level to the upgrade unit action.
     */
    public int getNewUnitLevel() {
        return newUnitLevel;
    }

    /**
     * Setter of the newUnitLevel field.
     *
     * @param newUnitLevel an int represents the new unit level to the upgrade unit action.
     */
    public void setNewUnitLevel(int newUnitLevel) {
        this.newUnitLevel = newUnitLevel;
    }

    /**
     * Getter of the numToUpgrade field.
     *
     * @return an int represents the number of units to upgrade from the old level to * the new
     *     level.
     */
    public int getNumToUpgrade() {
        return numToUpgrade;
    }

    /**
     * Setter of the numToUpgrade field.
     *
     * @param numToUpgrade an int represents the number of units to upgrade from the old level to *
     *     the new level.
     */
    public void setNumToUpgrade(int numToUpgrade) {
        this.numToUpgrade = numToUpgrade;
    }
}
