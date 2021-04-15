package edu.duke.ece651.risk.client.view;

import edu.duke.ece651.risk.client.controller.SelectTerritoryGroupController;
import edu.duke.ece651.risk.client.controller.ServerConnectController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(ApplicationExtension.class)
public class ViewTest {
    @Test
    public void test_view() throws IOException {
        View v1 = new View("a", "b", "c");
        assertEquals("a", v1.getName());
        assertEquals("b", v1.getXMLPath());
        assertEquals("c", v1.getCSSPath());
        assertEquals("aview.\n" + "XML Path: `b`\n", v1.toString());
        assertEquals(-1122958654, v1.hashCode());
        View v2 = new View("serverConnect", "/ui/views/server-connect.fxml");
        v2.makeScene(mock(ServerConnectController.class));
        View v3 =
                new View(
                        "selectTerritoryGroupEven",
                        "/ui/views/select-territory.fxml",
                        "/ui/styling/territory-group.css");
        v3.makeScene(mock(SelectTerritoryGroupController.class));
    }
}
