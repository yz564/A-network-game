package edu.duke.ece651.risk.client;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.duke.ece651.risk.shared.*;

public class ClientOrderHelperTest {
    @Test
    public void test_read_attack_order() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        BufferedReader input = new BufferedReader(new StringReader("AAA\nBBB\n21\n"));
        PrintStream output = new PrintStream(bytes, true);

        ClientOrderHelper helper = new ClientOrderHelper("Green Player", input, output, 10);
        helper.readAttackOrder();
    }

    @Test
    public void test_read_move_order() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        BufferedReader input = new BufferedReader(new StringReader("AAA\nBBB\n21\n"));
        PrintStream output = new PrintStream(bytes, true);

        ClientOrderHelper helper = new ClientOrderHelper("Green Player", input, output);
        helper.readMoveOrder();
    }

    @Test
    public void test_issue_place_orders() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        BufferedReader input = new BufferedReader(new StringReader("-1\n11\n1\n9\n"));
        PrintStream output = new PrintStream(bytes, true);

        ClientOrderHelper helper = new ClientOrderHelper("Green Player", input, output);
        ArrayList<String> t = new ArrayList<String>();
        t.add("A");
        t.add("B");
        helper.issuePlaceOrders(10, t);
    }

    @Disabled
    @Test
    public void test_issue_action_orders1() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        String actions =
                "M\nNarnia\nMidkemia\n0\n"
                        + "M\nScadrial\nMidkemia\n0\n"
                        + "A\n"
                        + "A\nNarnia\nElantris\n0\n"
                        + "M\n"
                        + "A\nScadrial\nElantris\n0\n"
                        + "D\n";
        BufferedReader input = new BufferedReader(new StringReader(actions));
        PrintStream output = new PrintStream(bytes, true);

        ClientOrderHelper helper = new ClientOrderHelper("Green player", input, output);

        ArrayList<String> playerNames = new ArrayList<String>();
        playerNames.add("Green player");
        playerNames.add("Blue player");
        playerNames.add("Red player");
        WorldMapFactory mf = new V1MapFactory();
        WorldMap map = mf.makeTestWorldMap();
        map.tryAssignInitOwner(1, playerNames.get(0));
        map.tryAssignInitOwner(2, playerNames.get(1));
        map.tryAssignInitOwner(3, playerNames.get(2));

        helper.issueActionOrders(map, playerNames);

        // String actual = bytes.toString();
        // assertEquals("", actual);
    }

    @Test
    public void test_issue_action_orders2() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        String actions = "D\n";
        BufferedReader input = new BufferedReader(new StringReader(actions));
        PrintStream output = new PrintStream(bytes, true);

        ClientOrderHelper helper = new ClientOrderHelper("Green player", input, output);

        ArrayList<String> playerNames = new ArrayList<String>();
        playerNames.add("Green player");
        playerNames.add("Blue player");
        playerNames.add("Red player");
        WorldMapFactory mf = new V1MapFactory();
        WorldMap map = mf.makeTestWorldMap();
        map.tryAssignInitOwner(1, playerNames.get(0));
        map.tryAssignInitOwner(2, playerNames.get(1));
        map.tryAssignInitOwner(3, playerNames.get(2));

        helper.issueActionOrders(map, playerNames);

        // String actual = bytes.toString();
        // assertEquals("", actual);
    }
}
