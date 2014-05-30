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

import javax.measure.function.ValueSupplier;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * <p> This class represents a converter dividing numeric values by π (Pi).</p>
 *
 * <p> This class is package private, instances are created
 *     using the {@link PiMultiplierConverter#inverse()} method.</p>
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 5.2, April 22, 2014
 */
final class PiDivisorConverter extends AbstractConverter 
	implements ValueSupplier<String> { //implements Immutable<String> {
	

	/**
	 * 
	 */
//	private static final long serialVersionUID = 5052794216568914141L;

	/**
     * Creates a Pi multiplier converter.
     */
    public PiDivisorConverter() {
    }

    @Override
    public double convert(double value) {
        return value / PI;
    }

    @Override
    public BigDecimal convert(BigDecimal value, MathContext ctx) throws ArithmeticException {
        int nbrDigits = ctx.getPrecision();
        if (nbrDigits == 0) throw new ArithmeticException("Pi multiplication with unlimited precision");
        BigDecimal pi = PiMultiplierConverter.Pi.pi(nbrDigits);
        return value.divide(pi, ctx).scaleByPowerOfTen(nbrDigits-1);
    }

    @Override
    public AbstractConverter inverse() {
        return new PiMultiplierConverter();
    }

    @Override
    public final String toString() {
        return "(1/π)";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof PiDivisorConverter) ? true : false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean isLinear() {
        return true;
    }

    @Override
	public String getValue() {
		return toString();
	}

}
