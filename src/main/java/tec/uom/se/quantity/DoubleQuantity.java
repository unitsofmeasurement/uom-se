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
import java.math.MathContext;
import java.util.Comparator;

import javax.measure.Quantity;
import javax.measure.Unit;

import tec.uom.se.AbstractQuantity;
import tec.uom.se.function.AbstractConverter;
import tec.uom.se.function.NaturalOrder;

/**
 * An amount of quantity, consisting of a double and a Unit. DoubleQuantity
 * objects are immutable.
 *
 * @see AbstractQuantity
 * @see Quantity
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @author Otavio de Santana
 * @param <Q>
 *            The type of the quantity.
 * @version 0.3, $Date: 2014-10-10 $
 */
final class DoubleQuantity<Q extends Quantity<Q>> extends AbstractQuantity<Q> implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = 8660843078156312278L;

	final double value;

    public DoubleQuantity(double value, Unit<Q> unit) {
    	super(unit);
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }


    @Override
    public double doubleValue(Unit<Q> unit) {
        return (super.getUnit().equals(unit)) ? value : super.getUnit().getConverterTo(unit).convert(value);
    }

    @Override
    public BigDecimal decimalValue(Unit<Q> unit, MathContext ctx)
            throws ArithmeticException {
        BigDecimal decimal = BigDecimal.valueOf(value); // TODO check value if it is a BD, otherwise use different converter
        return (super.getUnit().equals(unit)) ? decimal : ((AbstractConverter)super.getUnit().getConverterTo(unit)).convert(decimal, ctx);
    }

	@Override
	public long longValue(Unit<Q> unit) {
        double result = doubleValue(unit);
        if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
            throw new ArithmeticException("Overflow (" + result + ")");
        }
        return (long) result;
	}

    @Override
    public Quantity<Q> add(Quantity<Q> that) {
        if (getUnit().equals(that.getUnit())) {
            return Quantities.getQuantity(value + that.getValue().doubleValue(), getUnit());
        }
        Quantity<Q> converted = that.to(getUnit());
        return Quantities.getQuantity(value + converted.getValue().doubleValue(), getUnit());
    }

    @Override
    public Quantity<Q> subtract(Quantity<Q> that) {
        if (getUnit().equals(that.getUnit())) {
            return Quantities.getQuantity(value - that.getValue().doubleValue(), getUnit());
        }
        Quantity<Q> converted = that.to(getUnit());
        return Quantities.getQuantity(value - converted.getValue().doubleValue(), getUnit());
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Quantity<?> multiply(Quantity<?> that) {
		return new DoubleQuantity(value * that.getValue().doubleValue(), getUnit().multiply(that.getUnit()));
	}

	@Override
	public Quantity<Q> multiply(Number that) {
		return Quantities.getQuantity(value * that.doubleValue(), getUnit());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Quantity<?> divide(Quantity<?> that) {
		return new DoubleQuantity(value / that.getValue().doubleValue(), getUnit().divide(that.getUnit()));
	}

	@Override
	public Quantity<Q> divide(Number that) {
		return Quantities.getQuantity(value / that.doubleValue(), getUnit());
	}

	@SuppressWarnings("unchecked")
	@Override
	public AbstractQuantity<Q> inverse() {
		return (AbstractQuantity<Q>) Quantities.getQuantity(1d / value, getUnit().inverse());
	}

	@Override
	public boolean isBig() {
		return false;
	}

	/**
     * @see {@link NaturalOrder}
     */
	@Override
	public int compareTo(Quantity<Q> that) {
	    Comparator<Quantity<Q>> comparator = new NaturalOrder<>();
        return comparator.compare(this, that);
	}
}