package edu.duke.ece651.risk.shared;

public class LevelTroop extends AbstractTroop {

    /*
     * Constructs a LevelTroop object.
     *
     * @param name is the name assigned to the troop.
     *
     * @param numUnits is the number of units put in the troop.
     *
     * @param unitLimit is the maximum amount of units that a troop can contain.
     *
     * @param bonus is the bonus for units in the Troop.
     */
    public LevelTroop(String name, int numUnits, int unitLimit, int bonus) {
        super(name, numUnits, unitLimit, bonus);
    }

    /*
     * Constructs a LevelTroop object with unitLimit assigned with 99999.
     *
     * @param name is the name assigned to the troop.
     *
     * @param numUnits is the number of units put in the troop.
     *
     * @param bonus is the bonus for units in the Troop.
     */
    public LevelTroop(String name, int numUnits, int bonus) {
        super(name, numUnits, 99999, bonus);
    }
}
