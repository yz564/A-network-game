package edu.duke.ece651.risk.shared;

import java.util.HashMap;

public class TerritoryActionInfo implements java.io.Serializable {

    /** The source Territory name of the action. */
    private String srcName;

    /** The destination Territory name of the action. */
    private String desName;

    /** The number of units in tech type of troop assigned to the action. */
    private HashMap<String, Integer> unitNum;

    /** Default constructor of TerritoryActionInfo. */
    public TerritoryActionInfo() {
        this.srcName = null;
        this.desName = null;
        this.unitNum = null;
    }

    /**
     * Constructs a TerritoryActionInfo for move or attack action.
     *
     * @param srcName is the source Territory name of the action.
     * @param desName is the destination Territory name of the action.
     * @param unitNum is the number of units in tech type of troop assigned to the action..
     */
    public TerritoryActionInfo(String srcName, String desName, HashMap<String, Integer> unitNum) {
        this.srcName = srcName;
        this.desName = desName;
        this.unitNum = unitNum;
    }

    /**
     * Getter of srcName field.
     *
     * @return a String represents the source Territory name of the action.
     */
    public String getSrcName() {
        return this.srcName;
    }

    /**
     * Getter of desName field.
     *
     * @return a String represents the destination Territory name of the action.
     */
    public String getDesName() {
        return this.desName;
    }

    /**
     * Getter of unitNum field.
     *
     * @return an int represents the number of units in tech type of troop assigned to the action.
     */
    public HashMap<String, Integer> getUnitNum() {
        return this.unitNum;
    }

    /**
     * Sets the srcName.
     *
     * @param srcName a String represents the source Territory name of the action.
     */
    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }

    /**
     * Sets the desName.
     *
     * @param desName a String represents the destination Territory name of the action.
     */
    public void setDesName(String desName) {
        this.desName = desName;
    }

    /**
     * Sets the unitNum.
     *
     * @param unitNum an int represents number of units in tech type of troop assigned to the
     *     action.
     */
    public void setUnitNum(HashMap<String, Integer> unitNum) {
        this.unitNum = unitNum;
    }
}
