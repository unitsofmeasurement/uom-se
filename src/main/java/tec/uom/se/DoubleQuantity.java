package tec.uom.se;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;

import javax.measure.Quantity;
import javax.measure.Unit;

import tec.uom.se.function.AbstractConverter;

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
 * @version 0.2, $Date: 2014-08-03 $
 */
final class DoubleQuantity<T extends Quantity<T>> extends AbstractQuantity<T> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8660843078156312278L;
	
	final double value;

    public DoubleQuantity(double value, Unit<T> unit) {
    	super(unit);
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }


    public double doubleValue(Unit<T> unit) {
        return (super.getUnit().equals(unit)) ? value : super.getUnit().getConverterTo(unit).convert(value);
    }

    @Override
    public BigDecimal decimalValue(Unit<T> unit, MathContext ctx)
            throws ArithmeticException {
        BigDecimal decimal = BigDecimal.valueOf(value); // TODO check value if it is a BD, otherwise use different converter
        return (super.getUnit().equals(unit)) ? decimal : ((AbstractConverter)super.getUnit().getConverterTo(unit)).convert(decimal, ctx);
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
		return of(value + that.getValue().doubleValue(), getUnit()); // TODO use shift of the unit?
	}

	@Override
	public  Quantity<T> subtract( Quantity<T> that) {
		return of(value - that.getValue().doubleValue(), getUnit()); // TODO use shift of the unit?
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Quantity<?> multiply(Quantity<?> that) {
		return new DoubleQuantity(value * that.getValue().doubleValue(), getUnit().multiply(that.getUnit()));
	}

	@Override
	public Quantity<T> multiply(Number that) {
		return of(value * that.doubleValue(), getUnit());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Quantity<?> divide(Quantity<?> that) {
		return new DoubleQuantity(value / that.getValue().doubleValue(), getUnit().divide(that.getUnit()));
	}
	
	@Override
	public Quantity<T> divide(Number that) {
		return of(value / that.doubleValue(), getUnit());
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
}