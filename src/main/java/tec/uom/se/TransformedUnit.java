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
package tec.uom.se;

import javax.measure.Dimension;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.function.UnitConverter;

import java.util.Map;
import java.util.Objects;


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
 *     they may have labels attached to them (see {@link tec.uom.se.format.SymbolMap
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
    private final UnitConverter unitConverter;

    /**
     * Holds the symbol.
     */
    private final String symbol;

    /**
     * Creates a transformed unit from the specified system unit.
     * using the parent as symbol
     * @param parentUnit the system unit from which this unit is derived.
     * @param unitConverter the converter to the parent units.
     * @throws IllegalArgumentException if the specified parent unit is not an
     *         {@link AbstractUnit#isSystemUnit() system unit}
     */
    public TransformedUnit(AbstractUnit<Q> parentUnit, UnitConverter unitConverter) {
        this(parentUnit.getSymbol(), parentUnit, unitConverter);
    }

    public TransformedUnit(String symbol, AbstractUnit<Q> parentUnit, UnitConverter unitConverter) {
        if (!parentUnit.isSI()) {
            throw new IllegalArgumentException("The parent unit: " + parentUnit
                    + " is not a system unit");
        }
        this.parentUnit = parentUnit;
        this.unitConverter = unitConverter;
        this.symbol = symbol;
    }
    @Override
    public Dimension getDimension() {
        return parentUnit.getDimension();
    }

    @Override
    public UnitConverter getConverterToSI() {
        return parentUnit.getConverterToSI().concatenate(unitConverter);
    }

    @Override
    public AbstractUnit<Q> toSI() {
        return parentUnit.toSI();
    }

    @Override
    public Map<? extends Unit<?>, Integer> getProductUnits() {
        return parentUnit.getProductUnits();
    }

    @Override
    public int hashCode() {
        return Objects.hash(parentUnit, unitConverter);
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof TransformedUnit) {
			TransformedUnit<?> other = (TransformedUnit<?>) obj;
			return Objects.equals(parentUnit, other.parentUnit)
					&& Objects.equals(unitConverter, other.unitConverter);
		}
		return false;
	}

	@Override
	public String getSymbol() {
		return symbol;
	}
}
