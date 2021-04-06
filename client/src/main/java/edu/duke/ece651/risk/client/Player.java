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
    public volatile Boolean wait;
    public volatile Boolean ready;
    private String territoryGroupSelected;
    private int maxUnitsToPlace;
    private WorldMap theMap;
    private final ActionRuleCheckerHelper ruleChecker = new ActionRuleCheckerHelper();
    private final ActionExecuter executer = new ActionExecuter();
    private ArrayList<ActionInfo> tmpOrders;

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
    }

    public void setName(String n) {
      this.name=n;
    }

    public String getName() {
      return name;
    }

    public String getTerritoryGroupSelected() { return territoryGroupSelected; }

    public WorldMap getMap() {
        return theMap;
    }

    private void updateMap() {
        //theMap = (WorldMap) SerializationUtils.clone(tmp.map);
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
              receiveMessage();
                this.territoryGroupSelected = info;
              //System.out.println(theMap.getPlayerTerritories(name));
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

  public void startAllocation() throws Exception {
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
            receiveMessage();
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

    public String tryIssueUpgradeTechOrder(ActionInfo order) {
        String problem = ruleChecker.checkRuleForUpgradeTech(order, this.theMap);
        if (problem == null) {
            this.tmpOrders.add(order);
            executer.executeUpgradeTech(this.theMap, order);
            return null;
        } else {
            return problem;
        }
    }

    public String doneIssueOrders() throws Exception {
        ArrayList<ActionInfo> group1Orders = new ArrayList<>();
        ArrayList<ActionInfo> attackOrders = new ArrayList<>();
        ObjectIO toSend = new ObjectIO();
        for (ActionInfo order : this.tmpOrders) {
            if (order.getActionType().equals("attack")) {
                attackOrders.add(order);
            } else {
                group1Orders.add(order);
            }
        }
        this.tmpOrders = new ArrayList<>(); // refresh the local order ArrayList<ActionInfo>
        toSend.moveOrders = group1Orders;
        toSend.attackOrders = attackOrders;
        sendMessage(toSend);
        // read in the new map for next action phase.
        receiveMessage();
        if (tmp.id == -1) {
            return "Your lost all territories...";
        }
        if (tmp.id == -2) {
            return tmp.message; // other player wins
        }
        if (tmp.id == -3) {
            return "You win!";
        }
        return null;
    }

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

    /**
     * first wait the ObjectIO from server, then call the placeOrder method in the helper class,
     * finally send ObjectIO to server need to check the status of the player: win or lose
     */
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
            /* tmpS = null;
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
              }*/
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

    /**
     * each turn ask the user watch or not, enter something start with /q will quit, no longer print
     * the game enter others will update the game's map
     */
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
}
