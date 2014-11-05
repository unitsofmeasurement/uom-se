package tec.uom.se.function;

import javax.measure.Quantity;

/**
 * default implementation to {@link QuantityOperations}
 * @author otaviojava
 *
 * @param <Q> to do the operation with Quantity<Q>
 * @param <E> to verify and cast using Class<E>
 */
final class QuantityOperationsImpl<Q extends Quantity<Q>, E extends Quantity<E>> implements QuantityOperations<Q, E> {

    private Class<E> quantityClass;

    private Quantity<Q> quantity;


    public QuantityOperationsImpl(Class<E> quantityClass,
            Quantity<Q> quantity) {
        this.quantityClass = quantityClass;
        this.quantity = quantity;
    }

    @Override
    public Quantity<Q> getQuantity() {
        return quantity;
    }

    @Override
    public Class<E> getQuantityClass() {
        return quantityClass;
    }

}
