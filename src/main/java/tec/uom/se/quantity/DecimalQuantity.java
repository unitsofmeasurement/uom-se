package tec.uom.se.quantity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import javax.measure.Quantity;
import javax.measure.Unit;

import tec.uom.se.AbstractQuantity;
import tec.uom.se.function.AbstractConverter;

final class DecimalQuantity<Q extends Quantity<Q>> extends AbstractQuantity<Q> implements Serializable {

	private static final long serialVersionUID = 6504081836032983882L;

	private final BigDecimal value;

    public DecimalQuantity(BigDecimal value, Unit<Q> unit) {
    	super(unit);
    	this.value = value;
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public double doubleValue(Unit<Q> unit) {
        return (unit.equals(unit)) ? value.doubleValue() : unit.getConverterTo(unit).convert(value.doubleValue());
    }

    @Override
    public BigDecimal decimalValue(Unit<Q> unit, MathContext ctx)
            throws ArithmeticException {
        return (super.getUnit().equals(unit)) ? value :
        	((AbstractConverter)unit.getConverterTo(unit)).convert(value, ctx);
    }

    @Override
    public Quantity<Q> add(Quantity<Q> that) {
        if (getUnit().equals(that.getUnit())) {
            return Quantities.getQuantity(value.add(
                    toBigDecimal(that.getValue()), MathContext.DECIMAL128),
                    getUnit());
        }
        Quantity<Q> converted = that.to(getUnit());
        return Quantities.getQuantity(
                value.add(toBigDecimal(converted.getValue())), getUnit());
    }

    @Override
    public Quantity<Q> subtract(Quantity<Q> that) {
        if (getUnit().equals(that.getUnit())) {
            return Quantities.getQuantity(value.subtract(
                    toBigDecimal(that.getValue()), MathContext.DECIMAL128),
                    getUnit());
        }
        Quantity<Q> converted = that.to(getUnit());
        return Quantities.getQuantity(value.subtract(
                toBigDecimal(converted.getValue()), MathContext.DECIMAL128),
                getUnit());
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Quantity<?> multiply(Quantity<?> that) {
		return new DecimalQuantity(value.multiply(toBigDecimal(that.getValue()), MathContext.DECIMAL128),
				getUnit().multiply(that.getUnit()));
	}

	@Override
	public Quantity<Q> multiply(Number that) {
        return Quantities.getQuantity(
                value.multiply(toBigDecimal(that), MathContext.DECIMAL128),
                getUnit());
	}

	@Override
	public Quantity<Q> divide(Number that) {
        return Quantities.getQuantity(
                value.divide(toBigDecimal(that), MathContext.DECIMAL128),
                getUnit());
	}


	@SuppressWarnings("unchecked")
	@Override
	public Quantity<Q> inverse() {
		return (Quantity<Q>) Quantities.getQuantity(BigDecimal.ONE.divide(value), getUnit().inverse());
	}

	@Override
    protected long longValue(Unit<Q> unit) {
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
        return new DecimalQuantity(value.divide(toBigDecimal(that.getValue()),
                MathContext.DECIMAL128), getUnit().divide(that.getUnit()));
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