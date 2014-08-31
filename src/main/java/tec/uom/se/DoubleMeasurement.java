package tec.uom.se;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.measure.Measurement;
import javax.measure.Quantity;
import javax.measure.Unit;

import tec.uom.se.function.AbstractConverter;

/**
 * 
 * @author Werner
 *
 * @param <T>
 * 
 * @deprecated We probably don't need this as it duplicates DoubleQuantity?
 */
final class DoubleMeasurement<T extends Quantity<T>> extends AbstractMeasurement<T, Double> {

    final double value;

    public DoubleMeasurement(double value, Unit<T> unit) {
    	super(unit);
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }


    @Override
    public double doubleValue(Unit<T> unit) {
        return (super.unit.equals(unit)) ? value : super.unit.getConverterTo(unit).convert(value);
    }

    @Override
    public BigDecimal decimalValue(Unit<T> unit, MathContext ctx)
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

	protected Measurement<T, Number> add(AbstractMeasurement<T, Double> that) {
		return new DoubleMeasurement(value + that.getValue().doubleValue(), getUnit()); // TODO use shift of the unit?
	}

	protected Measurement<T, Number> subtract(AbstractMeasurement<T, Number> that) {
		return new DoubleMeasurement(value - that.getValue().doubleValue(), getUnit()); // TODO use shift of the unit?
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Measurement<?, Number> multiply(AbstractMeasurement<T, Number> that) {
		return new DoubleMeasurement(value * that.getValue().doubleValue(), getUnit().multiply(that.getUnit()));
	}

	public Measurement<T, Number> multiply(Number that) {
		return new DoubleMeasurement(value * that.doubleValue(), getUnit());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Measurement<?, Number> divide(AbstractMeasurement<T, Number> that) {
		return new DoubleMeasurement(value / that.getValue().doubleValue(), getUnit().divide(that.getUnit()));
	}

	public Measurement<T, Number> divide(Number that) {
		return new DoubleMeasurement(value / that.doubleValue(), getUnit());
	}

	@SuppressWarnings("unchecked")
	protected AbstractMeasurement<T, Double> inverse() {
		return (AbstractMeasurement<T, Double>) of(value, getUnit().inverse());
	}

	@Override
	public boolean isBig() {
		return false;
	}
}