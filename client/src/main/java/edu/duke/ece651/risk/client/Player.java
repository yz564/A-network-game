package edu.duke.ece651.risk.client;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import edu.duke.ece651.risk.shared.*;

public class Player implements Runnable {
    private int id;
    private String name;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ObjectIO tmp;
    private String tmpS;
    private BufferedReader stdIn;
    public Boolean wait;
    public Boolean ready;
    private int maxUnitsToPlace;
    private WorldMap theMap;
    private final ActionRuleCheckerHelper ruleChecker = new ActionRuleCheckerHelper();
    private final ActionCostCalculator costCal = new ActionCostCalculator();
    private final ActionExecuter executer = new ActionExecuter();
    private ArrayList<ActionInfo> tmpOrders;
    private HashMap<String, Boolean> isLimitedActionUsed;
    private String gameOverMessage;
    private HashMap<String, TerritoryInfo> territoriesInfo;

    public Player(int id, ObjectInputStream in, ObjectOutputStream out, BufferedReader stdIn) {
        this.id = id;
        this.in = in;
        this.out = out;
        this.stdIn = stdIn;
        this.tmpS = null;
        this.wait = false;
        this.ready = false;
        this.maxUnitsToPlace = 30;
        this.tmpOrders = new ArrayList<ActionInfo>();
        this.isLimitedActionUsed = new HashMap<String, Boolean>();
        isLimitedActionUsed.put("upgrade tech", false);
        isLimitedActionUsed.put("move spy", false);
        isLimitedActionUsed.put("research patent", false);
        territoriesInfo = new HashMap<>();
    }

    public HashMap<String, Boolean> getIsLimitedActionUsed() {
        return isLimitedActionUsed;
    }

    public String waitOtherPlayers() throws Exception {
        sendMessage(new ObjectIO("wait others", 0));
        tmp = (ObjectIO) in.readObject();
        System.out.println(tmp.message);
        return tmp.message;
    }

    public String getGameOverMessage() {
        return gameOverMessage;
    }

    public int getRoomId() {
        quitRoom();
        return id;
    }

    private void quitRoom() {
        try {
            sendMessage(new ObjectIO("/gameOver"));
        } catch (Exception e) {
        }
    }

    public void setName(String n) {
        this.name = n;
    }

    public String getName() {
        return name;
    }

    public WorldMap getMap() {
        return theMap;
    }

    private void updateMap() {
        // theMap = (WorldMap) SerializationUtils.clone(tmp.map);
        theMap = tmp.map;
    }

    public void setWait(Boolean b) {
        wait = b;
    }

    public Boolean isWait() {
        return wait;
    }

    public void updateInput(String s) {
        tmpS = s;
    }

    public void receiveMessage() throws Exception {
        tmp = (ObjectIO) in.readObject();
        updateMap();
        updateTerritoryInfo();
        // return (ObjectIO) in.readObject();
    }

    public void sendMessage(ObjectIO info) throws Exception {
        out.writeObject(info);
        out.flush();
        out.reset();
    }

    public void startInitialization() throws Exception {
        receiveMessage();
    }

    public boolean tryInitialization(String info) throws Exception {
        if (tmp.groups.contains(Integer.parseInt(info))) {
            sendMessage(new ObjectIO(info, Integer.parseInt(info)));
            receiveMessage();
            if (tmp.id == 0) {
                // receiveMessage();
                // this.territoryGroupSelected = info;
                // System.out.println(theMap.getPlayerTerritories(name));
                return true;
            }
        }
        return false;
    }

