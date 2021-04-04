package edu.duke.ece651.risk.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.duke.ece651.risk.shared.V1MapFactory;
import edu.duke.ece651.risk.shared.WorldMap;
import edu.duke.ece651.risk.shared.WorldMapFactory;

public class MapTextViewTest {
    @Test
    public void test_display_testmap() {
        ArrayList<String> playerNames = new ArrayList<String>();
        playerNames.add("Green player");
        playerNames.add("Blue player");
        playerNames.add("Red player");
        MapTextView view = new MapTextView(playerNames);

        WorldMapFactory mf = new V1MapFactory();
        WorldMap map = mf.makeTestWorldMap();

        map.tryAssignInitOwner(1, playerNames.get(0));
        map.tryAssignInitOwner(2, playerNames.get(1));
        map.tryAssignInitOwner(3, playerNames.get(2));

        String expected = "";
        expected =
                expected
                        + "Green player:\n"
                        + "-------------\n"
                        + "    0 units in Narnia (next to: Elantris, Midkemia)\n"
                        + "    0 units in Midkemia (next to: Elantris, Narnia, Oz, Roshar)\n"
                        + "    0 units in Oz (next to: Mordor, Scadrial, Midkemia, Gondor)\n";
        expected =
                expected
                        + "Blue player:\n"
                        + "-------------\n"
                        + "    0 units in Elantris (next to: Narnia, Scadrial, Midkemia, Roshar)\n"
                        + "    0 units in Scadrial (next to: Elantris, Mordor, Hogwarts, Midkemia, Oz, Roshar)\n"
                        + "    0 units in Roshar (next to: Elantris, Hogwarts, Scadrial)\n";
        expected =
                expected
                        + "Red player:\n"
                        + "-------------\n"
                        + "    0 units in Mordor (next to: Hogwarts, Scadrial, Gondor, Oz)\n"
                        + "    0 units in Hogwarts (next to: Mordor, Scadrial, Roshar)\n"
                        + "    0 units in Gondor (next to: Mordor, Oz)\n";
        String actual = view.displayMap(map);
        assertEquals(expected, actual);
    }

    @Test
    public void test_display_map1() {
        ArrayList<String> playerNames = new ArrayList<String>();
        playerNames.add("Green player");
        playerNames.add("Blue player");
        playerNames.add("Red player");
        MapTextView view = new MapTextView(playerNames);

        WorldMapFactory mf = new V1MapFactory();
        WorldMap map = mf.makeWorldMap(3);
        map.tryAssignInitOwner(1, playerNames.get(0));
        map.tryAssignInitOwner(2, playerNames.get(1));
        map.tryAssignInitOwner(3, playerNames.get(2));

        String expected = "";
        expected =
                expected
                        + "Green player:\n"
                        + "-------------\n"
                        + "    0 units in Hills of Horvos (next to: Braavosian Coastlands, Myr, Forest of Qohor, Lower Rnoyne)\n"
                        + "    0 units in Braavosian Coastlands (next to: Hills of Horvos, Myr)\n"
                        + "    0 units in Forest of Qohor (next to: Hills of Horvos, Lower Rnoyne, Western Dothraki Sea, Northern Dothraki Sea)\n"
                        + "    0 units in Western Dothraki Sea (next to: Lhaxar, Mantarys, Forest of Qohor, Lower Rnoyne, Eastern Dothraki Sea, Northern Dothraki Sea)\n"
                        + "    0 units in Northern Dothraki Sea (next to: Forest of Qohor, Eastern Dothraki Sea, Western Dothraki Sea)\n";
        expected =
                expected
                        + "Blue player:\n"
                        + "-------------\n"
                        + "    0 units in Lhaxar (next to: Old Ohis, Mantarys, The Red Waste, Eastern Dothraki Sea, Western Dothraki Sea)\n"
                        + "    0 units in Old Ohis (next to: Lhaxar, The Red Waste)\n"
                        + "    0 units in Myr (next to: Hills of Horvos, Braavosian Coastlands, Lower Rnoyne)\n"
                        + "    0 units in Mantarys (next to: Lhaxar, Lower Rnoyne, Western Dothraki Sea)\n"
                        + "    0 units in Lower Rnoyne (next to: Hills of Horvos, Myr, Mantarys, Forest of Qohor, Western Dothraki Sea)\n";
        expected =
                expected
                        + "Red player:\n"
                        + "-------------\n"
                        + "    0 units in Vaes Dothrak (next to: Northern Jade Sea, Eastern Dothraki Sea)\n"
                        + "    0 units in Bayasabhad (next to: The Red Waste, Northern Jade Sea)\n"
                        + "    0 units in The Red Waste (next to: Lhaxar, Bayasabhad, Old Ohis, Northern Jade Sea, Eastern Dothraki Sea)\n"
                        + "    0 units in Northern Jade Sea (next to: Vaes Dothrak, Bayasabhad, The Red Waste, Eastern Dothraki Sea)\n"
                        + "    0 units in Eastern Dothraki Sea (next to: Vaes Dothrak, Lhaxar, The Red Waste, Northern Jade Sea, Western Dothraki Sea, Northern Dothraki Sea)\n";
        String actual = view.displayMap(map);
        assertEquals(expected, actual);
    }
}
