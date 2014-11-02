package tec.uom.se.quantity;

import javax.measure.Quantity;

/**
 *
 * Quantity specialized to SE plataform.
 * @see {@link Quantity}
 * @author otaviojava
 * @param <Q>
 */
public interface  BaseQuantity<Q extends Quantity<Q>> extends Quantity<Q> {

    /**
     * Compares two instances of {@link Quantity<Q>}.
     * Conversion of unit can happen if necessary
     * @param that the {@code quantity<Q>} to be compared with this instance.
     * @return {@code true} if {@code that > this}.
     * @throws NullPointerException if the that is null
     */
    boolean isGreaterThan(Quantity<Q> that);
    /**
     * Compares two instances of {@link Quantity<Q>},
     * doing the conversion of unit if necessary.
     * @param that the {@code quantity<Q>} to be compared with this instance.
     * @return {@code true} if {@code that >= this}.
     * @throws NullPointerException if the that is null
     */
    boolean isGreaterThanOrEqualTo(Quantity<Q> that);
    /**
     * Compares two instances of {@link Quantity<Q>},
     * doing the conversion of unit if necessary.
     * @param that the {@code quantity<Q>} to be compared with this instance.
     * @return {@code true} if {@code that < this}.
     * @throws NullPointerException if the quantity is null
     */
    boolean isLessThan(Quantity<Q> that);
    /**
     * Compares two instances of {@link Quantity<Q>},
     * doing the conversion of unit if necessary.
     * @param that the {@code quantity<Q>} to be compared with this instance.
     * @return {@code true} if {@code that < this}.
     * @throws NullPointerException if the quantity is null
     */
    boolean isLessThanOrEqualTo(Quantity<Q> that);
    /**
     * Compares two instances of {@link Quantity<Q>},
     * doing the conversion of unit if necessary.
     * @param that the {@code quantity<Q>} to be compared with this instance.
     * @return {@code true} if {@code that < this}.
     * @throws NullPointerException if the quantity is null
     */
    boolean isEquivalentTo(Quantity<Q> that);

}