    /**
     * first wait to read the ObjectIO sent by the server. then let the user to select the available
     * group finally send the ObjectIO with the selection to the server.
     */
    /*
    public void doInitialization() throws Exception {
      while (true) {
        tmpS = stdIn.readLine();
        String str = tryInitialization(tmpS);
        System.out.println(str);
        if (str == null) {
          break;
        }

      }
    }*/
    /*
      public void doInitialization(Boolean b) throws Exception {
          if (b) {
              if ((tmp = (ObjectIO) in.readObject()) != null) {}
          }
          mark:
          while (true) {
              wait = true; // let the main thread to listen input just once
              while (!ready) {}
              wait = false;
              ready = false;
              tmpS = null;
              System.out.println(tmp.message);
              System.out.println("(you can leave the game by /leave) Your available choices are: ");
              Iterator<Integer> itr = (tmp.groups).iterator();
              while (itr.hasNext()) {
                  Integer g = (Integer) itr.next();
                  System.out.println(Integer.toString(g) + " : " + tmp.map.getInitGroup(g));
              }
              // if ((tmpS = stdIn.readLine()) != null) {}
              while (tmpS == null) {
                  if (wait) {
                      continue mark;
                  }
              }
              try {
                  if (tmp.groups.contains(Integer.parseInt(tmpS))) {
                      break;
                  }
              } catch (NumberFormatException e) {
                  System.out.println("Input should be a number, please retry");
              }
              System.out.println("Your input is not valid, please retry");
          }
          out.writeObject(new ObjectIO(tmpS, Integer.parseInt(tmpS)));
          out.flush();
          out.reset();
          tmpS = null;
          if ((tmp = (ObjectIO) in.readObject()) != null) {}
          if (tmp.id == -1) {
              doInitialization(false);
          }
      }
    */
    public void startAllocation() throws Exception {
        receiveMessage();
        receiveMessage();
        this.maxUnitsToPlace = tmp.id;
    }
    /**
     * Checks if the HashMap<String, Integer> from GUI is valid, and finally send ObjectIO with the
     * HashMap to server if it is valid.
     *
     * @param placeOrders HashMap<String, Integer> that maps the territory name to number of units
     *     to place in the territory.
     * @return a String that describing the problem of the allocation order. Or returns null if
     *     there is no problem.
     */
    public String tryAllocation(HashMap<String, Integer> placeOrders) throws Exception {
        int totalUnits = 0;
        for (int unitNum : placeOrders.values()) {
            totalUnits = totalUnits + unitNum;
        }
        if (totalUnits <= maxUnitsToPlace) {
            ObjectIO orders = new ObjectIO();
            orders.placeOrders = placeOrders;
            sendMessage(orders);
            // read in a map for action phase.
            // TODO: abstract this out with askLeave()?
            // receiveMessage();
            return null;
        } else {
            return "Invalid placement: Total number of units exceeds maximum.";
        }
    }

    /**
     * Returns the maximum number of unit to place for tryAllocate() unit.
     *
     * @return an int that represents maximum number of unit to place for tryAllocate() unit.
     */
    public int getMaxUnitsToPlace() {
        return maxUnitsToPlace;
    }

    public String tryIssueAttackOrder(ActionInfo order) {
        String problem = ruleChecker.checkRuleForAttack(order, this.theMap);
        if (problem == null) {
            this.tmpOrders.add(order);
            executer.executePreAttack(this.theMap, order);
            return null;
        } else {
            return problem;
        }
    }

    public String tryIssueMoveOrder(ActionInfo order) {
        String problem = ruleChecker.checkRuleForMove(order, this.theMap);
        if (problem == null) {
            this.tmpOrders.add(order);
            executer.executeMove(this.theMap, order);
            return null;
        } else {
            return problem;
        }
    }

    public String tryIssueUpgradeUnitOrder(ActionInfo order) {
        String problem = ruleChecker.checkRuleForUpgradeUnit(order, this.theMap);
        if (problem == null) {
            this.tmpOrders.add(order);
            executer.executeUpgradeUnit(this.theMap, order);
            return null;
        } else {
            return problem;
        }
    }

    public String tryIssueUpgradeSpyUnitOrder(ActionInfo order) {
        String problem = ruleChecker.checkRuleForUpgradeSpy(order, this.theMap);
        if (problem == null) {
            this.tmpOrders.add(order);
            executer.executeUpgradeSpyUnit(this.theMap, order);
            return null;
        } else {
            return problem;
        }
    }

    public String tryIssueMoveSpyOrder(ActionInfo order) {
        String problem = ruleChecker.checkRuleForMoveSpy(order, this.theMap);
        if (problem == null) {
            if (isLimitedActionUsed.get("move spy")) {
                return "Invalid action: You can only move spy once in each turn.";
            }
            this.tmpOrders.add(order);
            executer.executeMoveSpy(this.theMap, order);
            isLimitedActionUsed.put("move spy", true);
            return null;
        } else {
            return problem;
        }
    }

