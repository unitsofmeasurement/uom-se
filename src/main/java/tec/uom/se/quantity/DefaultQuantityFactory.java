package tec.uom.se.quantity;

import java.util.Objects;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.function.QuantityFactory;

/**
 * A factory producing simple quantities instances (tuples {@link Number}/{@link Unit}).
 *
 * For example:<br/><code>
 *      Mass m = QuantityFactory.getInstance(Mass.class).create(23.0, KILOGRAM); // 23.0 kg<br/>
 *      Time m = QuantityFactory.getInstance(Time.class).create(124, MILLI(SECOND)); // 124 ms
 * </code>
 * @param <Q> The type of the quantity.
 *
 * @author  <a href="mailto:desruisseaux@users.sourceforge.net">Martin Desruisseaux</a>
 * @author  <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:otaviojava@java.net">Otavio Santana</a>
 * @version 0.5.5, $Date: 2014-07-22 $
 */
class DefaultQuantityFactory <Q extends Quantity<Q>> implements QuantityFactory<Q>{

    private Class<Q> unit;

    DefaultQuantityFactory(Class<Q> unit) {
        this.unit = unit;

    }

    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("tec.uom.se.DefaultQuantityFactory <");
        string.append(unit.getName()).append('>');
        return string.toString();
    }

    public boolean equals(Object obj) {
        if(DefaultQuantityFactory.class.isInstance(obj)) {
            @SuppressWarnings("rawtypes")
            DefaultQuantityFactory other = DefaultQuantityFactory.class.cast(obj);
            return Objects.equals(unit, other.unit);
        }
        return false;
    }

    public int hashCode() {
        return unit.hashCode();
    }

    @SuppressWarnings("unchecked")
    public <T extends Number, U extends Unit<Q>> Q create(T number, U unit) {
        return (Q) tec.uom.se.QuantityFactory.of(number, unit);
    }
}
