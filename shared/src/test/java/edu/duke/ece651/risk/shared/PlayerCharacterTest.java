package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerCharacterTest {
    @Test
    public void test_character() {
        String name = "Andrew Hilton";
        String domain = "Engineering";
        PlayerCharacter drew = new PlayerCharacter(name, domain);
        assertEquals(name, drew.getName());
        assertEquals(domain, drew.getResearchDomain());
        assertEquals("{Name: " + name + "}" + "{Domain: " + domain + "}", drew.toString());
        assertEquals(-2065748607, drew.hashCode());
    }
}