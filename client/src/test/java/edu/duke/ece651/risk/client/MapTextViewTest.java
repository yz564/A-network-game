package edu.duke.ece651.risk.client;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import edu.duke.ece651.risk.shared.*;

public class MapTextViewTest {
    @Test
    public void test_constructor() {
        ArrayList<String> playerNames = new ArrayList<String>();
        playerNames.add("Green player");
        playerNames.add("Blue player");
        playerNames.add("Red player");
        MapTextView view = new MapTextView(playerNames, "Green player");

        WorldMapFactory mf = new V1MapFactory();
        WorldMap map1 = mf.makeWorldMap(3);
        map1.tryAssignInitOwner(1, playerNames.get(0));
        map1.tryAssignInitOwner(2, playerNames.get(1));
        map1.tryAssignInitOwner(3, playerNames.get(2));

    }
}
