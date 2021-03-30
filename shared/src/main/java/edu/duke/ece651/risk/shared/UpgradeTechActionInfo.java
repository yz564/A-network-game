package edu.duke.ece651.risk.shared;

public class UpgradeTechActionInfo implements java.io.Serializable {

    /** The new tech level of the upgrade tech level action. */
    private Integer newTechLevel;

    /** Default constructor of UpgradeTechActionInfo. */
    public UpgradeTechActionInfo() {
        this.newTechLevel = null;
    }

    /**
     * Constructs a UpgradeTechActionInfo for upgrade tech level action.
     *
     * @param newTechLevel is an Integer represents the new tech level for upgrade tech level field.
     */
    public UpgradeTechActionInfo(Integer newTechLevel) {
        this.newTechLevel = newTechLevel;
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
     * Sets the newTechLevel field.
     *
     * @param newTechLevel an Integer represents the new tech level for upgrade tech level field.
     */
    public void setNewTechLevel(Integer newTechLevel) {
        this.newTechLevel = newTechLevel;
    }
}
