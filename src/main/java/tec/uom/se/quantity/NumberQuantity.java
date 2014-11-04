/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright (c) 2005-2014, Jean-Marie Dautelle, Werner Keil, V2COM.
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
package tec.uom.se.quantity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Objects;

import javax.measure.IncommensurableException;
import javax.measure.Quantity;
import javax.measure.UnconvertibleException;
import javax.measure.Unit;
import javax.measure.UnitConverter;

import tec.uom.se.AbstractQuantity;
import tec.uom.se.function.AbstractConverter;

/**
 * An amount of quantity, consisting of a Number and a Unit.
 * <type>ComparableQuantity</type> objects are immutable.
 *
 * @see AbstractQuantity
 * @see Quantity
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @param <Q>
 *            The type of the quantity.
 * @version 0.9.3, $Date: 2014-10-10 $
 */
public class NumberQuantity<Q extends Quantity<Q>> extends AbstractQuantity<Q>
		implements Serializable { //Comparable<ComparableQuantity<Q>>

	/**
	 *
	 */
	private static final long serialVersionUID = 7312161895652321241L;

	private final Number value;

	/*
	 * (non-Javadoc)
	 *
	 * @see AbstractMeasurement#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Quantity) {
			Quantity<?> other = (Quantity<?>) obj;
			return Objects.equals(getUnit(), other.getUnit())
					&& Objects.equals(getValue(), other.getValue());
		}
		return false;
	}

	/**
	 * Indicates if this measure is exact.
	 */
	private final boolean isExact;

	/**
	 * Indicates if this measure is big.
	 */
	private final boolean isBig;

	/**
	 * Holds the exact value (when exact) stated in this measure unit.
	 */
	// private long exactValue;

	/**
	 * Holds the minimum value stated in this measure unit. For inexact
	 * measures: minimum < maximum
	 */
	// private double minimum;

	/**
	 * Holds the maximum value stated in this measure unit. For inexact
	 * measures: maximum > minimum
	 */
	// private double maximum;

	protected NumberQuantity(Number number, Unit<Q> unit) {
		super(unit);
		value = number;
		isExact = false;
		isBig = number instanceof BigDecimal || number instanceof BigInteger;
	}

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Quantity<?> of(Number number, Unit<?> unit) {
        return new NumberQuantity(number, unit);
    }
    
	/*
	 * (non-Javadoc)
	 *
	 * @see AbstractQuantity#doubleValue(javax.measure.Unit)
	 */
	@Override
    public double doubleValue(Unit<Q> unit) {
		Unit<Q> myUnit = getUnit();
		try {
			UnitConverter converter = unit.getConverterTo(myUnit);
			return converter.convert(getValue().doubleValue());
		} catch (UnconvertibleException e) {
			throw e;
		} // catch (IncommensurableException e) {
			// throw new IllegalArgumentException(e.getMessage());
			// }
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.uomo.units.AbstractMeasurement#longValue(javax.measure
	 * .Unit)
	 */
	@Override
    public long longValue(Unit<Q> unit) {
		Unit<Q> myUnit = getUnit();
		try {
			UnitConverter converter = unit.getConverterToAny(myUnit);
			if ((getValue() instanceof BigDecimal || getValue() instanceof BigInteger)
					&& converter instanceof AbstractConverter) {
				return (((AbstractConverter) converter).convert(
						BigDecimal.valueOf(getValue().longValue()),
						MathContext.DECIMAL128)).longValue();
			} else {
				double result = doubleValue(unit);
				if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
					throw new ArithmeticException("Overflow (" + result + ")");
				}
				return (long) result;
			}
		} catch (UnconvertibleException e) {
			throw e;
		} catch (IncommensurableException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.measure.Quantity#getValue()
	 */
	@Override
    public Number getValue() {
		return value;
	}

	/**
	 * Indicates if this measured amount is exact. An exact amount is guarantee
	 * exact only when stated in this measure unit (e.g.
	 * <code>this.longValue()</code>); stating the amount in any other unit may
	 * introduce conversion errors.
	 *
	 * @return <code>true</code> if this measure is exact; <code>false</code>
	 *         otherwise.
	 */
	public boolean isExact() {
		return isExact;
	}

	/**
	 * Indicates if this measured amount is a big number, i.E. BigDecimal or
	 * BigInteger. In all other cases this would be false.
	 *
	 * @return <code>true</code> if this measure is big; <code>false</code>
	 *         otherwise.
	 */
	@Override
    public boolean isBig() {
		return isBig;
	}

	@Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Quantity<Q> add(Quantity<Q> that) {
		final Quantity<Q> thatToUnit = that.to(getUnit());
		return new NumberQuantity(this.getValue().doubleValue()
				+ thatToUnit.getValue().doubleValue(), getUnit());
	}

	@Override
    public String toString() {
		return String.valueOf(getValue()) + " " + String.valueOf(getUnit());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Quantity<?> multiply(Quantity<?> that) {
		final Unit<?> unit = getUnit().multiply(that.getUnit());
		return new NumberQuantity((getValue().doubleValue() * that.getValue()
				.doubleValue()), unit);
	}

	@Override
	public Quantity<Q> multiply(Number that) {
		return Quantities.getQuantity(
				(getValue().doubleValue() * that.doubleValue()), getUnit());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Quantity<?> divide(Quantity<?> that) {
		final Unit<?> unit = getUnit().divide(that.getUnit());
		return new NumberQuantity((getValue().doubleValue() / that.getValue()
				.doubleValue()), unit);
	}

	@Override
	public Quantity<Q> divide(Number that) {
		// TODO may use isBig() here, too
		if (value instanceof BigDecimal && that instanceof BigDecimal) {
			return Quantities.getQuantity(((BigDecimal) value).divide((BigDecimal) that), getUnit());
		}
		return Quantities.getQuantity(getValue().doubleValue() / that.doubleValue(), getUnit());
	}

	@Override
	public Quantity<Q> inverse() {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final Quantity<Q> m = new NumberQuantity((getValue() instanceof BigDecimal ? 
				BigDecimal.ONE.divide((BigDecimal)getValue()) : 1d / getValue().doubleValue()), getUnit()
				.inverse());
		return m;
	}

	@Override
	public BigDecimal decimalValue(Unit<Q> unit, MathContext ctx)
			throws ArithmeticException {
		if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		}
		if (value instanceof BigInteger) {
			return new BigDecimal((BigInteger) value);
		}
		return BigDecimal.valueOf(value.doubleValue());
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Quantity<Q> subtract(Quantity<Q> that) {
		final Quantity<Q> thatToUnit = that.to(getUnit());
		return new NumberQuantity(this.getValue().doubleValue()
				- thatToUnit.getValue().doubleValue(), getUnit());
	}

    public int compareTo(Quantity<Q> o) {
        return Double.compare(getValue().doubleValue(), o.getValue().doubleValue());
    }
}
