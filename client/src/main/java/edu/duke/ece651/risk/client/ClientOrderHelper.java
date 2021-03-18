package edu.duke.ece651.risk.client;

import edu.duke.ece651.risk.shared.ActionInfo;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.ArrayList;

import org.apache.commons.lang3.SerializationUtils;

import edu.duke.ece651.risk.shared.*;

public class ClientOrderHelper {
    private String playerName;
    private ClientIO stdIO;
    private ActionRuleChecker moveChecker;
    private ActionRuleChecker attackChecker;
    private ActionExecuter executer;
    private PrintStream out;

    public ClientOrderHelper(String playerName, BufferedReader inputReader, PrintStream out) {
        this.playerName = playerName;
        this.stdIO = new ClientTextIO(inputReader, out);
        this.moveChecker = new TerritoryExistenceRuleChecker(
                new SrcValidityRuleChecker(new DesReachableRuleChecker(null)));
        this.attackChecker = new TerritoryExistenceRuleChecker(
                new SrcValidityRuleChecker(new DesOwnershipRuleChecker(new DesAdjacencyRuleChecker(null))));
        this.executer = new ActionExecuter(); // default seed
        this.out = out;
    }

    public ClientOrderHelper(String playerName, BufferedReader inputReader, PrintStream out, long seed) {
        this.playerName = playerName;
        this.stdIO = new ClientTextIO(inputReader, out);
        this.moveChecker = new TerritoryExistenceRuleChecker(
                new SrcValidityRuleChecker(new DesReachableRuleChecker(null)));
        this.attackChecker = new TerritoryExistenceRuleChecker(
                new SrcValidityRuleChecker(new DesOwnershipRuleChecker(new DesAdjacencyRuleChecker(null))));
        this.executer = new ActionExecuter(seed);
        this.out = out;
    }

    public ObjectIO issueOrders(WorldMap map, ArrayList<String> playerNames) {
        WorldMap temp = (WorldMap) SerializationUtils.clone(map); // temp map for checking move action
        ObjectIO orders = new ObjectIO();
        String newOrderType;
        out.println("You may order Move actions now then order Attack actions.\n"
                + "Or you may skip ordering Move actions by ordering Attack action directly.");
        while ((newOrderType = stdIO.readActionName(playerName)) != "D") {
            ActionInfo newOrder = new ActionInfo(playerName);
            if (newOrderType == "M") {
                String srcName = stdIO.readTerritoryName("What territory do you want to move your unit(s) from?");
                String desName = stdIO.readTerritoryName("What territory do you want to move your unit(s) to?");
                int unitNum = stdIO.readNumUnits("How many units do you want to Move?");
                newOrder.setSrcName(srcName);
                newOrder.setDesName(desName);
                newOrder.setUnitNum(unitNum);
                String problem = moveChecker.checkAction(newOrder, temp);
                if (problem != null) {
                    out.println(problem);
                    out.println("You may order action again.");
                } else {
                    orders.moveOrders.add(newOrder);
                    executer.executeMove(temp, newOrder);
                    out.println("Your order is taken.");
                    stdIO.printMap(temp, playerNames);
                }
            } else { // done with move action, go for attack action
                break;
            }
        }
        out.println("You may order Attack actions now.");
        while ((newOrderType = stdIO.readActionName(playerName)) != "D") {
            ActionInfo newOrder = new ActionInfo(playerName);
            if (newOrderType == "A") {
                // Attack order
                String srcName = stdIO.readTerritoryName("What territory do you want to send your unit(s) from?");
                String desName = stdIO.readTerritoryName("What territory do you want to attack?");
                int unitNum = stdIO.readNumUnits("How many units do you want to send for this attck?");
                newOrder.setSrcName(srcName);
                newOrder.setDesName(desName);
                newOrder.setUnitNum(unitNum);
                String problem = attackChecker.checkAction(newOrder, temp);
                if (problem != null) {
                    out.println(problem);
                    out.println("You may order action again.");
                } else {
                    orders.attackOrders.add(newOrder);
                    out.println("Your order is taken.");
                }
            } else {
                out.println("You can only order Attack actions now. Move orders are down.");
            }
        }
        return orders;
    }

}
