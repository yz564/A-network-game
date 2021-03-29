package edu.duke.ece651.risk.shared;

public class UpgradeTechActionInfo implements java.io.Serializable {
    /** The old tech level of the upgrade tech level action. */
    private Integer oldTechLevel;

    /** The new tech level of the upgrade tech level action. */
    private Integer newTechLevel;

    /** Default constructor of UpgradeTechActionInfo. */
    public UpgradeTechActionInfo() {
        this.oldTechLevel = null;
        this.newTechLevel = null;
    }

    /**
     * Constructs a UpgradeTechActionInfo for upgrade tech level action.
     *
     * @param oldTechLevel is an Integer represents the old tech level for upgrade tech level field.
     * @param newTechLevel is an Integer represents the new tech level for upgrade tech level field.
     */
    public UpgradeTechActionInfo(Integer oldTechLevel, Integer newTechLevel) {
        this.oldTechLevel = oldTechLevel;
        this.newTechLevel = newTechLevel;
    }

    /*
     * Getter of oldTechLevel field.
     *
     * @return an Integer represents the old tech level for upgrade tech level field.
     */
    public Integer getOldTechLevel() {
        return this.oldTechLevel;
    }

    /**
     * Getter of newTechLevel field.
     *
     * @return an Integer represents the new tech level for upgrade tech level field.
     */
    public Integer getNewTechLevel() {
        return this.newTechLevel;
    }

    /**
     * Sets the oldTechLevel field.
     *
     * @param oldTechLevel an Integer represents the old tech level for upgrade tech level field.
     */
    public void setOldTechLevel(Integer oldTechLevel) {
        this.oldTechLevel = oldTechLevel;
    }

    /**
     * Sets the newTechLevel field.
     *
     * @param newTechLevel an Integer represents the new tech level for upgrade tech level field.
     */
    public void setNewTechLevel(Integer newTechLevel) {
        this.newTechLevel = newTechLevel;
    }
}
