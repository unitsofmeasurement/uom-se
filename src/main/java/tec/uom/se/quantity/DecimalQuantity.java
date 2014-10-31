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

import javax.measure.Quantity;
import javax.measure.Unit;

import tec.uom.se.AbstractQuantity;
import tec.uom.se.function.AbstractConverter;

final class DecimalQuantity<Q extends Quantity<Q>> extends AbstractQuantity<Q> implements Serializable {

	private static final long serialVersionUID = 6504081836032983882L;

	private final BigDecimal value;

    public DecimalQuantity(BigDecimal value, Unit<Q> unit) {
    	super(unit);
    	this.value = value;
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public double doubleValue(Unit<Q> unit) {
        return (unit.equals(unit)) ? value.doubleValue() : unit.getConverterTo(unit).convert(value.doubleValue());
    }

    @Override
    public BigDecimal decimalValue(Unit<Q> unit, MathContext ctx)
            throws ArithmeticException {
        return (super.getUnit().equals(unit)) ? value :
        	((AbstractConverter)unit.getConverterTo(unit)).convert(value, ctx);
    }

    @Override
    public Quantity<Q> add(Quantity<Q> that) {
        if (getUnit().equals(that.getUnit())) {
            return Quantities.getQuantity(value.add(
                    toBigDecimal(that.getValue()), MathContext.DECIMAL128),
                    getUnit());
        }
        Quantity<Q> converted = that.to(getUnit());
        return Quantities.getQuantity(
                value.add(toBigDecimal(converted.getValue())), getUnit());
    }

    @Override
    public Quantity<Q> subtract(Quantity<Q> that) {
        if (getUnit().equals(that.getUnit())) {
            return Quantities.getQuantity(value.subtract(
                    toBigDecimal(that.getValue()), MathContext.DECIMAL128),
                    getUnit());
        }
        Quantity<Q> converted = that.to(getUnit());
        return Quantities.getQuantity(value.subtract(
                toBigDecimal(converted.getValue()), MathContext.DECIMAL128),
                getUnit());
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Quantity<?> multiply(Quantity<?> that) {
		return new DecimalQuantity(value.multiply(toBigDecimal(that.getValue()), MathContext.DECIMAL128),
				getUnit().multiply(that.getUnit()));
	}

	@Override
	public Quantity<Q> multiply(Number that) {
        return Quantities.getQuantity(
                value.multiply(toBigDecimal(that), MathContext.DECIMAL128),
                getUnit());
	}

	@Override
	public Quantity<Q> divide(Number that) {
        return Quantities.getQuantity(
                value.divide(toBigDecimal(that), MathContext.DECIMAL128),
                getUnit());
	}


	@SuppressWarnings("unchecked")
	@Override
	public Quantity<Q> inverse() {
		return (Quantity<Q>) Quantities.getQuantity(BigDecimal.ONE.divide(value), getUnit().inverse());
	}

	@Override
    protected long longValue(Unit<Q> unit) {
        double result = doubleValue(unit);
        if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
            throw new ArithmeticException("Overflow (" + result + ")");
        }
        return (long) result;
	}

	@Override
	public boolean isBig() {
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Quantity<?> divide(Quantity<?> that) {
        return new DecimalQuantity(value.divide(toBigDecimal(that.getValue()),
                MathContext.DECIMAL128), getUnit().divide(that.getUnit()));
	}

	private BigDecimal toBigDecimal(Number value) {
        if (BigDecimal.class.isInstance(value)) {
            return BigDecimal.class.cast(value);
        } else if (BigInteger.class.isInstance(value)) {
            return new BigDecimal(BigInteger.class.cast(value));
        }
        return BigDecimal.valueOf(value.doubleValue());
    }
}