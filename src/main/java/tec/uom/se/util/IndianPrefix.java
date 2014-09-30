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
package tec.uom.se.util;

import java.math.BigInteger;

import javax.measure.Quantity;
import javax.measure.Unit;

import tec.uom.se.function.RationalConverter;


/**
 * Utility class holding prefixes used today in India, Pakistan, Bangladesh, Nepal
 * and Myanmar (Burma); based on grouping by two decimal places, rather than the
 * three decimal places common in most parts of the world. [code] import static
 * org.eclipse.uomo.units.IndianPrefix.*; // Static import. ... Unit<Pressure>
 * LAKH_PASCAL = LAKH(PASCAL);
 * Unit<Length>CRORE_METER = CRORE(METER); [/code]
 * 
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.4 ($Revision: 212 $), $Date: 2010-09-13 23:50:44 +0200 (Mo, 13 Sep 2010) $
 * @see <a
 *      href="http://en.wikipedia.org/wiki/Indian_numbering_system">Wikipedia: Indian numbering system</a>
 */
public abstract class IndianPrefix {

	/**
	 * <p>
	 * एक (Ek)
	 * </p>
	 * Returns the specified unit multiplied by the factor <code>1</code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> EK(Unit<Q> unit) {
		return unit;
	}

	/**
	 * <p>
	 * दस (Das)
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>1</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(10)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> DAS(Unit<Q> unit) {
		return unit.transform(E1);
	}

	/**
	 * <p>
	 * सौ (Sau)
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>2</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(100)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> SAU(Unit<Q> unit) {
		return unit.transform(E2);
	}

	/**
	 * <p>
	 * सहस्र (Sahasr)
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>3</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e3)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> SAHASR(Unit<Q> unit) {
		return unit.transform(E3);
	}

	/**
	 * <p>
	 * हजार (Hazaar)
	 * </p>
	 * Equivalent to {@link #SAHASR}.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> HAZAAR(Unit<Q> unit) {
		return SAHASR(unit);
	}

	/**
	 * <p>
	 * लाख (Lakh)
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>5</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e5)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> LAKH(Unit<Q> unit) {
		return unit.transform(E5);
	}

	static final RationalConverter E5 = new RationalConverter(
			BigInteger.TEN.pow(5), BigInteger.ONE);

	/**
	 * <p>
	 * करोड़ (Crore)
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>7</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e7)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> CRORE(Unit<Q> unit) {
		return unit.transform(E7);
	}

	static final RationalConverter E7 = new RationalConverter(
			BigInteger.TEN.pow(7), BigInteger.ONE);

	/**
	 * <p>
	 * अरब (Arawb)
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>9</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e9)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> ARAWB(Unit<Q> unit) {
		return unit.transform(E9);
	}

	/**
	 * <p>
	 * खरब (Kharawb)
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>11</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e11)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> KHARAWB(Unit<Q> unit) {
		return unit.transform(E11);
	}

	/**
	 * <p>
	 * नील (Neel)
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>13</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e13)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> NEEL(Unit<Q> unit) {
		return unit.transform(E13);
	}

	/**
	 * <p>
	 * पद्म (Padma)
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>15</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e15)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> PADMA(Unit<Q> unit) {
		return unit.transform(E15);
	}

	/**
	 * <p>
	 * शंख (Shankh)
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>17</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e17)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> SHANKH(Unit<Q> unit) {
		return unit.transform(E17);
	}

	/**
	 * <p>
	 * महाशंख (Mahashankh)
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>19</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e19)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> MAHASHANKH(Unit<Q> unit) {
		return unit.transform(E19);
	}

	// Holds prefix converters (optimization).
	private static RationalConverter E19 = new RationalConverter(
			BigInteger.TEN.pow(19), BigInteger.ONE);
	private static RationalConverter E17 = new RationalConverter(
			BigInteger.TEN.pow(17), BigInteger.ONE);
	private static RationalConverter E15 = new RationalConverter(
			BigInteger.TEN.pow(15), BigInteger.ONE);
	private static RationalConverter E13 = new RationalConverter(
			BigInteger.TEN.pow(13), BigInteger.ONE);
	private static RationalConverter E11 = new RationalConverter(
			BigInteger.TEN.pow(11), BigInteger.ONE);
	private static RationalConverter E9 = new RationalConverter(
			BigInteger.TEN.pow(9), BigInteger.ONE);
	private static RationalConverter E3 = new RationalConverter(
			BigInteger.TEN.pow(3), BigInteger.ONE);
	private static RationalConverter E2 = new RationalConverter(
			BigInteger.TEN.pow(2), BigInteger.ONE);
	private static RationalConverter E1 = new RationalConverter(
			BigInteger.TEN.pow(1), BigInteger.ONE);
}
