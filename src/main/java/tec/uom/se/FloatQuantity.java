package tec.uom.se;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;

import javax.measure.Quantity;
import javax.measure.Unit;

import tec.uom.se.function.AbstractConverter;

/**
 * An amount of quantity, consisting of a float and a Unit. FloatQuantity
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
final class FloatQuantity<T extends Quantity<T>> extends AbstractQuantity<T> 
   implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7857472738562215118L;

	final float value;

	public FloatQuantity(float value, Unit<T> unit) {
		super(unit);
		this.value = value;
	}

	@Override
	public Float getValue() {
		return value;
	}

	// Implements AbstractMeasurement
	public double doubleValue(Unit<T> unit) {
		return (super.getUnit().equals(unit)) ? value : super.getUnit()
				.getConverterTo(unit).convert(value);
	}

	// Implements AbstractMeasurement
	public BigDecimal decimalValue(Unit<T> unit, MathContext ctx)
			throws ArithmeticException {
		BigDecimal decimal = BigDecimal.valueOf(value); // TODO check value if
														// it is a BD, otherwise
														// use different
														// converter
		return (super.getUnit().equals(unit)) ? decimal
				: ((AbstractConverter) super.getUnit().getConverterTo(unit))
						.convert(decimal, ctx);
	}

	public long longValue(Unit<T> unit) {
		double result = doubleValue(unit);
		if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
			throw new ArithmeticException("Overflow (" + result + ")");
		}
		return (long) result;
	}

	@Override
	public Quantity<T> add( Quantity<T> that) {
		return of(value + that.getValue().floatValue(), getUnit()); // TODO use
																	// shift of
																	// the unit?
	}

	@Override
	public Quantity<T> subtract(Quantity<T> that) {
		return of(value - that.getValue().floatValue(), getUnit()); // TODO use
																	// shift of
																	// the unit?
	}

	@SuppressWarnings("unchecked")
	@Override
	public Quantity<?> multiply(Quantity<?> that) {
		return (AbstractQuantity<T>) of(value * that.getValue().floatValue(),
				getUnit().multiply(that.getUnit()));
	}

	@Override
	public Quantity<T> multiply(Number that) {
		return of(value * that.floatValue(),
				getUnit().multiply(that.floatValue()));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Quantity<?> divide(Quantity<?> that) {
		return new FloatQuantity(value / that.getValue().floatValue(),
				getUnit().divide(that.getUnit()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Quantity<T> inverse() {
		return (AbstractQuantity<T>) of(value, getUnit().inverse());
	}

	@Override
	public boolean isBig() {
		return false;
	}

	@Override
	public Quantity<T> divide(Number that) {
		return of(value / that.floatValue(), getUnit());
	}
}