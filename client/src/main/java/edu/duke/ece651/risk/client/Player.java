package edu.duke.ece651.risk.client;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;

import edu.duke.ece651.risk.client.ClientOrderHelper;
import edu.duke.ece651.risk.client.MapTextView;
import edu.duke.ece651.risk.shared.ObjectIO;

public class Player implements Runnable {
    int id;
    ObjectInputStream in;
    ObjectOutputStream out;
    ObjectIO tmp;
    volatile String tmpS;
    BufferedReader stdIn;
    public volatile Boolean wait;
    public volatile Boolean ready;
    private int maxUnitsToPlace;

    public Player(int id, ObjectInputStream in, ObjectOutputStream out, BufferedReader stdIn) {
        this.id = id;
        this.in = in;
        this.out = out;
        this.stdIn = stdIn;
        this.tmpS = null;
        this.wait = false;
        this.ready = false;
        this.maxUnitsToPlace = 30;
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

    public String tryInitialization(String info) throws Exception {
        tmp = (ObjectIO) in.readObject();
        if (tmp.groups.contains(Integer.parseInt(info)) && Integer.parseInt(info) < id + 3 && Integer.parseInt(info) > 0) {
            out.writeObject(new ObjectIO(info, Integer.parseInt(info)));
            out.flush();
            out.reset();
        } else {
            return "Input is invalid, please retry";
        }
        tmp = (ObjectIO) in.readObject();
        if (tmp.id == -1) {
            return "This territory group has been selected by other player(s), please retry.";
        }
        return null;
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

            // if ((tmpS = stdIn.readLine()) != null) {
            // }
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
        this.maxUnitsToPlace = ((ObjectIO) in.readObject()).id;
        int totalUnits = 0;
        for (int unitNum : placeOrders.values()) {
            totalUnits = totalUnits + unitNum;
        }
        if (totalUnits > maxUnitsToPlace) {
            ObjectIO orders = new ObjectIO();
            orders.placeOrders = placeOrders;
            out.writeObject(orders); // here tmp.playerNames is territory names...
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
            System.out.println("wait other players...");
            doInitialization(true);
            doPlacement();
            System.out.println("Initialization is done");
            doAction();
            doWatch();
            // System.exit(0);
            while (true) {}
        } catch (Exception e) {
        }
    }
}