    public String tryIssueResearchCloakingOrder(ActionInfo order) {
        String problem = ruleChecker.checkRuleForResearchCloaking(order, this.theMap);
        if (problem == null) {
            this.tmpOrders.add(order);
            executer.executeResearchCloaking(this.theMap, order);
            return null;
        } else {
            return problem;
        }
    }

    public String tryIssueCloakingOrder(ActionInfo order) {
        String problem = ruleChecker.checkRuleForCloaking(order, this.theMap);
        if (problem == null) {
            this.tmpOrders.add(order);
            executer.executeCloaking(this.theMap, order);
            return null;
        } else {
            return problem;
        }
    }

    public String tryIssueUpgradeTechOrder(ActionInfo order) {
        String problem = ruleChecker.checkRuleForUpgradeTech(order, this.theMap);
        if (problem == null) {
            if (isLimitedActionUsed.get("upgrade tech")) {
                return "Invalid action: You can only upgrade tech once in each turn.";
            }
            this.tmpOrders.add(order);
            // executer.executeUpgradeTech(this.theMap, order);
            HashMap<String, Integer> resCost = costCal.calculateUpgradeTechCost(order, theMap);
            executer.deductCost(theMap, order, resCost);
            isLimitedActionUsed.put("upgrade tech", true);
            return null;
        } else {
            return problem;
        }
    }

    public String tryIssueResearchPatentOrder(ActionInfo order) {
        String problem = ruleChecker.checkRuleForResearchPatent(order, this.theMap);
        if (problem == null) {
            if (isLimitedActionUsed.get("research patent")) {
                return "Invalid action: You can only research patent once in each turn.";
            } else if (!tmpOrders.isEmpty()) {
                return "Invalid action: You cannot only research patent after other actions in a turn.";
            }
            this.tmpOrders.add(order);
            executer.executeResearchPatent(theMap, order);
            isLimitedActionUsed.put("research patent", true);
            return null;
        } else {
            return problem;
        }
    }

    public void doneIssueOrders() throws Exception {
        ArrayList<ActionInfo> group1Orders = new ArrayList<>();
        ArrayList<ActionInfo> attackOrders = new ArrayList<>();
        ArrayList<ActionInfo> patentOrders = new ArrayList<>();
        ObjectIO toSend = new ObjectIO();
        for (ActionInfo order : this.tmpOrders) {
            if (order.getActionType().equals("attack")) {
                attackOrders.add(order);
            } else if (order.getActionType().equals("research patent")) {
                patentOrders.add(order);
            } else {
                group1Orders.add(order);
            }
        }
        this.tmpOrders = new ArrayList<>(); // refresh the local order ArrayList<ActionInfo>
        toSend.moveOrders = group1Orders;
        toSend.attackOrders = attackOrders;
        toSend.patentOrders = patentOrders;
        // resets all to false in isLimitedActionUsed for limited orders
        isLimitedActionUsed.replaceAll((n, v) -> false);
        // send the orders to server
        sendMessage(toSend);
        // read in the new map for next action phase.
        // return checkStatus();
    }

