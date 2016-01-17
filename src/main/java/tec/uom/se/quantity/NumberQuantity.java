/*
 * Units of Measurement Implementation for Java SE
 * Copyright (c) 2005-2016, Jean-Marie Dautelle, Werner Keil, V2COM.
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

import javax.measure.Quantity;
import javax.measure.UnconvertibleException;
import javax.measure.Unit;
import javax.measure.UnitConverter;

import tec.uom.se.AbstractQuantity;
import tec.uom.se.ComparableQuantity;

/**
 * An amount of quantity, implementation of {@link ComparableQuantity} that keep {@link Number} as possible otherwise
 * converts to {@link DecimalQuantity}, this object is immutable.
 *
 * @see AbstractQuantity
 * @see Quantity
 * @see ComparableQuantity
 * @param <Q> The type of the quantity.
 * @author otaviojava
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.9.4, $Date: 2015-10-10 $
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class NumberQuantity<Q extends Quantity<Q>> extends AbstractQuantity<Q>	implements Serializable {

    private static final long serialVersionUID = 7312161895652321241L;

	private final Number value;


	/**
	 * Indicates if this measure is big.
	 */
	private final boolean isBig;

	protected NumberQuantity(Number number, Unit<Q> unit) {
		super(unit);
		value = number;
		isBig = number instanceof BigDecimal || number instanceof BigInteger;
	}

	@Override
    public double doubleValue(Unit<Q> unit) {
		Unit<Q> myUnit = getUnit();
		try {
			UnitConverter converter = unit.getConverterTo(myUnit);
			return converter.convert(getValue().doubleValue());
		} catch (UnconvertibleException e) {
			throw e;
		}
	}

	@Override
    public Number getValue() {
		return value;
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
	public ComparableQuantity<Q> add(Quantity<Q> that) {
	    return toDecimalQuantity().add(that);
	}

	@Override
	public ComparableQuantity<?> multiply(Quantity<?> that) {
	    return toDecimalQuantity().multiply(that);
	}

	@Override
	public ComparableQuantity<Q> multiply(Number that) {
	    return toDecimalQuantity().multiply(that);
	}

	@Override
	public ComparableQuantity<?> divide(Quantity<?> that) {
	    return toDecimalQuantity().divide(that);
	}

	@Override
	public ComparableQuantity<Q> divide(Number that) {
	    return toDecimalQuantity().divide(that);
	}

	@Override
	public ComparableQuantity<Q> inverse() {

		return new NumberQuantity((getValue() instanceof BigDecimal ?
				BigDecimal.ONE.divide((BigDecimal)getValue()) : 1d / getValue().doubleValue()), getUnit()
				.inverse());
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


	@Override
	public ComparableQuantity<Q> subtract(Quantity<Q> that) {
		return toDecimalQuantity().subtract(that);
	}

	private DecimalQuantity<Q> toDecimalQuantity() {
	    return new DecimalQuantity<>(BigDecimal.valueOf(value.doubleValue()), getUnit());
	}
}
