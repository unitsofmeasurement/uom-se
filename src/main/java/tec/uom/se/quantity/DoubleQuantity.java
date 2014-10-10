package tec.uom.se.quantity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;

import javax.measure.Quantity;
import javax.measure.Unit;

import tec.uom.se.AbstractQuantity;
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
	public <T extends Quantity<T>, R extends Quantity<R>> Quantity<R> multiply(Quantity<T> that) {
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
		return (AbstractQuantity<Q>) Quantities.getQuantity(value, getUnit().inverse());
	}

	@Override
	public boolean isBig() {
		return false;
	}
}