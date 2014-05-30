/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright 2010-2014, Jean-Marie Dautelle, Werner Keil, V2COM and individual
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

import org.unitsofmeasurement.impl.format.LocalUnitFormat;
import org.unitsofmeasurement.impl.format.UCUMFormat;
import org.unitsofmeasurement.impl.function.AbstractConverter;
import org.unitsofmeasurement.impl.function.AddConverter;
import org.unitsofmeasurement.impl.function.MultiplyConverter;
import org.unitsofmeasurement.impl.function.RationalConverter;
import org.unitsofmeasurement.impl.model.DimensionalModel;
import org.unitsofmeasurement.impl.model.QuantityDimension;
import org.unitsofmeasurement.impl.util.SI;

import javax.measure.*;
import javax.measure.function.UnitConverter;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Map;

import static org.unitsofmeasurement.impl.format.UCUMFormat.Variant.CASE_INSENSITIVE;


/**
 * <p> The class represents units founded on the seven
 *     {@link org.org.unitsofmeasurement.impl.system.SI SI} base units for
 *     seven base quantities assumed to be mutually independent.</p>
 *
 * <p> For all physics units, units conversions are symmetrical:
 *     <code>u1.getConverterTo(u2).equals(u2.getConverterTo(u1).inverse())</code>.
 *     Non-physical units (e.g. currency units) for which conversion is
 *     not symmetrical should have their own separate class hierarchy and
 *     are considered distinct (e.g. financial units), although
 *     they can always be combined with physics units (e.g. "€/Kg", "$/h").</p>
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.5, May 31, 2014
 */
