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

    /** The source Territory name of the action. */
    private String srcName;

    /** The destination Territory name of the action. */
    private String desName;

    /** The number of units in tech type of troop assigned to the action. */
    private HashMap<String, Integer> unitNum;

    /** The old tech level of the upgrade tech level action. */
    private Integer oldTechLevel;

    /** The new tech level of the upgrade tech level action. */
    private Integer newTechLevel;

    /**
     * Default constructor of ActionInfo.
     *
     * @param srcOwnerName is the owner's name of the source Territory.
     * @param actionType is a String represents the action type of the action.
     */
    public ActionInfo(String srcOwnerName, String actionType) {
        this.srcOwnerName = srcOwnerName;
        this.actionType = actionType;
        this.srcName = null;
        this.desName = null;
        this.unitNum = null;
        this.oldTechLevel = null;
        this.newTechLevel = null;
    }

    /**
     * Constructs a ActionInfo for move or attack action.
     *
     * @param srcOwnerName is the owner's name of the source Territory.
     * @param actionType is a String represents the action type of the action.
     * @param srcName is the source Territory name of the action.
     * @param desName is the destination Territory name of the action.
     * @param unitNum is the number of units in tech type of troop assigned to the action..
     */
    public ActionInfo(
            String srcOwnerName,
            String actionType,
            String srcName,
            String desName,
            HashMap<String, Integer> unitNum) {
        this.srcOwnerName = srcOwnerName;
        this.actionType = actionType;
        this.srcName = srcName;
        this.desName = desName;
        this.unitNum = unitNum;
        this.oldTechLevel = null;
        this.newTechLevel = null;
    }

    /**
     * Constructs a ActionInfo for upgrade units action.
     *
     * @param srcOwnerName is the owner's name of the source Territory.
     * @param actionType is a String represents the action type of the action.
     * @param srcName is the source Territory name of the action.
     * @param unitNum is the number of units number of units in tech type of troop assigned to the
     *     action.
     */
    public ActionInfo(
            String srcOwnerName,
            String actionType,
            String srcName,
            HashMap<String, Integer> unitNum) {
        this.srcOwnerName = srcOwnerName;
        this.actionType = actionType;
        this.srcName = srcName;
        this.desName = null;
        this.unitNum = unitNum;
        this.oldTechLevel = null;
        this.newTechLevel = null;
    }

    /**
     * Constructs a ActionInfo for upgrade tech level action.
     *
     * @param ownerName is the owner's name of the source Territory.
     * @param actionType is a String represents the action type of the action.
     * @param oldTechLevel is an Integer represents the old tech level for upgrade tech level field.
     * @param newTechLevel is an Integer represents the new tech level for upgrade tech level field.
     */
    public ActionInfo(
            String ownerName, String actionType, Integer oldTechLevel, Integer newTechLevel) {
        this.srcOwnerName = ownerName;
        this.actionType = actionType;
        this.srcName = null;
        this.desName = null;
        this.unitNum = null;
        this.oldTechLevel = oldTechLevel;
        this.newTechLevel = newTechLevel;
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
