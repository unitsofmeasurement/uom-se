package tec.uom.se.unit;

import javax.measure.Quantity;
import javax.measure.Unit;

/**
 * Inner product element represents a rational power of a single unit.
 */
final class ElementProduct <T extends Quantity<T>> {

    /**
     * Holds the single unit.
     */
    final Unit<T> unit;

    /**
     * Holds the power exponent.
     */
    final int pow;

    /**
     * Holds the root exponent.
     */
    final int root;

    /**
     * Structural constructor.
     *
     * @param unit the unit.
     * @param pow the power exponent.
     * @param root the root exponent.
     */
    ElementProduct(Unit<T> unit, int pow, int root) {
        this.unit = unit;
        this.pow = pow;
        this.root = root;
    }

    /**
     * Returns this element's unit.
     *
     * @return the single unit.
     */
    public Unit<?> getUnit() {
        return unit;
    }

    /**
     * Returns the power exponent. The power exponent can be negative but is
     * always different from zero.
     *
     * @return the power exponent of the single unit.
     */
    public int getPow() {
        return pow;
    }

    /**
     * Returns the root exponent. The root exponent is always greater than
     * zero.
     *
     * @return the root exponent of the single unit.
     */
    public int getRoot() {
        return root;
    }
}