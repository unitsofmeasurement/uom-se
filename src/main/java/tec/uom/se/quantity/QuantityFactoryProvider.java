package tec.uom.se.quantity;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.measure.Quantity;
import javax.measure.function.QuantityFactory;

/**
 * Provider of QuantityFactory
 * @author otaviojava
 * @author werner
 */
@SuppressWarnings("rawtypes")
public final class QuantityFactoryProvider {

    private QuantityFactoryProvider() {}


    private static final Map<Class, QuantityFactory> INSTANCE = new HashMap<>();

    /**
     * return a factory QuantityFactory from this unit
     * @param unit the unit
     * @return the QuantityFactory
     * @throws NullPointerException
     */
    @SuppressWarnings("unchecked")
    public static final <Q extends Quantity<Q>>  QuantityFactory<Q>  of(Class<Q> unit){
        Objects.requireNonNull(unit);
        if(!INSTANCE.containsKey(unit)) {
            synchronized (INSTANCE) {
                INSTANCE.put(unit, new DefaultQuantityFactory<>(unit));
            }
        }
        return INSTANCE.get(unit);
    }
}
