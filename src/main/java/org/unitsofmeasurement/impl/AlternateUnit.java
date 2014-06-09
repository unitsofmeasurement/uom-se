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

import javax.measure.Dimension;
import javax.measure.Quantity;
import javax.measure.function.UnitConverter;

import java.util.Map;
import java.util.Objects;


/**
 * <p> This class represents units used in expressions to distinguish
 *     between quantities of a different nature but of the same dimensions.</p>
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.3, June 09, 2014
 */
public final class AlternateUnit<Q extends Quantity<Q>> extends AbstractUnit<Q> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4696690756456282705L;

	/**
     * Holds the parent unit (a system unit).
     */
    private final AbstractUnit<?> parentUnit;

    /**
     * Holds the symbol for this unit.
     */
    private final String symbol;

    /**
     * Creates an alternate unit for the specified system unit identified by the
     * specified name and symbol.
     *
     * @param parent the system unit from which this alternate unit is derived.
     * @param symbol the symbol for this alternate unit.
     * @throws IllegalArgumentException if the specified parent unit is not an
     *         {@link AbstractUnit#isSystemUnit() system unit}
     */
    public AlternateUnit(AbstractUnit<?> parentUnit, String symbol) {
        if (!parentUnit.isSI())
            throw new IllegalArgumentException("The parent unit: " +  parentUnit
                    + " is not an unscaled SI unit");
        this.parentUnit = (parentUnit instanceof AlternateUnit) ?
            ((AlternateUnit)parentUnit).getParentUnit() : parentUnit;
        this.symbol = symbol;
    }

    /**
     * Returns the parent unit of this alternate unit, always a system unit and
     * never an alternate unit.
     *
     * @return the parent unit.
     */
    public AbstractUnit<?> getParentUnit() {
        return parentUnit;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public Dimension getDimension() {
        return parentUnit.getDimension();
    }

    @Override
    public UnitConverter getConverterToSI() {
        return parentUnit.getConverterToSI();
    }

    @Override
    public AbstractUnit<Q> toSI() {
        return this; // Alternate units are SI units.
    }

    @Override
    public Map<? extends AbstractUnit<?>, Integer> getProductUnits() {
        return parentUnit.getProductUnits();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(symbol);
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof AlternateUnit) {
			AlternateUnit that = (AlternateUnit) obj;
			return Objects.equals(parentUnit, that.parentUnit)
					&& Objects.equals(symbol, that.symbol);
		}
		return false;
	}

}
