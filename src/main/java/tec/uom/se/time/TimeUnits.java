package tec.uom.se.time;

import javax.measure.quantity.Time;

import tec.uom.se.function.RationalConverter;
import tec.uom.se.model.QuantityDimension;
import tec.uom.se.unit.BaseUnit;
import tec.uom.se.unit.TransformedUnit;
import tec.uom.se.util.SI;
import tec.uom.se.util.SIPrefix;

public final class TimeUnits {

    private TimeUnits() {
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

}
