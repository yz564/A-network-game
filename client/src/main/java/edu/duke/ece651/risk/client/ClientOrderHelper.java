package edu.duke.ece651.risk.client;

import edu.duke.ece651.risk.shared.ActionInfo;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.ArrayList;

import org.apache.commons.lang3.SerializationUtils;

import edu.duke.ece651.risk.shared.*;

public class ClientOrderHelper {
    /**
     * A String represtending the player's name who is placeing the order.
     */
    private String playerName;

    /**
     * A ClientIO object for printing world map and reading required string or int.
     */
    private ClientIO stdIO;

    /**
     * The rule checker for move action.
     */
    private ActionRuleChecker moveChecker;

    /**
     * The rule checker for attack action.
     */
    private ActionRuleChecker attackChecker;

    /**
     * The executer for execute attack and move actions.
     */
    private ActionExecuter executer;

    /**
     * An output stream that prints out text to client.
     */
    private PrintStream out;

    /**
     * Constructs a ClientOrderhelper with the gievn client player's name, given
     * text input and output stream. And set decault random seed for executing
     * attack action.
     * 
     * @param playerName  a String represtending the player's name who is placeing
     *                    the order.
     * @param inputReader a input stream that takes text input from client.
     * @param out         an output stream that prints out text to client.
     */
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

    /**
     * Constructs a ClientOrderhelper with the gievn client player's name, given
     * text input and output stream ,and a given random seed for executing attack
     * action.
     * 
     * @param playerName  a String represtending the player's name who is placeing
     *                    the order.
     * @param inputReader a input stream that takes text input from client.
     * @param out         an output stream that prints out text to client.
     * @param seed        the random seed for executing attack action.
     */
    public ClientOrderHelper(String playerName, BufferedReader inputReader, PrintStream out, long seed) {
        this.playerName = playerName;
        this.stdIO = new ClientTextIO(inputReader, out);
        this.moveChecker = new TerritoryExistenceRuleChecker(
                new SrcValidityRuleChecker(new DesReachableRuleChecker(null)));
        this.attackChecker = new TerritoryExistenceRuleChecker(
                new SrcValidityRuleChecker(new DesOwnershipRuleChecker(new DesAdjacencyRuleChecker(null))));
        this.out = out;
        this.executer = new ActionExecuter(seed);
    }

    /**
     * Asks for action orders information from the client player, checks the order,
     * and issues the order to the ObjectIO that takes the order.
     * 
     * @param map         the WroldMap to implement the order.
     * @param playerNames the list of all players name in the game.
     * @return an OjbectIO that takes the issued action order.
     */
    public ObjectIO issueActionOrders(WorldMap map, ArrayList<String> playerNames) {
        WorldMap temp = (WorldMap) SerializationUtils.clone(map); // temp map for checking move action
        ObjectIO orders = new ObjectIO();
        String newOrderType;
        out.println("You may order Move actions now then order Attack actions.\n"
                + "Or you may skip ordering Move actions by ordering Attack action directly.");
        while (!((newOrderType = stdIO.readActionName(playerName)).equals("D"))) {
            if (newOrderType.equals("M")) {
                ActionInfo newOrder = readMoveOrder();
                String problem = moveChecker.checkAction(newOrder, temp);
                if (problem != null) {
                    out.println(problem);
                    out.println("You may order action again.");
                } else {
                    orders.moveOrders.add(newOrder);
                    executer.executeMove(temp, newOrder);
                    out.println("Your Move action order is taken.");
                    stdIO.printMap(new MapTextView(playerNames), temp, playerNames);
                }
            } else { // done with move action, go for attack action
                break;
            }
        }
        out.println("You may order Attack actions now.");
        while (!((newOrderType = stdIO.readActionName(playerName)).equals("D"))) {
            if (newOrderType.equals("A")) {
                // Attack order
                ActionInfo newOrder = readAttackOrder();
                String problem = attackChecker.checkAction(newOrder, temp);
                if (problem != null) {
                    out.println(problem);
                    out.println("You may order action again.");
                } else {
                    orders.attackOrders.add(newOrder);
                    out.println("Your Action action order is taken.");
                }
            } else {
                out.println("You can only order Attack actions now. Move orders are all set.");
                out.println("You may order action again.");
            }
        }
        return orders;
    }

    /**
     * Reads a attack order from stdIO.
     * 
     * @return an ActionInfo object that contains the information needed for the
     *         attack new order.
     */
    private ActionInfo readAttackOrder() {
        ActionInfo newOrder = new ActionInfo(playerName);
        String srcName = stdIO.readTerritoryName("What territory do you want to send your unit(s) from?");
        String desName = stdIO.readTerritoryName("What territory do you want to attack?");
        int unitNum = stdIO.readNumUnits("How many units do you want to send for this attck?");
        newOrder.setSrcName(srcName);
        newOrder.setDesName(desName);
        newOrder.setUnitNum(unitNum);
        return newOrder;
    }

    /**
     * Reads a move order from stdIO.
     * 
     * @return an ActionInfo object that contains the information needed for the
     *         move new order.
     */
    private ActionInfo readMoveOrder() {
        ActionInfo newOrder = new ActionInfo(playerName);
        String srcName = stdIO.readTerritoryName("What territory do you want to move your unit(s) from?");
        String desName = stdIO.readTerritoryName("What territory do you want to move your unit(s) to?");
        int unitNum = stdIO.readNumUnits("How many units do you want to Move?");
        newOrder.setSrcName(srcName);
        newOrder.setDesName(desName);
        newOrder.setUnitNum(unitNum);
        return newOrder;
    }

    /**
     * Asks for initial placement orders information from the client player, checks
     * the order and issues the order to the ObjectIO that takes the order.
     * 
     * @param totalUnitNum   is an int that represents the total unmber of unit to
     *                       place on territories.
     * @param territoryNames is an ArrayList of String represents all territoies'
     *                       name on which to place units.
     * @return an OjbectIO that takes the issued placement order.
     */
    public ObjectIO issuePlaceOrders(int totalUnitNum, ArrayList<String> territoryNames) {
        ObjectIO orders = new ObjectIO();
        out.println("You may place your units now.\n");
        int territoryNum = territoryNames.size();
        for (String territoryName : territoryNames) {
            while (true) {
                out.println("You have <" + totalUnitNum + "> unit(s) to place.");
                out.println("You have <" + territoryNum + "> territory(ies) to place unit(s).");
                Integer toPlace = stdIO.readNumUnits("How many units you want to place at <" + territoryName + ">?");
                if (toPlace > totalUnitNum) {
                    out.println("Invalid input: You have  only <" + totalUnitNum
                            + "> unit(s) to place, but you want to place <" + toPlace + "> units.");
                    out.println("You may place again.");
                } else if (totalUnitNum - toPlace < territoryNum - 1) {
                    out.println(
                            "Invalid input: You have to leave at least one unit for the rest of territory (territories).");
                    out.println("You may place again.");
                } else { // successful order
                    orders.placeOrders.put(territoryName, toPlace);
                    totalUnitNum = totalUnitNum - toPlace;
                    territoryNum = territoryNum - 1;
                    out.println("Your placement order is taken.");
                    break;
                }
            }
        }
        return orders;
    }

}
