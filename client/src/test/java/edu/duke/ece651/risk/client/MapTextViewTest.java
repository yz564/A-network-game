package edu.duke.ece651.risk.client;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import edu.duke.ece651.risk.shared.*;

public class MapTextViewTest {
    @Test
    public void test_constructor() {
        ArrayList<String> playerNames = new ArrayList();
        playerNames.add("Green player");
        playerNames.add("Blue player");
        playerNames.add("Red player");

        WorldMapFactory mf = new V1MapFactory();
        WorldMap map1 = mf.makeWorldMap(3);
        map1.tryAssignInitOwner(1, playerNames[1]);
        map1.tryAssignInitOwner(2, playerNames[2]);
        map1.tryAssignInitOwner(3, playerNames[3]);

        MapTextView view = new MapTextView(map1, playerNames, "Green player");
    }
}
