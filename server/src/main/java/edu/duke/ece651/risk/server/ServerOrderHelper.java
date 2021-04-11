package edu.duke.ece651.risk.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.lang3.SerializationUtils;
import edu.duke.ece651.risk.shared.*;
import org.checkerframework.checker.units.qual.A;

public class ServerOrderHelper {
    /** The rule checker helper for actions. */
    private final ActionRuleCheckerHelper ruleChecker = new ActionRuleCheckerHelper();

    /**
     * An ArrayList of ActionInfo that represents all information needed to execute the attack
     * action.
     */
    public ArrayList<ActionInfo> attackOrders;

    /**
     * An ArrayList of ActionInfo that represents all information needed to execute the move action,
     * upgrade tech action, or upgrade unit action.
     */
    public ArrayList<ActionInfo> group1Orders;

    /** The executer for execute attack and move actions. */
    private final ActionExecuter executer;

    /**
     * Getter of attackOrders
     *
     * @return an ArrayList of ActionInfo that represents all information needed to execute the
     *     attack action.
     */
    public ArrayList<ActionInfo> getAttackOrders() {
        return this.attackOrders;
    }

    /**
     * Getter of group1Orders.
     *
     * <p>An ArrayList of ActionInfo that represents all information needed to execute the move
     * action, upgrade tech action, or upgrade unit action.
     */
    public ArrayList<ActionInfo> getGroup1Orders() {
        return this.group1Orders;
    }

    /** Default constructor of the ServerOrderHelper. */
    public ServerOrderHelper() {
        this.attackOrders = new ArrayList<ActionInfo>();
        this.group1Orders = new ArrayList<ActionInfo>();
        this.executer = new ActionExecuter(); // default seed
    }

    /**
     * Constructs a ServerOrderHelper with a given random seed for executing attack action.
     *
     * @param seed the random seed for executing attack action.
     */
    public ServerOrderHelper(long seed) {
        this.attackOrders = new ArrayList<ActionInfo>();
        this.group1Orders = new ArrayList<ActionInfo>();
        this.executer = new ActionExecuter(seed);
    }

    /*
     * Clear all saved orders in the helper. Should be Called after executes all
     * orders if this helper needs to be used for another round of orders.
     */
    public void clearAllOrders() {
        attackOrders = new ArrayList<ActionInfo>();
        group1Orders = new ArrayList<ActionInfo>();
    }

    /**
     * Collects orders got from all player clients.
     *
     * @param orders the ObjectIO object that contains all orders.
     */
    public void collectOrders(ObjectIO orders) {
        group1Orders.addAll(orders.moveOrders);
        attackOrders.addAll(orders.attackOrders);
    }

    /**
     * Checks the validity of orders in group1Order ArrayList on a given map (should be a temporary
     * cloned map of the original map), and returns the problem of the orders as a String.
     *
     * @param tempMap a given WorldMap map (should be a temporary cloned * map of the original map).
     * @return a String that describing the problem of the orders in group1 ArrayList. Or null if
     *     there is not any problem in the orders.
     */
    public String rehearseGroup1Orders(WorldMap tempMap) {
        for (ActionInfo order : group1Orders) {
            if (order.getActionType().equals("move")) {
                String problem = ruleChecker.checkRuleForMove(order, tempMap); // Do a check
                if (problem != null) {
                    return problem;
                } else {
                    executer.executeMove(tempMap, order); // execute a move on temp map
                }
            } else if (order.getActionType().equals("upgrade tech")) {
                String problem = ruleChecker.checkRuleForUpgradeTech(order, tempMap);
                if (problem != null) {
                    return problem;
                } else {
                    executer.executeUpgradeTech(tempMap, order);
                }
            } else {
                String problem = ruleChecker.checkRuleForUpgradeUnit(order, tempMap);
                if (problem != null) {
                    return problem;
                } else {
                    executer.executeUpgradeUnit(tempMap, order);
                }
            }
        }
        return null;
    }

    /**
     * Resolves the orders in group1Order ArrayList on a given map. Orders should be all valid and
     * checked by rehearseGroup1Orders().
     *
     * @param map a given WorldMap map to resolve the orders on.
     */
    public void resolveGroup1Orders(WorldMap map) {
        for (ActionInfo order : group1Orders) {
            if (order.getActionType().equals("move")) {
                executer.executeMove(map, order); // execute a move on temp map
            } else if (order.getActionType().equals("upgrade tech")) {
                executer.executeUpgradeTech(map, order);
            } else {
                executer.executeUpgradeUnit(map, order);
            }
        }
    }

    /**
     * Checks the validity of orders in attackOrder ArrayList on a given map (should be a temporary
     * cloned map of the original map), and returns the problem of the orders as a String.
     *
     * @param tempMap a given WorldMap map (should be a temporary cloned * map of the original map).
     * @return a String that describing the problem of the orders in attackOrder ArrayList. Or null
     *     if there is not any problem in the orders.
     */
    public String rehearseAttackOrders(WorldMap tempMap) {
        for (ActionInfo order : attackOrders) {
            String problem = ruleChecker.checkRuleForAttack(order, tempMap);
            if (problem != null) {
                return problem;
            } else {
                executer.executePreAttack(tempMap, order);
                // just send troops for checking, because attacker units cannot take part in
                // defending territory.
            }
        }
        return null;
    }

