package edu.duke.ece651.risk.server;

import java.util.ArrayList;

import org.apache.commons.lang3.SerializationUtils;
import edu.duke.ece651.risk.shared.*;

public class ServerOrderHelper {
    /**
     * The rule checker for move action.
     */
    private ActionRuleChecker moveChecker;

    /**
     * The rule checker for attack action.
     */
    private ActionRuleChecker attackChecker;

    /**
     * An ArrayList of ActionInfo that represents all information needed to execute
     * the attack action.
     */
    public ArrayList<ActionInfo> attackOrders;

    /**
     * An ArrayList of ActionInfo that represents all information needed to execute
     * the move action.
     */
    public ArrayList<ActionInfo> moveOrders;

    /**
     * The executer for execute attack and move actions.
     */
    private ActionExecuter executer;

    /**
     * Getter of attackOrders
     * 
     * @return an ArrayList of ActionInfo that represents all information needed to
     *         execute the attack action.
     */
    public ArrayList<ActionInfo> getAttackOrders() {
        return this.attackOrders;
    }

    /**
     * Getter of moveOrders.
     * 
     * @return an ArrayList of ActionInfo that represents all information needed to
     *         execute the move action.
     */
    public ArrayList<ActionInfo> getMoveOrders() {
        return this.moveOrders;
    }

    /**
     * Default constructor of the ServerOrderHelper.
     */
    public ServerOrderHelper() {
        this.moveChecker = new TerritoryExistenceRuleChecker(
                new SrcValidityRuleChecker(new DesReachableRuleChecker(null)));
        this.attackChecker = new TerritoryExistenceRuleChecker(
                new SrcValidityRuleChecker(new DesOwnershipRuleChecker(new DesAdjacencyRuleChecker(null))));
        this.attackOrders = new ArrayList<ActionInfo>();
        this.moveOrders = new ArrayList<ActionInfo>();
        this.executer = new ActionExecuter(); // default seed
    }

    /**
     * Contructs a ServerOrderHelper with a given random seed for executing attack
     * action.
     * 
     * @param seed the random seed for executing attack action.
     */
    public ServerOrderHelper(long seed) {
        this.moveChecker = new TerritoryExistenceRuleChecker(
                new SrcValidityRuleChecker(new DesReachableRuleChecker(null)));
        this.attackChecker = new TerritoryExistenceRuleChecker(
                new SrcValidityRuleChecker(new DesOwnershipRuleChecker(new DesAdjacencyRuleChecker(null))));
        this.attackOrders = new ArrayList<ActionInfo>();
        this.moveOrders = new ArrayList<ActionInfo>();
        this.executer = new ActionExecuter(seed);
    }

    /*
     * Clear all saved orders in the helper. Should be Calles after executes all
     * orders if this helper needs to be used for another round of orders.
     */
    public void clearAllOrders() {
        attackOrders = new ArrayList<ActionInfo>();
        moveOrders = new ArrayList<ActionInfo>();
    }

    /**
     * Collects orders got from all player clients.
     * 
     * @param orders the ObjectIO object that contains all orders.
     */
    public void collectOrders(ObjectIO orders) {
        for (ActionInfo info : orders.moveOrders) {
            moveOrders.add(info);
        }
        for (ActionInfo info : orders.attackOrders) {
            attackOrders.add(info);
        }
    }

    /**
     * Try to resolve all move orders in this helper. Orders will all be resolved if
     * there is no problem in the orders. If there is problem in at least one of the
     * orders, no order will be resolved.
     * 
     * @param map the WorldMap on which server executes the actions.
     * @return a String describing the problem in the orders. If there is no
     *         problem, returns null.
     */
    public String tryResolveMoveOrders(WorldMap map) {
        WorldMap temp = (WorldMap) SerializationUtils.clone(map); // temp map for checking move action
        String problem = null;
        for (ActionInfo order : moveOrders) {
            problem = moveChecker.checkAction(order, temp); // Do a check
            if (problem != null) {
                return problem;
            } else {
                executer.executeMove(temp, order); // execute a move on temp map
            }
        }
        // real executions
        for (ActionInfo order : moveOrders) {
            executer.executeMove(map, order);
        }
        return problem;
    }

    /**
     * Try to resolve all attack orders in this helper. Orders will all be resolved
     * if there is no problem in the orders. If there is problem in at least one of
     * the orders, no order will be resolved.
     * 
     * @param map the WorldMap on which server executes the actions.
     * @return a String describing the problem in the orders. If there is no
     *         problem, returns null.
     */
    public String tryResolveAttackOrders(WorldMap map) {
        WorldMap temp = (WorldMap) SerializationUtils.clone(map);
        String problem = null;
        for (ActionInfo order : attackOrders) {
            problem = attackChecker.checkAction(order, temp);
            if (problem != null) {
                return problem;
            } else {
                executer.sendTroops(temp, order);
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
