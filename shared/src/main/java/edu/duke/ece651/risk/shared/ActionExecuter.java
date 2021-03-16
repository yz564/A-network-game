package edu.duke.ece651.risk.shared;

public class ActionExecuter {
    /**
     * Constructes a ActionExecuter helper class.
     */
    public ActionExecuter() {
    }

    /**
     * Sends troops only from the src Territory, troops don't arrive dis Territory.
     * 
     * Info of src, dis, and troop to send is in ActionInfo info argument.
     * 
     * Validity of this action should be checked by rule checker, so this method
     * doesn't throw error.
     * 
     * @param map  a WorldMap object where the action is implemented.
     * @param info a ActionInfo object that contains the information of src, dis,
     *             and troop to send.
     */
    public void sendTroops(WorldMap map, ActionInfo info) {
        Territory src = map.getTerritory(info.getSrcName());
        int sendNum = info.getUnitNum();
        src.trySetNumUnits(src.getNumUnits() - sendNum);
    }

    /**
     * Moves troops from the src Territory to dis Territory.
     * 
     * Info of src, dis, and troop to send is in ActionInfo info argument.
     * 
     * Validity of this action should be checked by rule checker, so this method
     * doesn't throw error.
     * 
     * @param map  a WorldMap object where the action is implemented.
     * @param info a ActionInfo object that contains the information of src, dis,
     *             and troop to send.
     */
    public void executeMove(WorldMap map, ActionInfo info) {
        Territory src = map.getTerritory(info.getSrcName());
        Territory des = map.getTerritory(info.getDesName());
        int sendNum = info.getUnitNum();
        src.trySetNumUnits(src.getNumUnits() - sendNum);
        des.trySetNumUnits(des.getNumUnits() + sendNum);
    }

}
