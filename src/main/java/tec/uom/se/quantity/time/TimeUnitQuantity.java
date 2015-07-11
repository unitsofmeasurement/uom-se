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

import static tec.uom.se.unit.Units.DAY;
import static tec.uom.se.unit.Units.HOUR;
import static tec.uom.se.unit.Units.MINUTE;
import static tec.uom.se.unit.Units.SECOND;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Time;

import tec.uom.se.quantity.Quantities;

/**
 * Class that represents {@link TimeUnit} in Unit-API
 * @author otaviojava
 * @author keilw
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
        Quantity<Time> seconds = Objects.requireNonNull(quantity).to(SECOND);
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
            return SECOND;
        case MINUTES:
            return MINUTE;
        case HOURS:
            return HOUR;
        case DAYS:
            return DAY;
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
