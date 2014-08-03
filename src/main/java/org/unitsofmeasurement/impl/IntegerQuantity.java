package org.unitsofmeasurement.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;

import javax.measure.Quantity;
import javax.measure.Unit;

import org.unitsofmeasurement.impl.function.AbstractConverter;

/**
 * An amount of quantity, consisting of a double and a Unit. IntegerQuantity
 * objects are immutable.
 * 
 * @see AbstractQuantity
 * @see Quantity
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @author Otavio de Santana
 * @param <Q>
 *            The type of the quantity.
 * @version 0.2, $Date: 2014-08-03 $
 */
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
	public Quantity<T> add(Quantity<T> that) {
		return of(value + that.getValue().intValue(), getUnit()); // TODO use shift of the unit?
	}

	@Override
	public Quantity<T> subtract(Quantity<T> that) {
		return of(value - that.getValue().intValue(), getUnit()); // TODO use shift of the unit?
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Quantity<?> multiply(Quantity<?> that) {
		return new IntegerQuantity(value * that.getValue().intValue(), getUnit());
	}

	@Override
	public Quantity<T> multiply(Number that) {
		return of(value * that.intValue(), getUnit());
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
	public Quantity<T> divide(Number that) {
		return of(value / that.doubleValue(), getUnit());
	}

}