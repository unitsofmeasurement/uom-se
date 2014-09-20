package tec.uom.se.function;

import java.util.Comparator;

import javax.measure.Quantity;

/**
 * Comparator to sort by natural order, looking both the unit and the value.
 * @return
 * <b>Given:</b>
 * <p>Quantity<Time> day = timeFactory.create(1, SI.DAY);</p>
 * <p>Quantity<Time> hours = timeFactory.create(18, SI.HOUR);</p>
 * <p>Quantity<Time> minutes = timeFactory.create(15, SI.HOUR);</p>
 * <p>Quantity<Time> seconds = timeFactory.create(100, SI.HOUR);</p>
 * will return: seconds, minutes, hours, day
 */
public class NaturalOrder<T extends Quantity<T>> implements Comparator<T>{

    @Override
    public int compare(T q1, T q2) {
        if (q1.getUnit().equals(q2.getUnit())) {
            return Double.compare(q1.getValue().doubleValue(), q2.getValue()
                    .doubleValue());
        }
        return Double.compare(q1.getValue().doubleValue(), q2.to(q1.getUnit())
                .getValue().doubleValue());
    }
}
