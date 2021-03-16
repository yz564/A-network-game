package edu.duke.ece651.risk.shared;

/**
 * Information used in a move or an attack action.
 */
public class ActionInfo implements java.io.Serializable {

    /**
     * A generated serial version UID for this class.
     */
    private static final long serialVersionUID = 4297826252791846347L;

    /**
     * The source Territory name of the action.
     */
    private String srcName;

    /**
     * The destination Territory name of the action.
     */
    private String desName;

    /**
     * The number of units assigned to the action.
     */
    private int unitNum;

    /**
     * Default constructor of ActionInfo.
     */
    public ActionInfo() {
        this.srcName = null;
        this.desName = null;
        this.unitNum = 0;
    }

    /**
     * Constructes a ActionInfo
     * 
     * @param srcName is the source Territory name of the action.
     * @param desName is the destination Territory name of the action.
     * @param unitNum is the number of units assigned to the action.
     */
    public ActionInfo(String srcName, String desName, int unitNum) {
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
     * @return an int represents the number of units assigned to the action.
     */
    public int getUnitNum() {
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
     * @param desName a String represents the destination Territory name of the
     *                action.
     */
    public void setDesName(String desName) {
        this.desName = desName;
    }

    /**
     * Sets the unitNum.
     * 
     * @param unitNum an int represents the number of units assigned to the action.
     */
    public void setUnitNum(int unitNum) {
        this.unitNum = unitNum;
    }
}
