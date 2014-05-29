/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright 2013-2014, Jean-Marie Dautelle, Werner Keil, V2COM and individual
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

import static javax.measure.format.FormatBehavior.LOCALE_NEUTRAL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParsePosition;

import javax.measure.Measurement;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.ParserException;
import javax.measure.quantity.Dimensionless;

import org.unitsofmeasurement.impl.format.MeasurementFormat;
import org.unitsofmeasurement.impl.format.QuantityFormat;
import org.unitsofmeasurement.impl.function.AbstractConverter;
import org.unitsofmeasurement.impl.util.SI;

/**
 * <p> This class represents the immutable result of a scalar measurement stated
 *     in a known unit.</p>
 * 
 * <p> To avoid any lost of precision, known exact measure (e.g. physical 
 *     constants) should not be created from <code>double</code> constants but
 *     from their decimal representation.<br/><code>
 *         public static final Measurement<Number, Velocity> C = AbstractMeasurement.of("299792458 m/s").asType(Velocity.class);
 *         // Speed of Light (exact).
 *    </code></p>
 * 
 * <p> Measures can be converted to different units, the conversion precision is
 *     determined by the specified {@link MathContext}.<br/><code>
 *         Measurement<Number, Velocity> milesPerHour = C.to(MILES_PER_HOUR, MathContext.DECIMAL128); // Use BigDecimal implementation.
 *         System.out.println(milesPerHour);
 * 
 *         > 670616629.3843951324266284896206156 [mi_i]/h
 *     </code>
 *     If no precision is specified <code>double</code> precision is assumed.<code>
 *         Measurement<Double, Velocity> milesPerHour = C.to(MILES_PER_HOUR); // Use double implementation (fast).
 *         System.out.println(milesPerHour);
 * 
 *         > 670616629.3843951 [mi_i]/h
 *     </code></p>
 * 
 * <p> Applications may sub-class {@link AbstractQuantity} for particular measurements
 *     types.<br/><code>
 *         // Quantity of type Mass based on <code>double</code> primitive types.
 *         public class MassAmount extends AbstractQuantity<Mass> { 
 *             private final double _kilograms; // Internal SI representation. 
 *             private Mass(double kilograms) { _kilograms = kilograms; }
 *             public static Mass of(double value, Unit<Mass> unit) {
 *                 return new Mass(unit.getConverterTo(SI.KILOGRAM).convert(value));
 *             } 
 *             public Unit<Mass> getUnit() { return SI.KILOGRAM; } 
 *             public Double getValue() { return _kilograms; } 
 *             ...
 *         }
 * 
 *         // Complex numbers measurements.
 *         public class ComplexQuantity<Q extends Quantity> extends AbstractQuantity<Q> {
 *             public Complex getValue() { ... } // Assuming Complex is a Number.
 *             ... 
 *         }
 * 
 *         // Specializations of complex numbers measurements.
 *         public class Current extends ComplexQuantity<ElectricCurrent> {...} 
 *         public class Tension extends ComplexQuantity<ElectricPotential> {...}
 *         </code></p>
 * 
 * <p> All instances of this class shall be immutable.</p>
 * 
 * @author  <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 1.3, $Date: 2014-04-23 $
 */
public abstract class AbstractQuantity<Q extends Quantity<Q>> implements Quantity<Q> {
	
    /**
	 * 
	 */
//	private static final long serialVersionUID = -4993173119977931016L;
    
	private final Unit<Q> unit;
	
	/**
	 * Holds a dimensionless measure of none (exact).
	 */
	public static final AbstractQuantity<Dimensionless> NONE = of(0, SI.ONE);
	
	/**
	 * Holds a dimensionless measure of one (exact).
	 */
	public static final AbstractQuantity<Dimensionless> ONE = of(1, SI.ONE);
	
	/**
     * constructor.
     */
    protected AbstractQuantity(Unit<Q> unit) {
    	this.unit = unit;
    }

    /**
     * Returns the measurement numeric value.
     *
     * @return the measurement value.
     */
    public abstract Number getValue();

    /**
     * Returns the measurement unit.
     *
     * @return the measurement unit.
     */
    public Unit<Q> getUnit() {
    	return unit;
    }

