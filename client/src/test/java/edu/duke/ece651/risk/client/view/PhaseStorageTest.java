package edu.duke.ece651.risk.client.view;

import edu.duke.ece651.risk.client.view.Phase;
import edu.duke.ece651.risk.client.view.PhaseStorage;
import edu.duke.ece651.risk.client.view.View;
import org.junit.jupiter.api.Test;

public class PhaseStorageTest {
    @Test
    public void test_phase_storage() {
        PhaseStorage.getPhase("serverConnect");
        View view = new View("a", "b", "c");
        Phase window = new Phase(view, "d");
        PhaseStorage.addPhase(window);
    }
}
