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
package tec.uom.se.function;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

import javax.measure.function.UnitConverter;


/**
 * <p> This class represents a converter adding a constant offset
 *     to numeric values (<code>double</code> based).</p>
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author Werner Keil
 * @version 5.1, March 18, 2014
 */
public final class AddConverter extends AbstractConverter {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2981335308595652284L;
	/**
     * Holds the offset.
     */
    private double offset;

    /**
     * Creates an additive converter having the specified offset.
     *
     * @param  offset the offset value.
     * @throws IllegalArgumentException if offset is <code>0.0</code>
     *         (would result in identity converter).
     */
    public AddConverter(double offset) {
        if (offset == 0.0)
            throw new IllegalArgumentException("Would result in identity converter");
        this.offset = offset;
    }

    /**
     * Returns the offset value for this add converter.
     *
     * @return the offset value.
     */
    public double getOffset() {
        return offset;
    }

    @Override
    public UnitConverter concatenate(UnitConverter converter) {
        if (!(converter instanceof AddConverter))
            return super.concatenate(converter);
        double newOffset = offset + ((AddConverter) converter).offset;
        return newOffset == 0.0 ? IDENTITY : new AddConverter(newOffset);
    }

    @Override
    public AddConverter inverse() {
        return new AddConverter(-offset);
    }

    @Override
    public double convert(double value) {
        return value + offset;
    }

    @Override
    public BigDecimal convert(BigDecimal value, MathContext ctx) throws ArithmeticException {
        return value.add(BigDecimal.valueOf(offset), ctx);
    }

    @Override
    public final String toString() {
        return "AddConverter(" + offset + ")";
    }

    @Override
    public boolean equals(Object obj) {
    	if (this == obj) {
    		return true;
    	}
        if (obj instanceof AddConverter) {
        	AddConverter other = (AddConverter) obj;
            return Objects.equals(offset, other.offset);
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(offset);
    }

    @Override
    public boolean isLinear() {
        return false;
    }

	public Double value() {
		return offset;
	}
    
}
