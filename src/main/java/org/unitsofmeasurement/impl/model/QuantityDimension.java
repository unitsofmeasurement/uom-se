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
package org.unitsofmeasurement.impl.model;

import org.unitsofmeasurement.impl.AbstractUnit;
import org.unitsofmeasurement.impl.BaseUnit;
import org.unitsofmeasurement.impl.util.SI;

import javax.measure.Dimension;
import javax.measure.Quantity;
import javax.measure.Unit;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
*  <p> This class represents a quantity dimension (dimension of a physical
 *     quantity).</p>
 *
 * <p> The dimension associated to any given quantity are given by the
 *     OSGi published {@link PhysicsDimensionService} instances.
 *     For convenience, a static method {@link QuantityDimension#getInstance(Class)
 *     aggregating the results of all {@link DimensionService} instances
 *     is provided.<br/><br/>
 *     <code>
 *        QuantityDimension velocityDimension
 *            = QuantityDimension.of(Velocity.class);
 *     </code>
 * </p>
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 5.4, $Date: 2014-05-09 $
 */
public class QuantityDimension implements Dimension {
	private static final Logger logger = Logger.getLogger(Dimension.class.getName());
	
    /**
	 * 
	 */
//	private static final long serialVersionUID = 123289037718650030L;

	/**
     * Holds dimensionless.
     */
    public static final Dimension NONE = new QuantityDimension(SI.ONE);

    /**
     * Holds length dimension (L).
     */
    public static final Dimension LENGTH = new QuantityDimension('L');

    /**
     * Holds mass dimension (M).
     */
    public static final Dimension MASS = new QuantityDimension('M');

    /**
     * Holds time dimension (T).
     */
    public static final Dimension TIME = new QuantityDimension('T');

    /**
     * Holds electric current dimension (I).
     */
    public static final Dimension ELECTRIC_CURRENT = new QuantityDimension('I');

    /**
     * Holds temperature dimension (Θ).
     */
    public static final Dimension TEMPERATURE = new QuantityDimension('Θ');

    /**
     * Holds amount of substance dimension (N).
     */
    public static final Dimension AMOUNT_OF_SUBSTANCE = new QuantityDimension('N');

    /**
     * Holds luminous intensity dimension (J).
     */
    public static final Dimension LUMINOUS_INTENSITY = new QuantityDimension('J');

    /**
     * Holds the pseudo unit associated to this dimension.
     */
    private final AbstractUnit<?> pseudoUnit;

    /**
     * Returns the dimension for the specified quantity type by aggregating
     * the results of {@link DimensionService} or <code>null</code>
     * if the specified quantity is unknown.
     *
     * @param quantityType the quantity type.
     * @return the dimension for the quantity type or <code>null</code>.
     */
    public static final <Q extends Quantity<Q>> Dimension getInstance(Class<Q> quantityType) {
        // TODO: Track OSGi services and aggregate results.
        Unit<Q> siUnit = SI.getInstance().getUnit(quantityType);
        if (siUnit == null) logger.warning("Quantity type: " + quantityType + " unknown");
        return (siUnit != null) ? siUnit.getDimension() : null;
    }

    /**
     * Returns the physical dimension having the specified symbol.
     *
     * @param symbol the associated symbol.
     */
    @SuppressWarnings("rawtypes")
	QuantityDimension(char symbol) {
        pseudoUnit = new BaseUnit("[" + symbol + ']', NONE);
    }
    
    /**
     * Returns the dimension for the specified symbol.
     *
     * @param sambol the quantity symbol.
     * @return the dimension for the given symbol.
     */
    static final QuantityDimension getInstance(char symbol) {
    	return new QuantityDimension(symbol);
    }

    /**
     * Constructor from pseudo-unit (not visible).
     *
     * @param pseudoUnit the pseudo-unit.
     */
    private QuantityDimension(AbstractUnit<?> pseudoUnit) {
        this.pseudoUnit = pseudoUnit;
    }

    /**
     * Returns the product of this dimension with the one specified.
     * If the specified dimension is not a physics dimension, then
     * <code>that.multiply(this)</code> is returned.
     *
     * @param  that the dimension multiplicand.
     * @return <code>this * that</code>
     */
    public Dimension multiply(Dimension that) {
        return (that instanceof QuantityDimension) ?
            this.multiply((QuantityDimension)that) : that.multiply(this);
    }

    /**
     * Returns the product of this dimension with the one specified.
     *
     * @param  that the dimension multiplicand.
     * @return <code>this * that</code>
     */
    public QuantityDimension multiply(QuantityDimension that) {
        return new QuantityDimension(this.pseudoUnit.multiply(that.pseudoUnit));
    }

    /**
     * Returns the quotient of this dimension with the one specified.
     *
     * @param  that the dimension divisor.
     * @return <code>this.multiply(that.pow(-1))</code>
     */
    public Dimension divide(Dimension that) {
        return this.multiply(that.pow(-1));
    }

    /**
     * Returns the quotient of this dimension with the one specified.
     *
     * @param  that the dimension divisor.
     * @return <code>this.multiply(that.pow(-1))</code>
     */
    public QuantityDimension divide(QuantityDimension that) {
        return this.multiply(that.pow(-1));
    }

    /**
     * Returns this dimension raised to an exponent.
     *
     * @param  n the exponent.
     * @return the result of raising this dimension to the exponent.
     */
    public final QuantityDimension pow(int n) {
        return new QuantityDimension(this.pseudoUnit.pow(n));
    }

    /**
     * Returns the given root of this dimension.
     *
     * @param  n the root's order.
     * @return the result of taking the given root of this dimension.
     * @throws ArithmeticException if <code>n == 0</code>.
     */
    public final QuantityDimension root(int n) {
        return new QuantityDimension(this.pseudoUnit.root(n));
    }

    /**
     * Returns the fundamental dimensions and their exponent whose product is
     * this dimension or <code>null</code> if this dimension is a fundamental
     * dimension.
     *
     * @return the mapping between the fundamental dimensions and their exponent.
     */
    @SuppressWarnings("rawtypes")
	public Map<? extends QuantityDimension, Integer> getProductDimensions() {
        Map<? extends AbstractUnit, Integer> pseudoUnits = pseudoUnit.getProductUnits();
        if (pseudoUnit == null) return null;
        Map<QuantityDimension, Integer> fundamentalDimensions = new HashMap<>();
        for (Map.Entry<? extends AbstractUnit, Integer> entry : pseudoUnits.entrySet()) {
            fundamentalDimensions.put(new QuantityDimension(entry.getKey()), entry.getValue());
        }
        return fundamentalDimensions;
    }

    @Override
    public String toString() {
        return pseudoUnit.toString();
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof QuantityDimension) {
			QuantityDimension other = (QuantityDimension) obj;
			return Objects.equals(pseudoUnit, other.pseudoUnit);
		}
		return false;
	}

    @Override
    public int hashCode() {
        return Objects.hashCode(pseudoUnit);
    }

}
