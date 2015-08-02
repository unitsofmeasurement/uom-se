/*
 *  Unit-API - Units of Measurement API for Java
 *  Copyright (c) 2005-2015, Jean-Marie Dautelle, Werner Keil, V2COM.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 *    and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of JSR-363 nor the names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package tec.uom.se.quantity.time;

import static tec.uom.se.unit.Units.SECOND;
import static tec.uom.se.unit.Units.HOUR;
import static tec.uom.se.unit.Units.DAY;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Time;

import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.MetricPrefix;
import tec.uom.se.unit.TransformedUnit;

public final class TimeQuantities {

    private TimeQuantities() {
    }

    public static final Unit<Time> MICROSECOND = new TransformedUnit<>(
            "μs", SECOND, MetricPrefix.MICRO.getConverter());

    public static final TransformedUnit<Time> MILLISECOND = new TransformedUnit<>(
            "ms", SECOND, MetricPrefix.MILLI.getConverter());

    public static final TransformedUnit<Time> NANOSECOND = new TransformedUnit<>(
            "ns", SECOND, MetricPrefix.NANO.getConverter());


    /**
     * Creates the {@link Quantity<Time>} based in the difference of the two {@link Temporal}
     * @param temporalA - First parameter to range, inclusive
     * @param temporalB - second parameter to range, exclusive
     * @return the Quantity difference based in {@link SI#DAY}.
     * @java.time.temporal.UnsupportedTemporalTypeException if some temporal doesn't support {@link ChronoUnit#DAYS}
     */
    public static Quantity<Time> getQuantity(Temporal temporalA, Temporal temporalB) {
        long days = ChronoUnit.DAYS.between(temporalA, temporalB);
        return Quantities.getQuantity(days, DAY);
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
        return Quantities.getQuantity(hours, HOUR);
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
     * Creates the {@link Quantity<Time>} based in the {@link Temporal} with {@link Supplier<TemporalAdjuster>}
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
        Quantity<Time> seconds = Objects.requireNonNull(quantity).to(SECOND);
        return new TimeUnitQuantity(TimeUnit.SECONDS, seconds.getValue().intValue());
    }



}