    /**
     * Convenient method equivalent to {@link #to(javax.measure.unit.Unit)
     * to(this.getUnit().toSI())}.
     *
     * @return this measure or a new measure equivalent to this measure but
     *         stated in SI units.
     * @throws ArithmeticException if the result is inexact and the quotient
     *         has a non-terminating decimal expansion.
     */
    public AbstractQuantity<Q> toSI() {
        return to(this.getUnit().getSystemUnit());
    }

    /**
     * Returns this measure after conversion to specified unit. The default
     * implementation returns
     * <code>Measure.valueOf(doubleValue(unit), unit)</code>. If this measure is
     * already stated in the specified unit, then this measure is returned and
     * no conversion is performed.
     *
     * @param unit the unit in which the returned measure is stated.
     * @return this measure or a new measure equivalent to this measure but
     *         stated in the specified unit.
     * @throws ArithmeticException if the result is inexact and the quotient has
     *         a non-terminating decimal expansion.
     */
    public AbstractQuantity<Q> to(Unit<Q> unit) {
        if (unit.equals(this.getUnit())) {
            return this;
        }
        //return AbstractMeasurement.of(doubleValue(unit), unit);
        return AbstractQuantity.of(decimalValue(unit, MathContext.UNLIMITED), unit);
    }

    /**
     * Returns this measure after conversion to specified unit. The default
     * implementation returns
     * <code>Measure.valueOf(decimalValue(unit, ctx), unit)</code>. If this
     * measure is already stated in the specified unit, then this measure is
     * returned and no conversion is performed.
     *
     * @param unit the unit in which the returned measure is stated.
     * @param ctx the math context to use for conversion.
     * @return this measure or a new measure equivalent to this measure but
     *         stated in the specified unit.
     * @throws ArithmeticException if the result is inexact but the rounding
     *         mode is <code>UNNECESSARY</code> or
     *         <code>mathContext.precision == 0</code> and the quotient has
     *         a non-terminating decimal expansion.
     */
    public AbstractQuantity<Q> to(Unit<Q> unit, MathContext ctx) {
        if (unit.equals(this.getUnit())) {
            return this;
        }
        return AbstractQuantity.of(decimalValue(unit, ctx), unit);
    }

    /**
     * Compares this measure to the specified Measurement quantity. The default
     * implementation compares the {@link Measurement#doubleValue(Unit)} of both
     * this measure and the specified Measurement stated in the same unit (this
     * measure's {@link #getUnit() unit}).
     *
     * @return a negative integer, zero, or a positive integer as this measure
     *         is less than, equal to, or greater than the specified Measurement
     *         quantity.
     * @return <code>Double.compare(this.doubleValue(getUnit()),
     *         that.doubleValue(getUnit()))</code>
     */
    public int compareTo(Measurement<Q, Number> that) {
        Unit<Q> unit = getUnit();
        return Double.compare(doubleValue(unit), that.getValue().doubleValue());
    }

    /**
     * Compares this measure against the specified object for <b>strict</b>
     * equality (same unit and same amount).
     *
     * <p> Similarly to the {@link BigDecimal#equals} method which consider 2.0
     *     and 2.00 as different objects because of different internal scales,
     *     measurements such as <code>Measure.valueOf(3.0, KILOGRAM)</code>
     *     <code>Measure.valueOf(3, KILOGRAM)</code> and
     *     <code>Measure.valueOf("3 kg")</code> might not be considered equals
     *     because of possible differences in their implementations.</p>
     *
     * <p> To compare measures stated using different units or using different
     *     amount implementations the {@link #compareTo compareTo} or
     *     {@link #equals(javax.measure.Measurement, double, javax.measure.unit.Unit)
     *      equals(Measurement, epsilon, epsilonUnit)} methods should be used.</p>
     *
     * @param obj the object to compare with.
     * @return <code>this.getUnit.equals(obj.getUnit())
     *         && this.getValue().equals(obj.getValue())</code>
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractQuantity<?>)) {
            return false;
        }
        AbstractQuantity<?> that = (AbstractQuantity<?>) obj;
        return this.getUnit().equals(that.getUnit()) && this.getValue().equals(that.getValue());
    }

    /**
     * Compares this measure and the specified Measurement to the given accuracy.
     * Measurements are considered approximately equals if their absolute
     * differences when stated in the same specified unit is less than the
     * specified epsilon.
     *
     * @param that the Measurement to compare with.
     * @param epsilon the absolute error stated in epsilonUnit.
     * @param epsilonUnit the epsilon unit.
     * @return <code>abs(this.doubleValue(epsilonUnit) - that.doubleValue(epsilonUnit)) &lt;= epsilon</code>
     */
    public boolean equals(AbstractQuantity<Q> that, double epsilon, Unit<Q> epsilonUnit) {
        return Math.abs(this.doubleValue(epsilonUnit) - that.doubleValue(epsilonUnit)) <= epsilon;
    }

