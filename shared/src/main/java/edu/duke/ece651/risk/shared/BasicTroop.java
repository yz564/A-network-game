package edu.duke.ece651.risk.shared;

/*
 * A troop contains an arbitrary number of a particular kind of units. E.g. a
 * troop could contain 10 soldiers or 5 knights but not both.
 */
public class BasicTroop extends AbstractTroop {

    /*
     * Constructs a BasicTroop object, with name filed assigned "Basic".
     *
     * @param numUnits is the number of units put in the troop.
     *
     * @param unitLimit is the maximum amount of units that a troop can contain.
     */
    public BasicTroop(int numUnits, int unitLimit) {
        super("Basic", numUnits, unitLimit);
    }

    /*
     * Construct a BasicTroop object. The default number of units is 999 if numLimit
     * is not explicitly set.
     *
     * @param numUnits is the number of units put in the troop.
     */
    public BasicTroop(int numUnits) {
        this(numUnits, 99999);
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o.getClass().equals(getClass())) {
            BasicTroop other = (BasicTroop) o;
            return this.troopName == other.getName() && this.numUnits == other.getNumUnits();
        }
        return false;
    }

    @Override
    public String toString() {
        return this.troopName + " with " + this.numUnits + " units.\n";
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public int getBonus() {
        return 0;
    }

    @Override
    public int getTechLevelReq(){
        return 0;
    }

    @Override
    public int getCost(){
        return 0;
    }
}
