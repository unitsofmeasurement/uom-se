package tec.uom.se;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import javax.measure.Quantity;
import javax.measure.Unit;

import tec.uom.se.function.AbstractConverter;

final class DecimalQuantity<T extends Quantity<T>> extends AbstractQuantity<T> implements Serializable {

	private static final long serialVersionUID = 6504081836032983882L;

	private final BigDecimal value;

    public DecimalQuantity(BigDecimal value, Unit<T> unit) {
    	super(unit);
    	this.value = value;
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public double doubleValue(Unit<T> unit) {
        return (unit.equals(unit)) ? value.doubleValue() : unit.getConverterTo(unit).convert(value.doubleValue());
    }

    @Override
    public BigDecimal decimalValue(Unit<T> unit, MathContext ctx)
            throws ArithmeticException {
        return (super.getUnit().equals(unit)) ? value :
        	((AbstractConverter)unit.getConverterTo(unit)).convert(value, ctx);
    }

	@Override
	public Quantity<T> add(Quantity<T> that) {
		return of(value.add(toBigDecimal(that.getValue())), getUnit()); // TODO use shift of the unit?
	}

	@Override
	public Quantity<T> subtract(Quantity<T> that) {
		return of(value.subtract(toBigDecimal(that.getValue())), getUnit()); // TODO use shift of the unit?
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Quantity<?> multiply(Quantity<?> that) {
		return new DecimalQuantity(value.multiply(toBigDecimal(that.getValue())),
				getUnit().multiply(that.getUnit()));
	}

	@Override
	public Quantity<T> multiply(Number that) {
		return of(value.multiply(toBigDecimal(that)), getUnit());
	}

	@Override
	public Quantity<T> divide(Number that) {
		return of(value.divide(toBigDecimal(that)), getUnit());
	}


	@SuppressWarnings("unchecked")
	@Override
	public Quantity<T> inverse() {
		return (AbstractQuantity<T>) of(value, getUnit().inverse());
	}

	@Override
    protected long longValue(Unit<T> unit) {
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
		return new DecimalQuantity(value.divide((BigDecimal)that.getValue()), getUnit());
	}

	@Override
	public String toString() {
	    return value + " " + getUnit();
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