public abstract class AbstractUnit<Q extends Quantity<Q>> implements Unit<Q>, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4344589505537030204L;

	/**
	 * Holds the name.
	 */
	protected String name;
	
	/**
     * Default constructor.
     */
    protected AbstractUnit() {
    }

   /**
     * Indicates if this unit belongs to the set of coherent SI units 
     * (unscaled SI units).
     * 
     * The base and coherent derived units of the SI form a coherent set, 
     * designated the set of coherent SI units. The word coherent is used here 
     * in the following sense: when coherent units are used, equations between 
     * the numerical values of quantities take exactly the same form as the 
     * equations between the quantities themselves. Thus if only units from 
     * a coherent set are used, conversion factors between units are never 
     * required. 
     * 
     * @return <code>equals(toSI())</code>
     */
    public boolean isSI() {
        AbstractUnit<Q> si = this.toSI();
        return (this == si) || this.equals(si);
    }

    /**
     * Returns the unscaled {@link SI} unit  from which this unit is derived.
     * 
     * They SI unit can be be used to identify a quantity given the unit.
     * For example:[code]
     *    static boolean isAngularVelocity(AbstractUnit<?> unit) {
     *        return unit.toSI().equals(RADIAN.divide(SECOND));
     *    }
     *    assert(REVOLUTION.divide(MINUTE).isAngularVelocity()); // Returns true.
     * [/code]
     *
     * @return the unscaled metric unit from which this unit is derived.
     */
    public abstract AbstractUnit<Q> toSI();

    /**
     * Returns the converter from this unit to its unscaled {@link #toSI SI} 
     * unit.
     *
     * @return <code>getConverterTo(this.toSI())</code>
     * @see #toSI
     */
    public abstract UnitConverter getConverterToSI();

   /**
     * Annotates the specified unit. Annotation does not change the unit
     * semantic. Annotations are often written between curly braces behind units.
     * For example:
     * [code]
     *     AbstractUnit<Volume> PERCENT_VOL = SI.PERCENT.annotate("vol"); // "%{vol}"
     *     AbstractUnit<Mass> KG_TOTAL = SI.KILOGRAM.annotate("total"); // "kg{total}"
     *     AbstractUnit<Dimensionless> RED_BLOOD_CELLS = SI.ONE.annotate("RBC"); // "{RBC}"
     * [/code]
     *
     * Note: Annotation of system units are not considered themselves as system units.
     *
     * @param annotation the unit annotation.
     * @return the annotated unit.
     */
    public AnnotatedUnit<Q> annotate(String annotation) {
        return new AnnotatedUnit<>(this, annotation);
    }
    
    /**
     * Returns the physics unit represented by the specified characters
     * as per standard <a href="http://www.unitsofmeasure.org/">UCUM</a> format.
     *
     * Locale-sensitive unit parsing should be handled using the OSGi
     * {@link org.unitsofmeasurement.service.UnitFormatService} or
     * for non-OSGi applications the
     * {@link LocalUnitFormat} utility class.
     *
     * <p>Note: The standard UCUM format supports dimensionless units.[code]
     *       AbstractUnit<Dimensionless> PERCENT = AbstractUnit.valueOf("100").inverse().asType(Dimensionless.class);
     * [/code]</p>
     *
     * @param charSequence the character sequence to parse.
     * @return <code>UCUMFormat.getCaseSensitiveInstance().parse(csq, new ParsePosition(0))</code>
     * @throws ParserException if the specified character sequence
     *         cannot be correctly parsed (e.g. not UCUM compliant).
     */
    public static AbstractUnit<?> of(CharSequence charSequence) {
        return UCUMFormat.getInstance(CASE_INSENSITIVE).parse(charSequence);
    }

    /**
     * Returns the standard <a href="http://unitsofmeasure.org/">UCUM</a>
     * representation of this physics unit. The string produced for a given unit is
     * always the same; it is not affected by the locale. It can be used as a
     * canonical string representation for exchanging units, or as a key for a
     * Hashtable, etc.
     *
     * Locale-sensitive unit parsing should be handled using the OSGi
     * {@link org.unitsofmeasurement.service.UnitFormat} service (or
     * the {@link LocalUnitFormat} class
     * for non-OSGi applications).
     *
     * @return <code>UCUMFormat.getCaseSensitiveInstance().format(this)</code>
     */
    @Override
    public String toString() {
        final Appendable tmp = new StringBuilder();
        try {
            //return UCUMFormat.getCaseSensitiveInstance().format(this, tmp).toString();
        	return LocalUnitFormat.getInstance().format(this, tmp).toString();
        } catch (IOException ioException) {
             throw new Error(ioException); // Should never happen.
        } finally {
            //if (tmp!=null) tmp.clear();
        }
    }


   /////////////////////////////////////////////////////////
    // Implements org.unitsofmeasurement.Unit<Q> interface //
    /////////////////////////////////////////////////////////

    /**
     * Returns the system unit (unscaled SI unit) from which this unit is derived.
     * They can be be used to identify a quantity given the unit. For example:[code]
     *    static boolean isAngularVelocity(AbstractUnit<?> unit) {
     *        return unit.getSystemUnit().equals(RADIAN.divide(SECOND));
     *    }
     *    assert(REVOLUTION.divide(MINUTE).isAngularVelocity()); // Returns true.
     * [/code]
     *
     * @return the unscaled metric unit from which this unit is derived.
     */
    @Override
    public final AbstractUnit<Q> getSystemUnit() {
        return toSI();
    }

    /**
     * Indicates if this unit is compatible with the unit specified.
     * To be compatible both units must be physics units having
     * the same fundamental dimension.
     *
     * @param that the other unit.
     * @return <code>true</code> if this unit and that unit have equals
     *         fundamental dimension according to the current physics model;
     *         <code>false</code> otherwise.
     */
    @Override
    public final boolean isCompatible(Unit<?> that) {
        if ((this == that) || this.equals(that)) return true;
        if (!(that instanceof AbstractUnit)) return false;
        Dimension thisDimension = this.getDimension();
        Dimension thatDimension = that.getDimension();
        if (thisDimension.equals(thatDimension)) return true;
        DimensionalModel model = DimensionalModel.getCurrent(); // Use dimensional analysis model.
        return model.getFundamentalDimension(thisDimension).equals(model.getFundamentalDimension(thatDimension));
    }

    /**
     * Casts this unit to a parameterized unit of specified nature or throw a
     * ClassCastException if the dimension of the specified quantity and
     * this unit's dimension do not match (regardless whether or not
     * the dimensions are independent or not).
     *
     * @param type the quantity class identifying the nature of the unit.
     * @throws ClassCastException if the dimension of this unit is different
     *         from the {@link SI} dimension of the specified type.
     * @see    SI#getUnit(Class)
     */
    @SuppressWarnings("unchecked")
	@Override
    public final <T extends Quantity<T>> AbstractUnit<T> asType(Class<T> type) {
        Dimension typeDimension = QuantityDimension.getInstance(type);
        if ((typeDimension != null) && (!this.getDimension().equals(typeDimension)))
           throw new ClassCastException("The unit: " + this + " is not compatible with quantities of type " + type);
        return (AbstractUnit<T>) this;
    }

    @Override
    public abstract Map<? extends AbstractUnit<?>, Integer> getProductUnits();

    @Override
    public abstract Dimension getDimension();

	protected void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
    @Override
    public final UnitConverter getConverterTo(Unit<Q> that) throws UnconvertibleException {
        if ((this == that) || this.equals(that)) return AbstractConverter.IDENTITY; // Shortcut.
        Unit<Q> thisSystemUnit = this.getSystemUnit();
        Unit<Q> thatSystemUnit = that.getSystemUnit();
        if (!thisSystemUnit.equals(thatSystemUnit))
			try {
				return getConverterToAny(that);
			} catch (IncommensurableException e) {
				throw new UnconvertibleException(e);
			} 
        UnitConverter thisToSI= this.getConverterToSI();
        UnitConverter thatToSI= that.getConverterTo(thatSystemUnit);
        return thatToSI.inverse().concatenate(thisToSI);    
    }

    @Override
    public final UnitConverter getConverterToAny(Unit<?> that) throws IncommensurableException,
            UnconvertibleException {
        if (!isCompatible(that))
            throw new IncommensurableException(this + " is not compatible with " + that);
        AbstractUnit thatAbstr = (AbstractUnit)that; // Since both units are compatible they must be both physics units.
        DimensionalModel model = DimensionalModel.getCurrent();
        AbstractUnit thisSystemUnit = this.getSystemUnit();
        UnitConverter thisToDimension = model.getDimensionalTransform(thisSystemUnit.getDimension()).concatenate(this.getConverterToSI());
        AbstractUnit thatSystemUnit = thatAbstr.getSystemUnit();
        UnitConverter thatToDimension = model.getDimensionalTransform(thatSystemUnit.getDimension()).concatenate(thatAbstr.getConverterToSI());
        return thatToDimension.inverse().concatenate(thisToDimension);
    }


    @Override
    public final Unit<Q> alternate(String symbol) {
        return new AlternateUnit(this, symbol);
    }

    @Override
    public final AbstractUnit<Q> transform(UnitConverter operation) {
        AbstractUnit<Q> systemUnit = this.getSystemUnit();
        UnitConverter cvtr = this.getConverterToSI().concatenate(operation);
        if (cvtr.equals(AbstractConverter.IDENTITY))
            return systemUnit;
        return new TransformedUnit<>(systemUnit, cvtr);
    }

    @Override
    public final AbstractUnit<Q> shift(double offset) {
        if (offset == 0)
            return this;
        return transform(new AddConverter(offset));
    }

    @Override
    public final AbstractUnit<Q> multiply(double factor) {
        if (factor == 1)
            return this;
        if (isLongValue(factor))
            return transform(new RationalConverter(BigInteger.valueOf((long)factor), BigInteger.ONE));
        return transform(new MultiplyConverter(factor));
    }
    private static boolean isLongValue(double value) {
        if ((value < Long.MIN_VALUE) || (value > Long.MAX_VALUE)) return false;
        return Math.floor(value) == value;
    }

    /**
     * Returns the product of this unit with the one specified.
     *
     * <p> Note: If the specified unit (that) is not a physical unit, then
     * <code>that.multiply(this)</code> is returned.</p>
     *
     * @param that the unit multiplicand.
     * @return <code>this * that</code>
     */
    @Override
    public final Unit<?> multiply(Unit<?> that) {
        if (that instanceof AbstractUnit)
            return multiply((AbstractUnit<?>) that);
        return that.multiply(this); // Commutatif.
    }

    /**
     * Returns the product of this physical unit with the one specified.
     *
     * @param that the physical unit multiplicand.
     * @return <code>this * that</code>
     */
    public final AbstractUnit<?> multiply(AbstractUnit<?> that) {
        if (this.equals(SI.ONE))
            return that;
        if (that.equals(SI.ONE))
            return this;
        return ProductUnit.getProductInstance(this, that);
    }

    /**
     * Returns the inverse of this physical unit.
     *
     * @return <code>1 / this</code>
     */
    @Override
    public final AbstractUnit<?> inverse() {
        if (this.equals(SI.ONE))
            return this;
        return ProductUnit.getQuotientInstance(SI.ONE, this);
    }

    /**
     * Returns the result of dividing this unit by the specifified divisor.
     * If the factor is an integer value, the division is exact.
     * For example:<pre><code>
     *    QUART = GALLON_LIQUID_US.divide(4); // Exact definition.
     * </code></pre>
     * @param divisor the divisor value.
     * @return this unit divided by the specified divisor.
     */
    @Override
    public final AbstractUnit<Q> divide(double divisor) {
        if (divisor == 1)
            return this;
        if (isLongValue(divisor))
            return transform(new RationalConverter(BigInteger.ONE, BigInteger.valueOf((long)divisor)));
        return transform(new MultiplyConverter(1.0/divisor));
    }

    /**
     * Returns the quotient of this unit with the one specified.
     *
     * @param that the unit divisor.
     * @return <code>this.multiply(that.inverse())</code>
     */
    @Override
    public final Unit<?> divide(Unit<?> that) {
        return this.multiply(that.inverse());
    }

    /**
     * Returns the quotient of this physical unit with the one specified.
     *
     * @param that the physical unit divisor.
     * @return <code>this.multiply(that.inverse())</code>
     */
    public final AbstractUnit<?> divide(AbstractUnit<?> that) {
        return this.multiply(that.inverse());
    }

    /**
     * Returns a unit equals to the given root of this unit.
     *
     * @param n the root's order.
     * @return the result of taking the given root of this unit.
     * @throws ArithmeticException if <code>n == 0</code> or if this operation
     *         would result in an unit with a fractional exponent.
     */
    @Override
    public final AbstractUnit<?> root(int n) {
        if (n > 0)
            return ProductUnit.getRootInstance(this, n);
        else if (n == 0)
            throw new ArithmeticException("Root's order of zero");
        else // n < 0
            return SI.ONE.divide(this.root(-n));
    }

    /**
     * Returns a unit equals to this unit raised to an exponent.
     *
     * @param n the exponent.
     * @return the result of raising this unit to the exponent.
     */
    @Override
    public final AbstractUnit<?> pow(int n) {
        if (n > 0)
            return this.multiply(this.pow(n - 1));
        else if (n == 0)
            return SI.ONE;
        else // n < 0
            return SI.ONE.divide(this.pow(-n));
    }


    ////////////////////////////////////////////////////////////////
    // Ensures that sub-classes implements hashCode/equals method.
    ////////////////////////////////////////////////////////////////

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object that);

}