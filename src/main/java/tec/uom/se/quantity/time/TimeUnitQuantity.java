package tec.uom.se.quantity.time;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Time;

import tec.uom.se.quantity.Quantities;
import tec.uom.se.spi.SI;

/**
 * Class that represents {@link TimeUnit} in measure API
 * @author otaviojava
 */
public class TimeUnitQuantity {

    private TimeUnit timeUnit;

    private Integer value;

    /**
     * creates the {@link TimeUnitQuantity} using {@link TimeUnit} and {@link Integer}
     * @param timeUnit - time to be used
     * @param value - value to be used
     */
    TimeUnitQuantity(TimeUnit timeUnit, Integer value) {
        this.timeUnit = timeUnit;
        this.value = value;
    }

    /**
     * creates the {@link TimeUnitQuantity} using {@link TimeUnit} and {@link Integer}
     * @param timeUnit - time to be used
     * @param value - value to be used
     */
    public static TimeUnitQuantity of(TimeUnit timeUnit, Integer number) {
        return new TimeUnitQuantity(Objects.requireNonNull(timeUnit),
                Objects.requireNonNull(number));
    }

    /**
     * Creates a {@link TimeUnitQuantity} based a {@link Quantity<Time>} converted to
     * {@link SI#SECOND}.
     * @param quantity - quantity to be used
     * @return the {@link TimeUnitQuantity} converted be quantity in seconds.
     */
    public static TimeUnitQuantity of(Quantity<Time> quantity) {
        Quantity<Time> seconds = Objects.requireNonNull(quantity).to(TimeQuantities.SECOND);
        return new TimeUnitQuantity(TimeUnit.SECONDS, seconds.getValue().intValue());
    }


    /**
     * get to {@link TimeUnit}
     * @return the TimeUnit
     */
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    /**
     * get value expressed in {@link Integer}
     * @return the value
     */
    public Integer getValue() {
        return value;
    }

    /**
     * converts the {@link TimeUnit} to {@link Unit}
     * @return the {@link TimeUnitQuantity#getTimeUnit()} converted to Unit
     */
    public Unit<Time> toUnit() {
        return toUnit(timeUnit);
    }

    /**
     * Converts the {@link TimeUnitQuantity} to {@link Quantity<Time>}
     * @return this class converted to Quantity
     */
    public Quantity<Time> toQuantity() {
        return Quantities.getQuantity(value, toUnit());
    }

    public TimeUnitQuantity to(TimeUnit timeUnit) {
        Quantity<Time> time = toQuantity().to(toUnit(timeUnit));
        return new TimeUnitQuantity(timeUnit, time.getValue().intValue());
    }



    private Unit<Time> toUnit(TimeUnit timeUnit) {
        switch (timeUnit) {
        case MICROSECONDS:
            return TimeQuantities.MICROSECOND;
        case MILLISECONDS:
            return TimeQuantities.MILLISECOND;
        case NANOSECONDS:
            return TimeQuantities.NANOSECOND;
        case SECONDS:
            return TimeQuantities.SECOND;
        case MINUTES:
            return TimeQuantities.MINUTE;
        case HOURS:
            return TimeQuantities.HOUR;
        case DAYS:
            return TimeQuantities.DAY;
        default:
            throw new IllegalStateException(
                    "In TimeUnitQuantity just supports DAYS, HOURS, MICROSECONDS, MILLISECONDS, MINUTES, NANOSECONDS, SECONDS ");
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeUnit, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (TimeUnitQuantity.class.isInstance(obj)) {
            TimeUnitQuantity other = TimeUnitQuantity.class.cast(obj);
            return Objects.equals(timeUnit, other.timeUnit)
                    && Objects.equals(value, other.value);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Time unit:" + timeUnit + " value: " + value;
    }

}
