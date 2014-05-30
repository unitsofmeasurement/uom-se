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
package org.unitsofmeasurement.impl.function;

import javax.measure.function.UnitConverter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p> The base class for our {@link UnitConverter} physics implementations.</p>
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 5.3, April 16, 2014
 */
public abstract class AbstractConverter implements UnitConverter {
	
    /**
	 * 
	 */
//	private static final long serialVersionUID = 5790242858468427131L;

	/**
     * The ratio of the circumference of a circle to its diameter.
     **/
    protected static final double PI = 3.1415926535897932384626433832795;
    
    /**
     * Holds identity converter.
     */
    public static final AbstractConverter IDENTITY = new Identity();

    /**
     * Default constructor.
     */
    protected AbstractConverter() {
    }

    /**
     * Concatenates this physics converter with another physics converter.
     * The resulting converter is equivalent to first converting by the
     * specified converter (right converter), and then converting by
     * this converter (left converter).
     *
     * @param that the other converter.
     * @return the concatenation of this converter with that converter.
     */
    public AbstractConverter concatenate(AbstractConverter that) {
        return (that == IDENTITY) ? this : new Compound(this, that);
    }

    @Override
    public boolean isIdentity() {
        return false;
    }

    @Override
    public abstract boolean equals(Object cvtr);

    @Override
    public abstract int hashCode();

    @Override
    public abstract AbstractConverter inverse();

    @Override
    public UnitConverter concatenate(UnitConverter converter) {
        return (converter == IDENTITY) ? this : new Compound(this, converter);
    }

    @Override
    public List<? extends UnitConverter> getCompoundConverters() {
        List<AbstractConverter> converters = new ArrayList<>();
        converters.add(this);
        return converters;
    }

    @Override
    public Number convert(Number value) {
        if (value instanceof BigDecimal) {
        	return convert((BigDecimal)value, MathContext.UNLIMITED);
        }
        return convert(value.doubleValue());
    }

    public abstract double convert(double value);
    		
    public abstract BigDecimal convert(BigDecimal value, MathContext ctx) throws ArithmeticException;
    
    /**
     * This class represents the identity converter (singleton).
     */
    private static final class Identity extends AbstractConverter {

        @Override
        public boolean isIdentity() {
            return true;
        }

        @Override
        public Identity inverse() {
            return this;
        }

        @Override
        public double convert(double value) {
            return value;
        }

        @Override
        public BigDecimal convert(BigDecimal value, MathContext ctx) {
            return value;
        }

        @Override
        public UnitConverter concatenate(UnitConverter converter) {
            return converter;
        }

        @Override
        public boolean equals(Object cvtr) {
            return (cvtr instanceof Identity);
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean isLinear() {
            return true;
        }
    }

    /**
     * This class represents converters made up of two or more separate
     * converters (in matrix notation <code>[compound] = [left] x [right]</code>).
     */
    private static final class Compound extends AbstractConverter {

        /**
         * Holds the first converter.
         */
        private UnitConverter left;

        /**
         * Holds the second converter.
         */
        private UnitConverter right;

        /**
         * Creates a compound converter resulting from the combined
         * transformation of the specified converters.
         *
         * @param  left the left converter.
         * @param  right the right converter.
         */
        public Compound(UnitConverter left, UnitConverter right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean isLinear() {
            return left.isLinear() && right.isLinear();
        }

        @Override
        public boolean isIdentity() {
            return false;
        }

        @Override
        public List<UnitConverter> getCompoundConverters() {
            List<UnitConverter> converters = new ArrayList<>();
            List<? extends UnitConverter> leftCompound = left.getCompoundConverters();
            List<? extends UnitConverter> rightCompound = right.getCompoundConverters();
            converters.addAll(leftCompound);
            converters.addAll(rightCompound);
            return converters;
        }

        @Override
        public Compound inverse() {
            return new Compound(right.inverse(), left.inverse());
        }

        @Override
        public double convert(double value) {
            return left.convert(right.convert(value));
        }

        @Override
        public BigDecimal convert(BigDecimal value, MathContext ctx) {
        	if (right instanceof AbstractConverter) {
        		return ((AbstractConverter)left).convert(((AbstractConverter)right).convert(value, ctx), ctx);
        	}
            return (BigDecimal)left.convert(right.convert(value));
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
            	return true;
            }
			if (obj instanceof Compound) {
				Compound that = (Compound) obj;
				return Objects.equals(left, that.left)
						&& Objects.equals(right, that.right);
			}
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right);
        }

    }

}
