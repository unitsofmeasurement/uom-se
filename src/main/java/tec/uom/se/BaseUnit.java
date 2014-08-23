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

import tec.uom.se.function.AbstractConverter;
import tec.uom.se.model.QuantityDimension;

import javax.measure.Dimension;
import javax.measure.Quantity;
import javax.measure.function.UnitConverter;

import java.util.Map;


/**
 * <p> This class represents the building blocks on top of which all others
 *     physical units are created. Base units are always unscaled SI units.</p>
 * 
 * <p> When using the {@link tec.uom.se.model.StandardModel standard model},
 *     all seven {@link org.org.unitsofmeasurement.impl.system.SI SI} base units
 *     are dimensionally independent.</p>
 *
 * @see <a href="http://en.wikipedia.org/wiki/SI_base_unit">
 *       Wikipedia: SI base unit</a>
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.2, May 22, 2014
 */
public class BaseUnit<Q extends Quantity<Q>> extends AbstractUnit<Q> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1721629233768215930L;

	/**
     * Holds the symbol.
     */
    private final String symbol;

    /**
     * Holds the base unit dimension.
     */
    private final Dimension dimension;

    /**
     * Creates a base unit having the specified symbol and dimension.
     *
     * @param symbol the symbol of this base unit.
     */
    public BaseUnit(String symbol, Dimension dimension) {
        this.symbol = symbol;
        this.dimension = dimension;
    }
    
    /**
     * Creates a base unit having the specified symbol and dimension.
     *
     * @param symbol the symbol of this base unit.
     */
    public BaseUnit(String symbol) {
        this.symbol = symbol;
        this.dimension = QuantityDimension.NONE;
    }
    
    /**
     * Creates a base unit having the specified symbol and name.
     *
     * @param symbol the symbol of this base unit.
     * @param name the name of this base unit.
     * @throws IllegalArgumentException if the specified symbol is
     *         associated to a different unit.
     */
    public BaseUnit(String symbol, String name) {
        this(symbol);
        this.name = name;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public AbstractUnit<Q> toSI() {
        return this;
    }

    @Override
    public UnitConverter getConverterToSI() throws UnsupportedOperationException {
        return AbstractConverter.IDENTITY;
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }

    @Override
    public final boolean equals(Object that) {
        if (this == that) return true;
        if (!(that instanceof BaseUnit)) return false;
        BaseUnit<?> thatUnit = (BaseUnit<?>) that;
        return this.symbol.equals(thatUnit.symbol) 
                && this.dimension.equals(thatUnit.dimension);
    }

    @Override
    public final int hashCode() {
        return symbol.hashCode();
    }

	@Override
	public Map<? extends AbstractUnit<Q>, Integer> getProductUnits() {
		// TODO Auto-generated method stub
		return null;
	}
}
