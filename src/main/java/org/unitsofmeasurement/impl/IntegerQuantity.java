package org.unitsofmeasurement.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;

import javax.measure.Measurement;
import javax.measure.Quantity;
import javax.measure.Unit;

import org.unitsofmeasurement.impl.function.AbstractConverter;

final class IntegerQuantity<T extends Quantity<T>> extends AbstractQuantity<T>
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5355395476874521709L;

	final int value;

	public IntegerQuantity(int value, Unit<T> unit) {
		super(unit);
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;
	}

	// Implements Measurement
	public double doubleValue(Unit<T> unit) {
		return (super.getUnit().equals(unit)) ? value : super.getUnit()
				.getConverterTo(unit).convert(value);
	}

	// Implements Measurement
	public BigDecimal decimalValue(Unit<T> unit, MathContext ctx)
			throws ArithmeticException {
		BigDecimal decimal = BigDecimal.valueOf(value);
		return (super.getUnit().equals(unit)) ? decimal
				: ((AbstractConverter) super.getUnit().getConverterTo(unit))
						.convert(decimal, ctx);
	}

	@Override
	public long longValue(Unit<T> unit) {
		double result = doubleValue(unit);
		if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
			throw new ArithmeticException("Overflow (" + result + ")");
		}
		return (long) result;
	}

	@Override
	public Measurement<T, Number> add(Measurement<T, Number> that) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntegerQuantity<T> substract(Measurement<T, Number> that) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Quantity<?> divide(Quantity<?> that) {
		return new DoubleQuantity((double) value / that.getValue().doubleValue(), getUnit()
				.divide(that.getUnit()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public AbstractQuantity<T> inverse() {
		return (AbstractQuantity<T>) of(value, getUnit().inverse());
	}

	@Override
	public boolean isBig() {
		return false;
	}

	@Override
	public Measurement<T, Number> multiply(Number that) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Measurement<T, Number> divide(Number that) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Measurement<?, Number> multiply(Measurement<?, Number> that) {
		// TODO Auto-generated method stub
		return null;
	}

}