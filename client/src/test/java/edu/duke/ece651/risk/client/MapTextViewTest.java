package edu.duke.ece651.risk.client;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import edu.duke.ece651.risk.shared.*;

public class MapTextViewTest {
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
        expected = expected + "Green player:\n" + "-------------\n"
                + "    0 units in Western Dothraki Sea (next to: Lower Rnoyne, Eastern Dothraki Sea, Forest of Qohor, Lhaxar, Mantarys, Northern Dothraki Sea)\n"
                + "    0 units in Braavosian Coastlands (next to: Myr, Hills of Horvos)\n"
                + "    0 units in Forest of Qohor (next to: Lower Rnoyne, Western Dothraki Sea, Hills of Horvos, Northern Dothraki Sea)\n"
                + "    0 units in Hills of Horvos (next to: Myr, Lower Rnoyne, Braavosian Coastlands, Forest of Qohor)\n"
                + "    0 units in Northern Dothraki Sea (next to: Western Dothraki Sea, Eastern Dothraki Sea, Forest of Qohor)\n";
        expected = expected + "Blue player:\n" + "-------------\n"
                + "    0 units in Myr (next to: Lower Rnoyne, Braavosian Coastlands, Hills of Horvos)\n"
                + "    0 units in Lower Rnoyne (next to: Myr, Western Dothraki Sea, Forest of Qohor, Hills of Horvos, Mantarys)\n"
                + "    0 units in Lhaxar (next to: The Red Waste, Western Dothraki Sea, Eastern Dothraki Sea, Old Ohis, Mantarys)\n"
                + "    0 units in Old Ohis (next to: The Red Waste, Lhaxar)\n"
                + "    0 units in Mantarys (next to: Lower Rnoyne, Western Dothraki Sea, Lhaxar)\n";
        expected = expected + "Red player:\n" + "-------------\n"
                + "    0 units in Bayasabhad (next to: The Red Waste, Northern Jade Sea)\n"
                + "    0 units in The Red Waste (next to: Bayasabhad, Northern Jade Sea, Eastern Dothraki Sea, Lhaxar, Old Ohis)\n"
                + "    0 units in Northern Jade Sea (next to: Bayasabhad, The Red Waste, Eastern Dothraki Sea, Vaes Dothrak)\n"
                + "    0 units in Eastern Dothraki Sea (next to: The Red Waste, Northern Jade Sea, Western Dothraki Sea, Lhaxar, Vaes Dothrak, Northern Dothraki Sea)\n"
                + "    0 units in Vaes Dothrak (next to: Northern Jade Sea, Eastern Dothraki Sea)\n";
        String actual = view.displayMap(map);
        assertEquals(expected, actual);
    }
}