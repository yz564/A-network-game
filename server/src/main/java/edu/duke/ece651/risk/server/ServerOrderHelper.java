package edu.duke.ece651.risk.server;

import java.util.ArrayList;

import org.apache.commons.lang3.SerializationUtils;
import edu.duke.ece651.risk.shared.*;

public class ServerOrderHelper {
    private ActionRuleChecker moveChecker;
    private ActionRuleChecker attackChecker;
    public ArrayList<ActionInfo> attackOrders;
    public ArrayList<ActionInfo> moveOrders;
    private ActionExecuter executer;

    public ServerOrderHelper() {
        this.moveChecker = new TerritoryExistenceRuleChecker(
                new SrcValidityRuleChecker(new DesReachableRuleChecker(null)));
        this.attackChecker = new TerritoryExistenceRuleChecker(
                new SrcValidityRuleChecker(new DesOwnershipRuleChecker(new DesAdjacencyRuleChecker(null))));
        this.attackOrders = new ArrayList<ActionInfo>();
        this.moveOrders = new ArrayList<ActionInfo>();
        this.executer = new ActionExecuter(); // default seed
    }

    public ServerOrderHelper(long seed) {
        this.moveChecker = new TerritoryExistenceRuleChecker(
                new SrcValidityRuleChecker(new DesReachableRuleChecker(null)));
        this.attackChecker = new TerritoryExistenceRuleChecker(
                new SrcValidityRuleChecker(new DesOwnershipRuleChecker(new DesAdjacencyRuleChecker(null))));
        this.attackOrders = new ArrayList<ActionInfo>();
        this.moveOrders = new ArrayList<ActionInfo>();
        this.executer = new ActionExecuter(seed);
    }

    public void clearAllOrders() {
        attackOrders = new ArrayList<ActionInfo>();
        moveOrders = new ArrayList<ActionInfo>();
    }

    public void collectOrders(ObjectIO orders) {
        for (ActionInfo info : orders.moveOrders) {
            moveOrders.add(info);
        }
        for (ActionInfo info : orders.attackOrders) {
            attackOrders.add(info);
        }
    }

    public String tryResolveMoveOrders(WorldMap map) {
        WorldMap temp = (WorldMap) SerializationUtils.clone(map);
        String problem = null;
        for (ActionInfo order : moveOrders) {
            problem = moveChecker.checkAction(order, map); // Do a check
            if (problem != null) {
                map = temp; // recover the map if found a problem
                break;
            } else {
                executer.executeMove(map, order); // execute a move
            }
        }
        return problem;
    }

    public String tryResolveAttackOrders(WorldMap map) {
        String problem = null;
        for (ActionInfo order : attackOrders) {
            problem = attackChecker.checkAction(order, map);
            if (problem != null) {
                return problem;
            }
        }
        for (ActionInfo order : attackOrders) {
            executer.sendTroops(map, order);
        }
        for (ActionInfo order : attackOrders) {
            executer.executeAttack(map, order);
        }
        return problem;
    }

}
