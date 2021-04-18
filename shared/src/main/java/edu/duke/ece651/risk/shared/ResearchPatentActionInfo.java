package edu.duke.ece651.risk.shared;

import java.util.ArrayList;

public class ResearchPatentActionInfo implements java.io.Serializable {
    /** The list of territories that player required to implement research patent. */
    private final ArrayList<String> targetTerritoryNames;

    /**
     * Constructs a ResearchPatentActionInfo with given target territory names.
     *
     * @param targetTerritoryNames is the list of territories that player required to implement
     *     research patent.
     */
    public ResearchPatentActionInfo(ArrayList<String> targetTerritoryNames) {
        this.targetTerritoryNames = targetTerritoryNames;
    }

    /**
     * Getter of targetTerritoryNames.
     *
     * @return a list of territories that player required to implement research patent.
     */
    public ArrayList<String> getTargetTerritoryNames() {
        return targetTerritoryNames;
    }
}
