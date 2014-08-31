/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright 2010-2014, Jean-Marie Dautelle, Werner Keil, V2COM and individual
 *  contributors by the @author tag.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package tec.uom.se;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.measure.Measurement;
import javax.measure.Quantity;
import javax.measure.Unit;

import tec.uom.se.function.AbstractConverter;

@SuppressWarnings({ "rawtypes", "unchecked" })
final class IntegerMeasurement<T extends Quantity<T>> extends AbstractMeasurement<T, Integer> {

	final int value;

    public IntegerMeasurement(int value, Unit<T> unit) {
    	super(unit);
    	this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    // Implements Measurement
    @Override
    public double doubleValue(Unit<T> unit) {
        return (super.unit.equals(unit)) ? value : super.unit.getConverterTo(unit).convert(value);
    }

    // Implements Measurement
    @Override
    public BigDecimal decimalValue(Unit<T> unit, MathContext ctx)
            throws ArithmeticException {
        BigDecimal decimal = BigDecimal.valueOf(value);
        return (super.unit.equals(unit)) ? decimal : ((AbstractConverter)super.unit.getConverterTo(unit)).convert(decimal, ctx);
    }

	@Override
	public long longValue(Unit<T> unit) {
        double result = doubleValue(unit);
        if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
            throw new ArithmeticException("Overflow (" + result + ")");
        }
        return (long) result;
	}

    protected Measurement<T, Number> add(AbstractMeasurement<T, Number> that) {
		return new IntegerMeasurement(value + that.getValue().intValue(), getUnit()); // TODO use shift of the unit?
	}

	protected Measurement<T, Number> subtract(AbstractMeasurement<T, Number> that) {
		return new IntegerMeasurement(value - that.getValue().intValue(), getUnit()); // TODO use shift of the unit?
	}

	public Measurement<?, Number> multiply(AbstractMeasurement<T, Number> that) {
		return new IntegerMeasurement(value * that.getValue().intValue(),
				getUnit().multiply(that.getUnit()));
	}

	public Measurement<T, Number> multiply(Number that) {
		return new IntegerMeasurement(value * that.intValue(),
				getUnit().multiply(that.intValue()));
	}

	public Measurement<?, Number> divide(AbstractMeasurement<T, Number> that) {
		return new DoubleMeasurement(value / that.getValue().doubleValue(), getUnit().divide(that.getUnit()));
	}

	public Measurement<T, Number> inverse() {
		return new IntegerMeasurement(value, getUnit().inverse());
	}

	@Override
	public boolean isBig() {
		return false;
	}

	public Measurement<T, Number> divide(Number that) {
		return new IntegerMeasurement(value / that.intValue(), getUnit());
	}

}