package edu.duke.ece651.risk.shared;

public class CloakingActionInfo implements java.io.Serializable {
    /** The String that represents the target territory, on which to do cloaking. */
    private String targetTerritoryName;

    /** The default constructor of CloakingActionInfo. */
    public CloakingActionInfo() {
        this.targetTerritoryName = null;
    }

    /**
     * Constructs an CloakingActionInfo with a given target territory name
     *
     * @param targetTerritoryName a String that represents the target territory, on which to do
     *     cloaking.
     */
    public CloakingActionInfo(String targetTerritoryName) {
        this.targetTerritoryName = targetTerritoryName;
    }

    /**
     * Getter of the targetTerritoryName.
     *
     * @return a String that represents the target territory, on which to do * cloaking.
     */
    public String getTargetTerritoryName() {
        return targetTerritoryName;
    }

    /**
     * Setter of the targetTerritoryName.
     *
     * @param targetTerritoryName a String that represents the target territory, on which to do *
     *     cloaking.
     */
    public void setTargetTerritoryName(String targetTerritoryName) {
        this.targetTerritoryName = targetTerritoryName;
    }
}
