package edu.duke.ece651.risk.shared;

import java.util.HashMap;

public class PlayerInfo implements java.io.Serializable {

    private final String playerName;
    private final int playerId;
    private int techLevel;
    private Boolean isCloakingResearched;
    private HashMap<String, Integer> resTotals;

    /**
     * Makes the HashMap with initial resource amounts for the player. Pass makeResTotals() in
     * constructor of playerInfo.
     *
     * @return a HashMap with String key as the resource names, and int value of the initial
     *     amounts.
     */
    private static HashMap<String, Integer> makeResTotals(int foodInitAmt, int techInitAmt) {
        HashMap<String, Integer> resources = new HashMap<String, Integer>();
        resources.put("food", foodInitAmt);
        resources.put("tech", techInitAmt);
        return resources;
    }

    /**
     * Constructs a PlayerInfo object.
     *
     * @param playerName is the name of the player.
     * @param techLevel is the maximum technology level for the player.
     * @param resTotals is a HashMap that contains the total amounts of different resouces for the
     *     player, where the key is the name of the resource, and the value is the player's current
     *     amount of the resource.
     */
    public PlayerInfo(String playerName, int techLevel, HashMap<String, Integer> resTotals) {
        this.playerName = playerName;
        this.playerId = 0;
        this.techLevel = techLevel;
        this.resTotals = resTotals;
        this.isCloakingResearched = false;
    }

    /**
     * Constructs a PlayerInfo object, using makeResTotals() to set the initial resources amounts.
     *
     * @param playerName is the name of the player.
     * @param foodInitAmt is the initial amount of food resources given to the player.
     * @param techInitAmt is the initial amount of tech resouces given to the player.
     */
    public PlayerInfo(String playerName, int foodInitAmt, int techInitAmt) {
        this(playerName, 1, makeResTotals(foodInitAmt, techInitAmt));
    }

    /**
     * Constructs a PlayerInfo object.
     *
     * @param playerName is the name of the player.
     * @param playerId is the ID of the player.
     * @param techLevel is the maximum technology level for the player.
     * @param resTotals is a HashMap that contains the total amounts of different resouces for the
     *     player, where the key is the name of the resource, and the value is the player's current
     *     amount of the resource.
     */
    public PlayerInfo(
            String playerName, int playerId, int techLevel, HashMap<String, Integer> resTotals) {
        this.playerName = playerName;
        this.playerId = playerId;
        this.techLevel = techLevel;
        this.resTotals = resTotals;
        this.isCloakingResearched = false;
    }

    /**
     * Constructs a PlayerInfo object, using makeResTotals() to set the initial resources amounts.
     *
     * @param playerName is the name of the player.
     * @param playerId is the ID of the player.
     * @param foodInitAmt is the initial amount of food resources given to the player.
     * @param techInitAmt is the initial amount of tech resouces given to the player.
     */
    public PlayerInfo(String playerName, int playerId, int foodInitAmt, int techInitAmt) {
        this(playerName, playerId, 1, makeResTotals(foodInitAmt, techInitAmt));
    }

    /**
     * Getter of isCloakingResearched filed.
     *
     * @return true if the player has researched cloaking action, false other wise.
     */
    public Boolean getIsCloakingResearched() {
        return isCloakingResearched;
    }

    /**
     * Setter of isCloakingResearched filed.
     *
     * @param isCloakingResearched a Boolean represents is the new cloaking researched or not. Ture
     *     if we set the new field as the cloaking is researched, false otherwise.
     */
    public void setIfCloakingResearched(Boolean isCloakingResearched) {
        this.isCloakingResearched = isCloakingResearched;
    }

    /**
     * Gets the player name.
     *
     * @return a String representing the player's name.
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * Gets the current maximum technology level of the player.
     *
     * @return an int representing the current maximum technology level of the player.
     */
    public int getTechLevel() {
        return this.techLevel;
    }

    /**
     * Gets the current resource totals of the player.
     *
     * @return a HashMap where the keys are the resource names, and the values are the current
     *     resouce amounts owned by the player.
     */
    public HashMap<String, Integer> getResTotals() {
        return this.resTotals;
    }

    /**
     * Checks if a given tech level is valid for upgrade: 1) between 1 and 6 inclusive 2) more than
     * current tech level.
     *
     * @param toUpgrade is an int representing a technology level to check
     * @return true if tech level is valid, return false if not
     */
    public boolean isValidTechLevel(int toUpgrade) {
        return (toUpgrade >= 1 && toUpgrade <= 6 && toUpgrade > techLevel);
    }

    /**
     * Checks if a given unit level is valid for upgrade: 1) between 1 and 6 inclusive 2) less than
     * or equals current tech level.
     *
     * @param toUpgrade is an int representing the level to upgrade units to
     * @return true if tech level is valid, return false if not
     */
    public boolean isValidUnitLevel(int toUpgrade) {
        return (toUpgrade >= 1 && toUpgrade <= techLevel);
    }

    /**
     * Sets the player's current maximum technology level to a given value.
     *
     * @param newTechLevel is an int representing the player's new maximum technology level.
     */
    public void setTechLevel(int newTechLevel) {
        this.techLevel = newTechLevel;
    }

    /**
     * Updates the total amount of a specific resources with the given name by a given change
     * amount. The change amount can be positive or negative, indicating increase of decrease of the
     * resource amount respectively.
     *
     * @param resName is the name of the resource to update.
     * @param changeAmt is the amount to change the resource amount by.
     */
    public void updateOneResTotal(String resName, int changeAmt) {
        int currAmt = resTotals.get(resName);
        resTotals.put(resName, currAmt + changeAmt);
    }

    /**
     * Updates the total amount of multiple resources.
     *
     * @param toUpdate is a HashMap of the resources to update, where the keys are the names of the
     *     resources, and the vlaues are the change amount for each resource.
     */
    public void updateMultiResTotals(HashMap<String, Integer> toUpdate) {
        for (String resName : toUpdate.keySet()) {
            updateOneResTotal(resName, toUpdate.get(resName));
        }
    }

    public int getPlayerId() {
        return this.playerId;
    }
}