    /**
     * Returns the hash code for this measure.
     *
     * @return the hash code value.
     */
    @Override
    public int hashCode() {
        return getUnit().hashCode() + getValue().hashCode();
    }

    public abstract boolean isBig();
    
    /**
     * Returns the <code>String</code> representation of this measure. The
     * string produced for a given measure is always the same; it is not
     * affected by locale. This means that it can be used as a canonical string
     * representation for exchanging measure, or as a key for a Hashtable, etc.
     * Locale-sensitive measure formatting and parsing is handled by the
     * {@link MeasurementFormat} class and its subclasses.
     *
     * @return <code>UnitFormat.getInternational().format(this)</code>
     */
    @Override
    public String toString() {
        //return MeasureFormat.getStandard().format(this); TODO improve MeasureFormat
    	return String.valueOf(getValue()) + " " + String.valueOf(getUnit());
    }

    public abstract BigDecimal decimalValue(Unit<Q> unit, MathContext ctx)
            throws ArithmeticException;
    
    public abstract  double doubleValue(Unit<Q> unit)
            throws ArithmeticException;
    
    // Implements AbstractMeasurement
    public final int intValue(Unit<Q> unit) throws ArithmeticException {
        long longValue = longValue(unit);
        if ((longValue < Integer.MIN_VALUE) || (longValue > Integer.MAX_VALUE)) {
            throw new ArithmeticException("Cannot convert " + longValue + " to int (overflow)");
        }
        return (int) longValue;
    }

