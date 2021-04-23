package edu.duke.ece651.risk.client;

import edu.duke.ece651.risk.shared.ObjectIO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player createPlayer() throws IllegalArgumentException {
        try {
            Socket server = new Socket("localhost", 3333);
            ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(server.getInputStream());
            ObjectIO tmp = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Player p = new Player(0, in, out, reader);
            return p;
        } catch (Exception e) {
            throw new IllegalArgumentException("Server address does not exist!");
        }
    }
    @Test
    public void test_constructor() throws IllegalArgumentException {
        Player p = createPlayer();
    }

    @Test
    public void test_name() {
        Player p = createPlayer();
        String name = "player";
        p.setName(name);
        assertEquals(name, p.getName());
    }

    @Test
    public void test_wait() {
        Player p = createPlayer();
        assertEquals(false, p.isWait());
        p.setWait(true);
        assertEquals(true, p.isWait());
    }

    @Test
    public void test_send_message() throws Exception {
        Player p = createPlayer();
        p.sendMessage(new ObjectIO());
    }

    @Test
    public void test_receive_message() throws Exception {
        Player p = createPlayer();
        p.receiveMessage();
    }

    @Disabled
    @Test
    public void test_initialization() throws Exception {
        Player p = createPlayer();
        p.receiveMessage();
        p.startInitialization();
        boolean success = p.tryInitialization("1");
        assertEquals(false, success);

        Player p2 = createPlayer();
        p2.receiveMessage();
        p2.startInitialization();
        boolean success2= p2.tryInitialization("1");
        assertEquals(false, success2);
    }

    @Test
    public void test_allocation() throws Exception {
        Player p = createPlayer();
        HashMap<String, Integer> numUnits = new HashMap<>();
        numUnits.put("A", 10);
        numUnits.put("b", 10);
        numUnits.put("c", 10);
        String success = p.tryAllocation(numUnits);
        assertEquals(null, success);

        numUnits.put("D", 1);
        success = p.tryAllocation(numUnits);
        assertEquals(true, success.equals("Invalid placement: Total number of units exceeds maximum."));
    }
}