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
package org.unitsofmeasurement.impl;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.measure.Measurement;
import javax.measure.Quantity;
import javax.measure.Unit;

import org.unitsofmeasurement.impl.function.AbstractConverter;

final class FloatMeasurement<T extends Quantity<T>> extends AbstractMeasurement<T, Float> {

	final float value;

    public FloatMeasurement(float value, Unit<T> unit) {
    	super(unit);
        this.value = value;
    }

    @Override
    public Float getValue() {
        return value;
    }

    // Implements AbstractMeasurement
    @Override
    public double doubleValue(Unit<T> unit) {
        return (super.unit.equals(unit)) ? value : super.unit.getConverterTo(unit).convert(value);
    }

    // Implements AbstractMeasurement
    @Override
    protected BigDecimal decimalValue(Unit<T> unit, MathContext ctx)
            throws ArithmeticException {
        BigDecimal decimal = BigDecimal.valueOf(value); // TODO check value if it is a BD, otherwise use different converter
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected AbstractMeasurement<T, Float> add(AbstractMeasurement<T, Float> that) {
		return new FloatMeasurement(value + that.getValue().floatValue(), getUnit()); // TODO use shift of the unit?
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected AbstractMeasurement<T, Float> subtract(AbstractMeasurement<T, Float> that) {
		return new FloatMeasurement(value - that.getValue().floatValue(), getUnit()); // TODO use shift of the unit?
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected AbstractMeasurement<T, Float> multiply(AbstractMeasurement<T, Float> that) {
		return new FloatMeasurement(value * that.getValue().floatValue(),
				getUnit().multiply(that.getUnit()));
	}

	protected Measurement<T, Float> multiply(Number that) {
		return new FloatMeasurement<T>(value * that.floatValue(),
				getUnit().multiply(that.floatValue()));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Measurement<?, Number> divide(AbstractMeasurement<T, Float> that) {
		return new FloatMeasurement(value / that.getValue().floatValue(), getUnit().divide(that.getUnit()));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AbstractMeasurement<T, Float> inverse() {
		return new FloatMeasurement(value, getUnit().inverse());
	}

	@Override
    public boolean isBig() {
		return false;
	}

	@SuppressWarnings("rawtypes")
	public Measurement<T, Float> divide(Number that) {
		return new FloatMeasurement(value / that.floatValue(), getUnit());
	}
}