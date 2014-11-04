/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright (c) 2005-2014, Jean-Marie Dautelle, Werner Keil, V2COM.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 *    and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of JSR-363 nor the names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package tec.uom.se;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.UnitConverter;
import javax.measure.quantity.Dimensionless;

import tec.uom.se.quantity.Quantities;

/**
 * <p> This class represents the immutable result of a scalar measurement stated
 *     in a known unit.</p>
 *
 * <p> To avoid any lost of precision, known exact measure (e.g. physical
 *     constants) should not be created from <code>double</code> constants but
 *     from their decimal representation.<br/><code>
 *         public static final Quantity<Velocity> C = AbstractQuantity.of("299792458 m/s").asType(Velocity.class);
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
 * @author  <a href="mailto:werner@uom.technology">Werner Keil</a>
 * @version 0.6.3, $Date: 2014-10-21 $
 */
public abstract class AbstractQuantity<Q extends Quantity<Q>> implements
        ComparableQuantity<Q> {

	private final Unit<Q> unit;

	/**
	 * Holds a dimensionless measure of none (exact).
	 */
	public static final Quantity<Dimensionless> NONE = Quantities.getQuantity(
			0, AbstractUnit.ONE);

	/**
	 * Holds a dimensionless measure of one (exact).
	 */
	public static final Quantity<Dimensionless> ONE = Quantities.getQuantity(1, AbstractUnit.ONE);

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
    @Override
    public abstract Number getValue();

    /**
     * Returns the measurement unit.
     *
     * @return the measurement unit.
     */
    @Override
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
    public Quantity<Q> toSI() {
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
    @Override
    public Quantity<Q> to(Unit<Q> unit) {
        if (unit.equals(this.getUnit())) {
            return this;
        }
        UnitConverter t = getUnit().getConverterTo(unit);
        Number convertedValue = t.convert(getValue());
        return Quantities.getQuantity(convertedValue, unit);
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
    public Quantity<Q> to(Unit<Q> unit, MathContext ctx) {
        if (unit.equals(this.getUnit())) {
            return this;
        }
        return Quantities.getQuantity(decimalValue(unit, ctx), unit);
    }


    @Override
    public boolean isGreaterThan(Quantity<Q> that) {
        return this.compareTo(that) > 0;
    }

    @Override
    public boolean isGreaterThanOrEqualTo(Quantity<Q> that) {
        return this.compareTo(that) >= 0;
    }

    @Override
    public boolean isLessThan(Quantity<Q> that) {
        return this.compareTo(that) < 0;
    }

    @Override
    public boolean isLessThanOrEqualTo(Quantity<Q> that) {
        return this.compareTo(that) <= 0;
    }

    @Override
    public boolean isEquivalentTo(Quantity<Q> that) {
        return this.compareTo(that) == 0;
    }

    /**
     * Compares this quantity to the specified Measurement quantity. The default
     * implementation compares the {@link AbstractQuantity#doubleValue(Unit)} of both
     * this quantity and the specified Quantity stated in the same unit (this
     * quantity's {@link #getUnit() unit}).
     *
     * @return a negative integer, zero, or a positive integer as this quantity
     *         is less than, equal to, or greater than the specified quantity.
     */
    public int compareTo(Quantity<Q> that) {
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
    	if (this == obj) {
    		return true;
    	}
        if (obj instanceof AbstractQuantity<?>) {
        	AbstractQuantity<?> that = (AbstractQuantity<?>) obj;
			return Objects.equals(getUnit(), that.getUnit())
					&& Objects.equals(getValue(), that.getValue());
        }
        return false;
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
        return Objects.hash(getUnit(), getValue());
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
    	return String.valueOf(getValue()) + " " + String.valueOf(getUnit());
    }

    public abstract BigDecimal decimalValue(Unit<Q> unit, MathContext ctx)
            throws ArithmeticException;

    public abstract  double doubleValue(Unit<Q> unit)
            throws ArithmeticException;

    public final int intValue(Unit<Q> unit) throws ArithmeticException {
        long longValue = longValue(unit);
        if ((longValue < Integer.MIN_VALUE) || (longValue > Integer.MAX_VALUE)) {
            throw new ArithmeticException("Cannot convert " + longValue
                    + " to int (overflow)");
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
     * Casts this quantity to a parameterized quantity of specified nature or throw a
     * <code>ClassCastException</code> if the dimension of the specified
     * quantity and its unit's dimension do not match. For
     * example:<br/><code>
     *     Quantity<Length> length = ComparableQuantity.of("2 km").asType(Length.class);
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
        this.getUnit().asType(type); // Raises ClassCastException if dimension mismatches.
        return (AbstractQuantity<T>) this;
    }
}
