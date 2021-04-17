package edu.duke.ece651.risk.shared;

/**
 * Represents the player's chosen character in the Duke RISK game.
 */
public class PlayerCharacter {
    private final String name;
    private final String researchDomain;

    /**
     * Initializes the character.
     * @param name is the name of the character such as "Andrew Hilton", "Melinda Gates", etc.
     * @param researchDomain is the domain that the character represents such as "Engineering", "Business", etc.
     */
    public PlayerCharacter (String name, String researchDomain) {
        this.name = name;
        this.researchDomain = researchDomain;
    }

    /**
     * @return the name of the character.
     */
    public String getName() { return name; }

    /**
     * @return the domain of the character.
     */
    public String getResearchDomain() { return researchDomain; }

    /**
     * @return the name and domain of the character in a single string.
     */
    @Override
    public String toString() {
        return "{Name: " + name + "}" +
                "{Domain: " + researchDomain + "}";
    }

    /**
     * @return the hash code of the character by returning toString()'s hash code.
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
