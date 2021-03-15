package edu.duke.ece651.risk.client;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import edu.duke.ece651.risk.shared.*;

public class MapTextViewTest {
    @Test
    public void test_constructor() {
        WorldMapFactory mf = new V1MapFactory();
        WorldMap toDisplay1 = mf.makeWorldMap(3);
        MapTextView view = new MapTextView(toDisplay1);
    }
}
