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


/**
 * <p> This class represents the units derived from other units using
 *     {@linkplain UnitConverter converters}.</p>
 *
 * <p> Examples of transformed units:[code]
 *         CELSIUS = KELVIN.shift(273.15);
 *         FOOT = METRE.multiply(3048).divide(10000);
 *         MILLISECOND = MILLI(SECOND);
 *     [/code]</p>
 *
 * <p> Transformed units have no label. But like any other units,
 *     they may have labels attached to them (see {@link org.unitsofmeasurement.impl.format.SymbolMap
 *     SymbolMap}</p>
 *
 * <p> Instances of this class are created through the {@link AbstractUnit#transform} method.</p>
 *
 * @param <Q> The type of the quantity measured by this unit.
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 5.3, March 20, 2014
 */
public final class TransformedUnit<Q extends Quantity<Q>> extends AbstractUnit<Q> {
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Holds the parent unit (always a system unit).
     */
    private final AbstractUnit<Q> parentUnit;

    /**
     * Holds the converter to the parent unit.
     */
    private final UnitConverter toParentUnit;
    
    /**
     * Holds the symbol.
     */
    private final String symbol;

    /**
     * Creates a transformed unit from the specified system unit.
     *
     * @param parentUnit the system unit from which this unit is derived.
     * @param toParentUnit the converter to the parent units.
     * @throws IllegalArgumentException if the specified parent unit is not an
     *         {@link AbstractUnit#isSystemUnit() system unit}
     */
    public TransformedUnit(AbstractUnit<Q> parentUnit, UnitConverter toParentUnit) {
        if (!parentUnit.isSI())
            throw new IllegalArgumentException("The parent unit: " +  parentUnit
                    + " is not a system unit");
        this.parentUnit = parentUnit;
        this.toParentUnit = toParentUnit;
        this.symbol = parentUnit.getSymbol();
    }

    @Override
    public Dimension getDimension() {
        return parentUnit.getDimension();
    }

    @Override
    public UnitConverter getConverterToSI() {
        return parentUnit.getConverterToSI().concatenate(toParentUnit);
    }

    @Override
    public AbstractUnit<Q> toSI() {
        return parentUnit.toSI();
    }

    @Override
    public Map<? extends AbstractUnit<?>, Integer> getProductUnits() {
        return parentUnit.getProductUnits();
    }

    @Override
    public int hashCode() {
        return parentUnit.hashCode() + toParentUnit.hashCode();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that)
            return true;
        if (!(that instanceof TransformedUnit))
            return false;
        TransformedUnit thatUnit = (TransformedUnit) that;
        return this.parentUnit.equals(thatUnit.parentUnit) &&
                this.toParentUnit.equals(thatUnit.toParentUnit);
    }

	@Override
	public String getSymbol() {
		return symbol;
	}
}
