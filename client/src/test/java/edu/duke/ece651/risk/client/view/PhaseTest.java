package edu.duke.ece651.risk.client.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class PhaseTest {
    @Test
    public void test_phase() {
        View v = new View("serverConnect", "/ui/views/server-connect.fxml");
        Phase phase = new Phase(v, "a");
        assertEquals("serverConnect", phase.getName());
        assertEquals("a", phase.getWindowTitle());
        assertEquals(v, phase.getView());
    }
}