    /**
     * Resolves the orders in attackOrders ArrayList on a given map. Orders should be all valid and
     * checked by rehearseAttackOrders().
     *
     * @param map a given WorldMap map to resolve the orders on.
     */
    public void resolveAttackOrders(WorldMap map) {
        for (ActionInfo order : attackOrders) {
            executer.executePreAttack(map, order);
        }
        HashMap<String, ActionInfo> mergedAttackOrders = mergeAttackOrders(attackOrders);
        for (ActionInfo order : mergedAttackOrders.values()) {
            executer.executeAttack(map, order);
        }
    }

    /**
     * Merges attack orders, so that the attack action infos with the same srcOwnerName and desName
     * can be merged into one. This is to achieve merge troop feature.
     *
     * @param attackOrders an ArrayList of ActionInfo for attack actions.
     * @return A HashMap with values are the merged attack action orders.
     */
    public HashMap<String, ActionInfo> mergeAttackOrders(ArrayList<ActionInfo> attackOrders) {
        ActionInfoFactory af = new ActionInfoFactory();
        HashMap<String, ActionInfo> mergedAttackOrders = new HashMap<String, ActionInfo>();
        for (ActionInfo order : attackOrders) {
            String ownerAndDes = order.getSrcOwnerName() + order.getDesName();
            if (mergedAttackOrders.containsKey(ownerAndDes)) {
                HashMap<String, Integer> mergedTroop =
                        mergedAttackOrders.get(ownerAndDes).getNumUnits();
                order.getNumUnits()
                        .forEach((key, value) -> mergedTroop.merge(key, value, Integer::sum));
                ActionInfo mergedOrder =
                        af.createAttackActionInfo(
                                order.getSrcOwnerName(),
                                order.getSrcName(),
                                order.getDesName(),
                                mergedTroop);
                mergedAttackOrders.put(ownerAndDes, mergedOrder);
            } else {
                mergedAttackOrders.put(ownerAndDes, order);
            }
        }
        return mergedAttackOrders;
    }

    /**
     * Checks and resolves all orders collected in group1Orders and attackOrders. The orders will be
     * resolved on map only if all orders are valid.
     *
     * @param map a given WorldMap map to resolve the orders on.
     * @return a String describing one of the problem in orders if there are any problems. Or null
     *     if all orders are valid.
     */
    public String tryResolveAllOrders(WorldMap map) {
        WorldMap tempMap = (WorldMap) SerializationUtils.clone(map);
        String problem = null;
        problem = rehearseGroup1Orders(tempMap);
        if (problem != null) {
            return problem;
        }
        problem = rehearseAttackOrders(tempMap);
        if (problem != null) {
            return problem;
        }
        resolveGroup1Orders(map);
        resolveAttackOrders(map);
        return problem;
    }

    /**
     * Run this after resolved all orders after each turn. Sends both tech and food resources to
     * each player. And adds one level0 units to each territory on the map.
     *
     * @param map the map to send the credits.
     * @param playerNames the players names who play the game on the map.
     */
    public void sendCreditToPlayers(WorldMap map, ArrayList<String> playerNames) {
        // add resources for each player
        for (String playerName : playerNames) {
            int foodBonus = 0;
            int techBonus = 0;
            for (Territory territory : map.getPlayerTerritories(playerName).values()) {
                foodBonus = foodBonus + territory.getResProduction().get("food");
                techBonus = techBonus + territory.getResProduction().get("tech");
            }
            PlayerInfo info = map.getPlayerInfo(playerName);
            info.updateOneResTotal("food", foodBonus);
            info.updateOneResTotal("tech", techBonus);
        }
        // add level0 unit for each territory
        for (String territory : map.getMyTerritories()) {
            map.getTerritory(territory).tryAddTroopUnits("level0", 1);
        }
    }

    /**
     * Updates the visibility status for a single player.
     *
     * @param map the WorkMap to update status with.
     * @param playerName the player for whom to update the status.
     */
    public void updateVizStatus(WorldMap map, String playerName) {
        PlayerInfo toUpdate = map.getPlayerInfo(playerName);
        for (String territoryName : toUpdate.getAllVizStatus().keySet()) {
            Territory territory = map.getTerritory(territoryName);
            if (territory.getOwnerName().equals(playerName)) {
                // if player owns the territory, set visible
                toUpdate.setOneVizStatus(territoryName, true);
            } else if (territory.getSpyTroopNumUnits(playerName) > 0) {
                // if player has spy in the territory, set visible
                toUpdate.setOneVizStatus(territoryName, true);
            } else if (isPlayerAdjTerritory(map, playerName, territory)
                    && territory.getCloakingTurns() < 1) {
                // if territory is adjacent to one of the player's territory and no cloaking on that
                // territory, set visible
                toUpdate.setOneVizStatus(territoryName, true);
            } else {
                // set invisible
                toUpdate.setOneVizStatus(territoryName, false);
            }
        }
    }

    /**
     * Determines if the given territory is adjacent to one of the given player's territory.
     *
     * @param map the WorldMap to check the adjacency on.
     * @param playerName the player's name for whom to check if the given territory is adjacent to
     *     one of the player's territory.
     * @param targetTerritory the territory for which to check if it is adjacent to one of the given
     *     player's territory.
     * @return
     */
    private Boolean isPlayerAdjTerritory(
            WorldMap map, String playerName, Territory targetTerritory) {
        for (Territory myTerritory : map.getPlayerTerritories(playerName).values()) {
            if (myTerritory.isAdjacentTo(targetTerritory)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the visibility status for a list of players.
     *
     * @param map the WorkMap to update status with.
     * @param playerNames the players' names for which to update the status.
     * @param map
     * @param playerNames
     */
    public void updateMultiVizStatus(WorldMap map, ArrayList<String> playerNames) {
        for (String playerName : playerNames) {
            updateVizStatus(map, playerName);
        }
    }
}
