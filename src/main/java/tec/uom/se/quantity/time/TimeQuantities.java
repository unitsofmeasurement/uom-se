package tec.uom.se.quantity.time;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import javax.measure.Quantity;
import javax.measure.quantity.Time;

import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.BaseUnit;
import tec.uom.se.unit.TransformedUnit;
import tec.uom.se.util.SI;
import tec.uom.se.util.SIPrefix;

public final class TimeQuantities {

    private TimeQuantities() {
    }

    /**
     * @see {@link SI#MINUTE}
     */
    public static final TransformedUnit<Time> MINUTE = SI.MINUTE;

    /**
     * @see {@link SI#HOUR}
     */
    public static final TransformedUnit<Time> HOUR = SI.HOUR;

    /**
     * @see {@link SI#DAY}
     */
    public static final TransformedUnit<Time> DAY = SI.DAY;

    /**
     * @see {@link SI#SECOND}
     */
    public static final BaseUnit<Time> SECOND = SI.SECOND;

    public static final TransformedUnit<Time> MICROSECOND = new TransformedUnit<Time>(
            "μs", SI.SECOND, SIPrefix.MICRO.getConverter());

    public static final TransformedUnit<Time> MILLISECOND = new TransformedUnit<Time>(
            "ms", SI.SECOND, SIPrefix.MILLI.getConverter());

    public static final TransformedUnit<Time> NANOSECOND = new TransformedUnit<Time>(
            "ns", SI.SECOND, SIPrefix.NANO.getConverter());


    /**
     * Creates the {@link Quantity<Time>} based in the difference of the two {@link Temporal}
     * @param temporalA - First parameter to range, inclusive
     * @param temporalB - second parameter to range, exclusive
     * @return the Quantity difference based in {@link SI#DAY}.
     * @java.time.temporal.UnsupportedTemporalTypeException if some temporal doesn't support {@link ChronoUnit#DAYS}
     */
    public static Quantity<Time> getQuantity(Temporal temporalA, Temporal temporalB) {
        long days = ChronoUnit.DAYS.between(temporalA, temporalB);
        return Quantities.getQuantity(days, SI.DAY);
    }

    /**
     * Creates the {@link Quantity<Time>} based in the difference of the two {@link LocalTime}
     * @param localTimeA - First parameter to range, inclusive
     * @param localTimeB - second parameter to range, exclusive
     * @return the Quantity difference based in {@link SI#HOUR}.
     * @java.time.temporal.UnsupportedTemporalTypeException if some temporal doesn't support {@link ChronoUnit#DAYS}
     */
    public static Quantity<Time> getQuantity(LocalTime localTimeA, LocalTime localTimeB) {
        long hours = ChronoUnit.HOURS.between(localTimeA, localTimeB);
        return Quantities.getQuantity(hours, SI.HOUR);
    }

    /**
     * Creates the {@link Quantity<Time>} based in the {@link Temporal} with {@link TemporalAdjuster}
     * @param temporalA - temporal
     * @param supplier the adjust @see {@link TemporalAdjuster}
     * @return The Quantity based in Temporal with TemporalAdjuster in {@link SI#DAY}.
     * @java.time.temporal.UnsupportedTemporalTypeException if some temporal doesn't support {@link ChronoUnit#DAYS}
     */
    public static Quantity<Time> getQuantity(Temporal temporalA, Supplier<TemporalAdjuster> supplier) {
        Temporal temporalB = temporalA.with(supplier.get());
        return getQuantity(temporalA, temporalB);
    }

    /**
     * Creates the {@link Quantity<Time>} based in the {@link Temporal} with {@link TemporalAdjuster}
     * @param localTimeA @see {@link LocalTime}
     * @param supplier he adjust @see {@link TemporalAdjuster}
     * @return The Quantity based in Temporal with TemporalAdjuster in {@link SI#DAY}.
     * @java.time.temporal.UnsupportedTemporalTypeException if some temporal doesn't support {@link ChronoUnit#DAYS}
     */
    public static Quantity<Time> getQuantity(LocalTime localTimeA, Supplier<TemporalAdjuster> supplier) {
        LocalTime localTimeB = localTimeA.with(supplier.get());
        return getQuantity(localTimeA, localTimeB);
    }

    /**
     * creates the {@link TimeUnitQuantity} using {@link TimeUnit} and {@link Integer}
     * @param timeUnit - time to be used
     * @param value - value to be used
     */
    public static TimeUnitQuantity getQuantity(TimeUnit timeUnit, Integer number) {
        return new TimeUnitQuantity(Objects.requireNonNull(timeUnit),
                Objects.requireNonNull(number));
    }

    /**
     * Creates a {@link TimeUnitQuantity} based a {@link Quantity<Time>} converted to
     * {@link SI#SECOND}.
     * @param quantity - quantity to be used
     * @return the {@link TimeUnitQuantity} converted be quantity in seconds.
     */
    public static TimeUnitQuantity getQuantity(Quantity<Time> quantity) {
        Quantity<Time> seconds = Objects.requireNonNull(quantity).to(TimeQuantities.SECOND);
        return new TimeUnitQuantity(TimeUnit.SECONDS, seconds.getValue().intValue());
    }



}