    protected long longValue(Unit<Q> unit) throws ArithmeticException {
        double result = doubleValue(unit);
        if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
            throw new ArithmeticException("Overflow (" + result + ")");
        }
        return (long) result;
    }

    protected final float floatValue(Unit<Q> unit) {
        return (float) doubleValue(unit);
    }

    /**
     * Casts this measure to a parameterized unit of specified nature or throw a
     * <code>ClassCastException</code> if the dimension of the specified
     * quantity and this measure unit's dimension do not match. For
     * example:<br/><code>
     *     Measure<Length> length = Measure.valueOf("2 km").asType(Length.class);
     * </code>
     *
     * @param type the quantity class identifying the nature of the measure.
     * @return this measure parameterized with the specified type.
     * @throws ClassCastException if the dimension of this unit is different
     *         from the specified quantity dimension.
     * @throws UnsupportedOperationException
     *             if the specified quantity class does not have a public static
     *             field named "UNIT" holding the SI unit for the quantity.
     * @see Unit#asType(Class)
     */
    @SuppressWarnings("unchecked")
    public final <T extends Quantity<T>> AbstractQuantity<T> asType(Class<T> type)
            throws ClassCastException {
        this.getUnit().asType(type); // Raises ClassCastException is dimension
        // mismatches.
        return (AbstractQuantity<T>) this;
    }

    /**
     * Returns the
     * {@link #valueOf(java.math.BigDecimal, javax.measure.unit.Unit) decimal}
     * measure of unknown type corresponding to the specified representation.
     * This method can be used to parse dimensionless quantities.<br/><code>
     *     Measurement<Number, Dimensionless> proportion = Measure.valueOf("0.234").asType(Dimensionless.class);
     * </code>
     *
     * <p> Note: This method handles only
     * {@link javax.measure.unit.UnitFormat#getStandard standard} unit format
     * (<a href="http://unitsofmeasure.org/">UCUM</a> based). Locale-sensitive
     * measure formatting and parsing are handled by the {@link MeasurementFormat}
     * class and its subclasses.</p>
     *
     * @param csq the decimal value and its unit (if any) separated by space(s).
     * @return <code>MeasureFormat.getStandard().parse(csq, new ParsePosition(0))</code>
     */
    public static AbstractQuantity<?> of(CharSequence csq) {
        try {
			return QuantityFormat.getInstance(LOCALE_NEUTRAL).parse(csq, new ParsePosition(0));
		} catch (IllegalArgumentException | ParserException e) {
			throw new IllegalArgumentException(e); // TODO could we handle this differently?
		}
    }

    /**
     * Returns the scalar measure for the specified <code>int</code> stated in
     * the specified unit.
     *
     * @param intValue the measurement value.
     * @param unit the measurement unit.
     * @return the corresponding <code>int</code> measure.
     */
    public static <Q extends Quantity<Q>> AbstractQuantity<Q> of(int intValue,
            Unit<Q> unit) {
        return new IntegerQuantity<Q>(intValue, unit);
    }

    private static final class IntegerQuantity<T extends Quantity<T>> extends AbstractQuantity<T> {

        /**
		 * 
		 */
//		private static final long serialVersionUID = 5355395476874521709L;
		
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
            return (super.unit.equals(unit)) ? value : super.unit.getConverterTo(unit).convert(value);
        }

        // Implements Measurement
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

		@Override
		public Measurement<?, Number> multiply(Measurement<?, Number> that) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Measurement<?, Number> multiply(Number that) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Measurement<?, Number> divide(Measurement<?, Number> that) {
			return of((double)value / that.getValue().doubleValue(), getUnit().divide(that.getUnit()));
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
		public Measurement<?, Number> divide(Number that) {
			// TODO Auto-generated method stub
			return null;
		}

    }
    
    /**
     * Returns the scalar measure for the specified <code>float</code> stated in
     * the specified unit.
     *
     * @param floatValue the measurement value.
     * @param unit the measurement unit.
     * @return the corresponding <code>float</code> measure.
     */
    public static <Q extends Quantity<Q>> AbstractQuantity<Q> of(float floatValue,
            Unit<Q> unit) {
        return new FloatQuantity<Q>(floatValue, unit);
    }

    private static final class FloatQuantity<T extends Quantity<T>> extends AbstractQuantity<T> {

        /**
		 * 
		 */
//		private static final long serialVersionUID = 7857472738562215118L;
		
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
            return (super.unit.equals(unit)) ? value : super.unit.getConverterTo(unit).convert(value);
        }

        // Implements AbstractMeasurement
        public BigDecimal decimalValue(Unit<T> unit, MathContext ctx)
                throws ArithmeticException {
            BigDecimal decimal = BigDecimal.valueOf(value); // TODO check value if it is a BD, otherwise use different converter
            return (super.unit.equals(unit)) ? decimal : ((AbstractConverter)super.unit.getConverterTo(unit)).convert(decimal, ctx);
        }

		public long longValue(Unit<T> unit) {
	        double result = doubleValue(unit);
	        if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
	            throw new ArithmeticException("Overflow (" + result + ")");
	        }
	        return (long) result;
		}

		@Override
		public AbstractQuantity<T> add(Measurement<T, Number> that) {
			return of(value + that.getValue().floatValue(), getUnit()); // TODO use shift of the unit?
		}

		@Override
		public AbstractQuantity<T> substract(Measurement<T, Number> that) {
			return of(value - that.getValue().floatValue(), getUnit()); // TODO use shift of the unit?
		}

		@SuppressWarnings("unchecked")
		@Override
		public AbstractQuantity<T> multiply(Measurement<?, Number> that) {
			return (AbstractQuantity<T>) of(value * that.getValue().floatValue(), 
					getUnit().multiply(that.getUnit()));
		}

		@Override
		public Measurement<?, Number> multiply(Number that) {
			return of(value * that.floatValue(), 
					getUnit().multiply(that.doubleValue()));
		}

		@Override
		public Quantity<?> divide(Measurement<?, Number> that) {
			return of(value / that.getValue().floatValue(), getUnit().divide(that.getUnit()));
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
		public Measurement<?, Number> divide(Number that) {
			return of(value / that.floatValue(), getUnit());
		}
    }

    /**
     * Returns the scalar measure for the specified <code>double</code> stated
     * in the specified unit.
     *
     * @param doubleValue the measurement value.
     * @param unit the measurement unit.
     * @return the corresponding <code>double</code> measure.
     */
    public static <Q extends Quantity<Q>> AbstractQuantity<Q> of(double doubleValue,
            Unit<Q> unit) {
        return new DoubleQuantity<Q>(doubleValue, unit);
    }

    private static final class DoubleQuantity<T extends Quantity<T>> extends AbstractQuantity<T> {

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

		@Override
		public Measurement<T, Number> add(Measurement<T, Number> that) {
			return of(value + that.getValue().doubleValue(), getUnit()); // TODO use shift of the unit?
		}

		@Override
		public Measurement<T, Number> substract(Measurement<T, Number> that) {
			return of(value - that.getValue().doubleValue(), getUnit()); // TODO use shift of the unit?
		}

		@Override
		public Measurement<?, Number> multiply(Measurement<?, Number> that) {
			return of(value * that.getValue().doubleValue(), getUnit().multiply(that.getUnit()));
		}

		@Override
		public Measurement<?, Number> multiply(Number that) {
			return of(value * that.doubleValue(), getUnit());
		}

		@Override
		public Measurement<?, Number> divide(Measurement<?, Number> that) {
			return of(value / that.getValue().doubleValue(), getUnit().divide(that.getUnit()));
		}
		
		@Override
		public Measurement<?, Number> divide(Number that) {
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

    /**
     * Returns the scalar measure for the specified <code>BigDecimal</code>
     * stated in the specified unit.
     *
     * @param decimalValue the measurement value.
     * @param unit the measurement unit.
     * @return the corresponding <code>BigDecimal</code> measure.
     */
    public static <Q extends Quantity<Q>> AbstractQuantity<Q> of(
            BigDecimal decimalValue, Unit<Q> unit) {
        return new DecimalQuantity<Q>(decimalValue, unit);
    }
    
    private static final class DecimalQuantity<T extends Quantity<T>> extends AbstractQuantity<T> implements Serializable {

        /**
		 * 
		 */
		private static final long serialVersionUID = 6504081836032983882L;
		
		final BigDecimal value;

        public DecimalQuantity(BigDecimal value, Unit<T> unit) {
        	super(unit);
        	this.value = value;
        }

        @Override
        public BigDecimal getValue() {
            return value;
        }

        // Implements AbstractMeasurement
        public double doubleValue(Unit<T> unit) {
            return (unit.equals(unit)) ? value.doubleValue() : unit.getConverterTo(unit).convert(value.doubleValue());
        }

        // Implements AbstractMeasurement
        public BigDecimal decimalValue(Unit<T> unit, MathContext ctx)
                throws ArithmeticException {
            return (super.unit.equals(unit)) ? value :
            	((AbstractConverter)unit.getConverterTo(unit)).convert(value, ctx);
        }

		@Override
		public Measurement<T, Number> add(Measurement<T, Number> that) {
			return of(value.add((BigDecimal)that.getValue()), getUnit()); // TODO use shift of the unit?
		}

		@Override
		public Measurement<T, Number> substract(Measurement<T, Number> that) {
			return of(value.subtract((BigDecimal)that.getValue()), getUnit()); // TODO use shift of the unit?
		}

		@Override
		public AbstractQuantity<?> multiply(Measurement<?, Number> that) {
			return of(value.multiply((BigDecimal)that.getValue()), 
					getUnit().multiply(that.getUnit()));
		}

		@Override
		public Measurement<?, Number> multiply(Number that) {
			return of(value.multiply((BigDecimal)that), getUnit());
		}

		@Override
		public Measurement<?, Number> divide(Measurement<?, Number> that) {
			return of(value.divide((BigDecimal)that.getValue()), getUnit());
		}

		@Override
		public Measurement<?, Number> divide(Number that) {
			return of(value.divide((BigDecimal)that), getUnit());
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public AbstractQuantity<T> inverse() {
			//return of(value.negate(), getUnit());
			return (AbstractQuantity<T>) of(value, getUnit().inverse());
		}

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
    }   

}
