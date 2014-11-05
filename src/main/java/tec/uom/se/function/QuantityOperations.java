package tec.uom.se.function;

import javax.measure.Quantity;
import javax.measure.Unit;


/**
 * This interface represents operations to Quantity that needs verification
 * ( {@link Quantity#divide(Quantity)} and {@link Quantity#multiply(Quantity)}).
 * it has the {@link Quantity} to do the operation and the {@link Class} to be cast after operation.
 * @author otaviojava
 *
 * @param <Q> to do the operation with Quantity<Q>
 * @param <E> to verify and cast using Class<E>
 * @see QuantityOperations#of(Quantity, Class)
 * @see QuantityOperations#getQuantity()
 * @see QuantityOperations#getQuantityClass()
 */
public interface QuantityOperations<Q extends Quantity<Q>, E extends Quantity<E>> {

    /**
     * The {@link Quantity} to do the operations
     * @return the quantity to operation
     */
    Quantity<Q> getQuantity();

    /**
     * Class to do the verification
     * @return the Class to verification
     * @see Unit#asType(Class)
     */
    Class<E> getQuantityClass();


    /**
     * creates the QuantityOperations
     *
     * @param quantity
     * @param quantityClass
     *
     * @see QuantityOperations
     * @see QuantityOperations#of(Quantity, Class)
     * @see QuantityOperations#getQuantity()
     * @see QuantityOperations#getQuantityClass()
     * @returnn a QuantityOperations instance
     */
    static <Q extends Quantity<Q>, E extends Quantity<E>> QuantityOperations<Q, E> of(Quantity<Q> quantity, Class<E> quantityClass){
        return new QuantityOperationsImpl<>(quantityClass, quantity);
    }


}
