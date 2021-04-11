package edu.duke.ece651.risk.shared;

public class CloakingActionInfo implements java.io.Serializable {
    private String targetTerritoryName;

    public CloakingActionInfo() {
        this.targetTerritoryName = null;
    }

    public CloakingActionInfo(String targetTerritoryName) {
        this.targetTerritoryName = targetTerritoryName;
    }

    public String getTargetTerritoryName() {
        return targetTerritoryName;
    }

    public void setTargetTerritoryName(String targetTerritoryName) {
        this.targetTerritoryName = targetTerritoryName;
    }
}
