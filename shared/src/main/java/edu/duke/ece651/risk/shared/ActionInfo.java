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
    private String disName;

    /**
     * The number of units assigned to the action.
     */
    private int unitNum;

    /**
     * Default constructor of ActionInfo.
     */
    public ActionInfo() {
        this.srcName = null;
        this.disName = null;
        this.unitNum = 0;
    }

    /**
     * Constructes a ActionInfo
     * 
     * @param srcName is the source Territory name of the action.
     * @param disName is the destination Territory name of the action.
     * @param unitNum is the number of units assigned to the action.
     */
    public ActionInfo(String srcName, String disName, int unitNum) {
        this.srcName = srcName;
        this.disName = disName;
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
     * Getter of disName field.
     * 
     * @return a String represents the destination Territory name of the action.
     */
    public String getDisName() {
        return this.disName;
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
     * Sets the disName.
     * 
     * @param disName a String represents the destination Territory name of the
     *                action.
     */
    public void setDisName(String disName) {
        this.disName = disName;
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
