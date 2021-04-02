package edu.duke.ece651.risk.server;

import java.util.ArrayList;

import org.apache.commons.lang3.SerializationUtils;
import edu.duke.ece651.risk.shared.*;

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
    public ArrayList<ActionInfo> group1Orders() {
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

    private String rehearseGroup1Orders(WorldMap tempMap) {
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

    private void resolveGroup1Orders(WorldMap map) {
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
     * Try to resolve all group1 orders in this helper. Orders will all be resolved if there is no
     * problem in the orders. If there is problem in at least one of the orders, no order will be
     * resolved.
     *
     * @param map the WorldMap on which server executes the actions.
     * @return a String describing the problem in the orders. If there is no problem, returns null.
     */
    public String tryResolveMoveOrders(WorldMap map) {
        WorldMap temp =
                (WorldMap) SerializationUtils.clone(map); // temp map for checking move action
        String problem = null;
        for (ActionInfo order : group1Orders) {
            problem = ruleChecker.checkRuleForMove(order, temp); // Do a check
            if (problem != null) {
                return problem;
            } else {
                executer.executeMove(temp, order); // execute a move on temp map
            }
        }
        // real executions
        for (ActionInfo order : group1Orders) {
            executer.executeMove(map, order);
        }
        return problem;
    }

    /**
     * Try to resolve all attack orders in this helper method. Orders will all be resolved if there
     * is no problem in the orders. If there is problem in at least one of the orders, no order will
     * be resolved.
     *
     * @param map the WorldMap on which server executes the actions.
     * @return a String describing the problem in the orders. If there is no problem, returns null.
     */
    public String tryResolveAttackOrders(WorldMap map) {
        WorldMap temp = (WorldMap) SerializationUtils.clone(map);
        String problem = null;
        for (ActionInfo order : attackOrders) {
            problem = ruleChecker.checkRuleForAttack(order, temp);
            if (problem != null) {
                return problem;
            } else {
                executer.sendTroops(temp, order);
                executer.executeAttack(temp, order);
            }
        }
        // real executions
        for (ActionInfo order : attackOrders) {
            executer.sendTroops(map, order);
        }
        for (ActionInfo order : attackOrders) {
            executer.executeAttack(map, order);
        }
        return problem;
    }
}
