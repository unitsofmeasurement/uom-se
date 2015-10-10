/*
 * Units of Measurement Implementation for Java SE
 * Copyright (c) 2005-2015, Jean-Marie Dautelle, Werner Keil, V2COM.
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
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Time;

import tec.uom.se.AbstractQuantity;
import tec.uom.se.ComparableQuantity;
import tec.uom.se.quantity.Quantities;

/**
 * Class that represents {@link TemporalUnit} in Unit-API
 * @author keilw
 */
public final class TemporalQuantity extends AbstractQuantity<Time> {
    private final TemporalUnit timeUnit;
    private final Integer value;

    /**
     * creates the {@link TemporalQuantity} using {@link TemporalUnit} and {@link Integer}
     * @param timeUnit - time to be used
     * @param value - value to be used
     */
    TemporalQuantity(Integer value, TemporalUnit timeUnit) {
        super(toUnit(timeUnit));
    	this.timeUnit = timeUnit;
        this.value = value;
    }

    /**
     * creates the {@link TemporalQuantity} using {@link TemporalUnit} and {@link Integer}
     * @param value - value to be used
     * @param timeUnit - time to be used
     */
    public static TemporalQuantity of(Integer number, TemporalUnit timeUnit) {
        return new TemporalQuantity(Objects.requireNonNull(number), 
        		Objects.requireNonNull(timeUnit));
    }

    /**
     * Creates a {@link TemporalQuantity} based a {@link Quantity<Time>} converted to
     * {@link SI#SECOND}.
     * @param quantity - quantity to be used
     * @return the {@link TemporalQuantity} converted be quantity in seconds.
     */
    public static TemporalQuantity of(Quantity<Time> quantity) {
        Quantity<Time> seconds = Objects.requireNonNull(quantity).to(SECOND);
        return new TemporalQuantity(seconds.getValue().intValue(), ChronoUnit.SECONDS);
    }

    /**
     * get to {@link TemporalUnit}
     * @return the TemporalUnit
     */
    public TemporalUnit getTemporalUnit() {
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
     * converts the {@link TemporalUnit} to {@link Unit}
     * @return the {@link TemporalQuantity#getTemporalUnit()} converted to Unit
     */
    public Unit<Time> toUnit() {
        return toUnit(timeUnit);
    }

    /**
     * Converts the {@link TemporalQuantity} to {@link Quantity<Time>}
     * @return this class converted to Quantity
     */
    public Quantity<Time> toQuantity() {
        return Quantities.getQuantity(value, toUnit());
    }

    public TemporalQuantity to(TemporalUnit timeUnit) {
        Quantity<Time> time = toQuantity().to(toUnit(timeUnit));
        return new TemporalQuantity(time.getValue().intValue(), timeUnit);
    }

    private static Unit<Time> toUnit(TemporalUnit timeUnit) {
        if (timeUnit instanceof ChronoUnit) {
        	ChronoUnit chronoUnit = (ChronoUnit)timeUnit;
	    	switch (chronoUnit) {
	        case MICROS:
	            return TimeQuantities.MICROSECOND;
	        case MILLIS:
	            return TimeQuantities.MILLISECOND;
	        case NANOS:
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
	            throw new IllegalArgumentException(
	                    "TemporalQuantity only supports DAYS, HOURS, MICROS, MILLIS, MINUTES, NANOS, SECONDS ");
	        }
        } else {
            throw new IllegalArgumentException(
                    "TemporalQuantity only supports temporal units of type ChronoUnit");

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
        if (TemporalQuantity.class.isInstance(obj)) {
            TemporalQuantity other = TemporalQuantity.class.cast(obj);
            return Objects.equals(timeUnit, other.timeUnit)
                    && Objects.equals(value, other.value);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Time unit:" + timeUnit + " value: " + value;
    }

    @Override
    public ComparableQuantity<Time> add(Quantity<Time> that) {
        if (getUnit().equals(that.getUnit())) {
            return TimeQuantities.getQuantity(value + that.getValue().intValue(), timeUnit);
        }
        Quantity<Time> converted = that.to(getUnit());
        return TimeQuantities.getQuantity(value + converted.getValue().intValue(), timeUnit);
    }

	@Override
	public ComparableQuantity<Time> subtract(Quantity<Time> that) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComparableQuantity<?> divide(Quantity<?> that) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComparableQuantity<Time> divide(Number that) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComparableQuantity<?> multiply(Quantity<?> multiplier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComparableQuantity<Time> multiply(Number multiplier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComparableQuantity<?> inverse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBig() {
		return false;
	}

	@Override
	public BigDecimal decimalValue(Unit<Time> unit, MathContext ctx)
			throws ArithmeticException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double doubleValue(Unit<Time> unit) throws ArithmeticException {
		// TODO Auto-generated method stub
		return 0;
	}
}
