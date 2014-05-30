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
package org.unitsofmeasurement.impl.function;

import javax.measure.function.ValueSupplier;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * <p>
 * This class represents a converter multiplying numeric values by π (Pi).
 * </p>
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Pi"> Wikipedia: Pi</a>
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 5.1, April 22, 2014
 */
public final class PiMultiplierConverter extends AbstractConverter implements
		ValueSupplier<String> {

	/**
	 * Creates a Pi multiplier converter.
	 */
	public PiMultiplierConverter() {
	}

	@Override
	public double convert(double value) {
		return value * PI;
	}

	@Override
	public BigDecimal convert(BigDecimal value, MathContext ctx)
			throws ArithmeticException {
		int nbrDigits = ctx.getPrecision();
		if (nbrDigits == 0)
			throw new ArithmeticException(
					"Pi multiplication with unlimited precision");
		BigDecimal pi = Pi.pi(nbrDigits);
		return value.multiply(pi, ctx).scaleByPowerOfTen(1 - nbrDigits);
	}

	@Override
	public AbstractConverter inverse() {
		return new PiDivisorConverter();
	}

	@Override
	public final String toString() {
		return "(π)";
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof PiMultiplierConverter) ? true : false;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean isLinear() {
		return true;
	}

	/**
	 * Pi calculation with Machin's formula.
	 * 
	 * @see <a
	 *      href="http://en.literateprograms.org/Pi_with_Machin's_formula_(Java)"
	 *      >Pi with Machin's formula</a>
	 * 
	 */
	static final class Pi {

		private Pi() {
		}

		public static BigDecimal pi(int numDigits) {
			int calcDigits = numDigits + 10;
			return FOUR.multiply(
					(FOUR.multiply(arccot(FIVE, calcDigits))).subtract(arccot(
							TWO_THIRTY_NINE, calcDigits))).setScale(numDigits,
					RoundingMode.DOWN);
		}

		private static BigDecimal arccot(BigDecimal x, int numDigits) {
			BigDecimal unity = BigDecimal.ONE.setScale(numDigits,
					RoundingMode.DOWN);
			BigDecimal sum = unity.divide(x, RoundingMode.DOWN);
			BigDecimal xpower = new BigDecimal(sum.toString());
			BigDecimal term = null;
			boolean add = false;
			for (BigDecimal n = new BigDecimal("3"); term == null
					|| !term.equals(BigDecimal.ZERO); n = n.add(TWO)) {
				xpower = xpower.divide(x.pow(2), RoundingMode.DOWN);
				term = xpower.divide(n, RoundingMode.DOWN);
				sum = add ? sum.add(term) : sum.subtract(term);
				add = !add;
			}
			return sum;
		}
	}

	private static final BigDecimal TWO = new BigDecimal("2");

	private static final BigDecimal FOUR = new BigDecimal("4");

	private static final BigDecimal FIVE = new BigDecimal("5");

	private static final BigDecimal TWO_THIRTY_NINE = new BigDecimal("239");

	@Override
	public String getValue() {
		return toString();
	}

}
