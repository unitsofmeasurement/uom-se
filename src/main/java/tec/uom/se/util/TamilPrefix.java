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
 * Utility class holding prefixes used today in parts of India and Sri Lanka;
 * based on grouping by two decimal places, rather than the
 * three decimal places common in most parts of the world.</br><code> import static
 * org.eclipse.uomo.units.TamilPrefix.*; // Static import. ... Unit<Pressure>
 * ONDRU_PASCAL = ONDRU(PASCAL); 
 * Unit<Length> PATHU_METER = PATHU(METER); </code>
 * 
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.2 ($Revision$), $Date: 2012-04-02 $
 * @see <a
 *      href="http://en.wikipedia.org/wiki/Tamil_units_of_measurement#Whole_numbers">Wikipedia:
 *      Tamil units of measurement - Whole numbers</a>
 */
public abstract class TamilPrefix  {

	/**
	 * <p>
	 * onRu
	 * </p>
	 * Returns the specified unit multiplied by the factor <code>1</code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> onRu(Unit<Q> unit) {
		return unit;
	}
	
	/**
	 * <p>
	 * patthu
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>1</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(10)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> patthu(Unit<Q> unit) {
		return unit.transform(E1);
	}

	/**
	 * <p>
	 * nooRu
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>2</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(100)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> nooRu(Unit<Q> unit) {
		return unit.transform(E2);
	}

	/**
	 * <p>
	 * aayiram
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>3</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e3)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> aayiram(Unit<Q> unit) {
		return unit.transform(E3);
	}

	/**
	 * <p>
	 * nooRaayiram
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>5</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e5)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> nooRaayiram(Unit<Q> unit) {
		return unit.transform(E5);
	}

	static final RationalConverter E5 = new RationalConverter(
			BigInteger.TEN.pow(5), BigInteger.ONE);

	/**
	 * <p>
	 * thoLLunn
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>9</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e9)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> thoLLunn(Unit<Q> unit) {
		return unit.transform(E9);
	}

	/**
	 * <p>
	 * eegiyam
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>12</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e12)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> eegiyam(Unit<Q> unit) {
		return unit.transform(E12);
	}

	/**
	 * <p>
	 * neLai
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>15</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e15)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> neLai(Unit<Q> unit) {
		return unit.transform(E15);
	}

	/**
	 * <p>
	 * iLanji
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>18</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e18)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> iLanji(Unit<Q> unit) {
		return unit.transform(E18);
	}

	/**
	 * <p>
	 * veLLam
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>20</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e20)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> veLLam(Unit<Q> unit) {
		return unit.transform(E20);
	}
	
	/**
	 * <p>
	 * aambal
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>21</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e21)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<Q> aambal(Unit<Q> unit) {
		return unit.transform(E21);
	}
	
	public static final class Sanskrit {
		/**
		 * <p>
		 * ONDRU -one
		 * </p>
		 * Sanskrit translation for {@link #onRu}.
		 */
		public static final <Q extends Quantity<Q>> Unit<Q> ONDRU (Unit<Q> unit) {
			return onRu(unit);
		}
		
		/**
		 * <p>
		 * PATHU -ten
		 * </p>
		 * Sanskrit translation for {@link #patthu}.
		 */
		public static final <Q extends Quantity<Q>> Unit<Q> PATHU(Unit<Q> unit) {
			return patthu(unit);
		}
	}

	// Holds prefix converters (optimization).
	private static RationalConverter E21 = new RationalConverter(
			BigInteger.TEN.pow(21), BigInteger.ONE);
	private static RationalConverter E20 = new RationalConverter(
			BigInteger.TEN.pow(20), BigInteger.ONE);
	private static RationalConverter E18 = new RationalConverter(
			BigInteger.TEN.pow(18), BigInteger.ONE);
	private static RationalConverter E15 = new RationalConverter(
			BigInteger.TEN.pow(15), BigInteger.ONE);
	private static RationalConverter E12 = new RationalConverter(
			BigInteger.TEN.pow(12), BigInteger.ONE);
	private static RationalConverter E9 = new RationalConverter(
			BigInteger.TEN.pow(9), BigInteger.ONE);
	private static RationalConverter E3 = new RationalConverter(
			BigInteger.TEN.pow(3), BigInteger.ONE);
	private static RationalConverter E2 = new RationalConverter(
			BigInteger.TEN.pow(2), BigInteger.ONE);
	private static RationalConverter E1 = new RationalConverter(
			BigInteger.TEN.pow(1), BigInteger.ONE);
}