    public String checkStatus() throws Exception {
        receiveMessage();
        if (tmp.id == -1) {
            gameOverMessage = "You lose";
            return gameOverMessage;
        }
        if (tmp.id == -2) {
            gameOverMessage = tmp.message; // other player wins
            return gameOverMessage;
        }
        if (tmp.id == -3) {
            gameOverMessage = "You win!";
            return gameOverMessage;
        }
        return null;
    }
    /*
      public void doPlacement() throws Exception {
          System.out.println("-----waitServerInput-----");
          if ((tmp = (ObjectIO) in.readObject()) != null) {
              String playerName = tmp.message;
              ClientOrderHelper coh =
                      new ClientOrderHelper(playerName, stdIn, new PrintStream(System.out));
              out.writeObject(
                      coh.issuePlaceOrders(
                              tmp.id, tmp.playerNames)); // here tmp.playerNames is territory names...
          }
      }

      public void doAskLeave() {
          tmpS = null;
          System.out.println("you can leave by /leave, or continue by entering anything else");
          wait = true;
          while (!ready) {}
          wait = false;
          ready = false;
          while (tmpS == null) {
              if (wait) {
                  System.out.println("Return to the game...");
                  wait = false;
                  tmpS = null;
                  doAskLeave();
              }
          }
      }
    */
    /**
     * first wait the ObjectIO from server, then call the placeOrder method in the helper class,
     * finally send ObjectIO to server need to check the status of the player: win or lose
     */
    /*
        public void doAction() throws Exception {
            while (true) {
                // doAskLeave();

                System.out.println("waiting other players");
                // stdIn = new BufferedReader(new InputStreamReader(System.in));
                if ((tmp = (ObjectIO) in.readObject()) != null) {
                    if (tmp.id < 0) {
                        break;
                    }
                    doAskLeave();
                    // MapTextView mapview = new MapTextView(tmp.playerNames);
                    // System.out.println(mapview.displayMap(tmp.map));
                    // System.out.println(tmp.message);
                    String playerName = tmp.message;
                    ClientOrderHelper coh =
                            new ClientOrderHelper(playerName, stdIn, new PrintStream(System.out));
                    out.writeObject(coh.issueActionOrders(tmp.map, tmp.playerNames));
                }
                // doAskLeave();
                // stdIn = null;
                tmpS = null;
                System.out.println("you can leave by /leave, continue by entering anything else");
                wait = true;
                while (!ready) {
                }
                wait = false;
                ready = false;
                while (tmpS == null) {
                    if (wait) {
                      System.out.println("Return to the game...");
                      wait = false;
                      continue mark2;
                    }
                  }
            }
            if (tmp.id == -1) {
                System.out.println("Your lost all territories...");
            }
            if (tmp.id == -2) {
                System.out.println(tmp.message);
            }
            if (tmp.id == -3) {
                System.out.println("You win!");
            }
        }
    */

    /**
     * each turn ask the user watch or not, enter something start with /q will quit, no longer print
     * the game enter others will update the game's map
     */
    public void doWatch() throws Exception {
        receiveMessage();
    }
    /*
        public void doWatch() throws Exception {
            while (true) {
                if ((tmp = (ObjectIO) in.readObject()) != null) {
                    MapTextView mapview = new MapTextView(tmp.playerNames);
                    System.out.println(mapview.displayMap(tmp.map));
                    System.out.println(tmp.message);
                }
                String tmpstr;
                System.out.println("Do you want watch? you can quit by /q");
                if ((tmpstr = stdIn.readLine()) != null) {
                    if (tmpstr.toLowerCase().startsWith("/q")) {
                        System.out.println("quited");
                        break;
                    }
                }
            }
        }
    */
    @Override
    public void run() {
        try {
            /*System.out.println("wait other players...");
            doInitialization(true);
            doPlacement();
            System.out.println("Initialization is done");
            doAction();
            doWatch();*/
            // System.exit(0);
            while (true) {}
        } catch (Exception e) {
        }
    }

    public void initializeTerritoriesInfo() {
        for (String territoryName : theMap.getMyTerritories()) {
            TerritoryInfo info =
                    new TerritoryInfo(
                            name,
                            territoryName,
                            theMap.getTerritory(territoryName).getDomain(),
                            theMap.getTerritory(territoryName).getResProduction());
            territoriesInfo.put(territoryName, info);
        }
    }

    public void updateTerritoryInfo() {
        HashMap<String, Boolean> vizStatus = theMap.getPlayerInfo("name").getAllVizStatus();
        for (String territoryName : theMap.getMyTerritories()) {
            if (vizStatus.get(territoryName)) {
                TerritoryInfo info = territoriesInfo.get(territoryName);
                Territory territory = theMap.getTerritory(territoryName);
                info.setCloakingTurns(territory.getCloakingTurns());
                info.setOwnerName(territory.getOwnerName());
                info.setPlayerSpyNum(territory.getSpyTroopNumUnits(name));
                info.setTroopNum(territory.getAllNumUnits());
            }
        }
    }

    public HashMap<String, TerritoryInfo> getTerritoriesInfo() {
        return territoriesInfo;
    }

    public TerritoryInfo getTerritoryInfo(String territoryName) {
        return territoriesInfo.get(territoryName);
    }
}
