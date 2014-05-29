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
package org.unitsofmeasurement.impl.util;

import javax.measure.Quantity;
import javax.measure.Unit;

/**
 * <p> This class provides support for common binary prefixes to be used by
 *     units.</p>
 *
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 5.1, December 25, 2013
 */
public final class BinaryPrefix {
// FIXME change to enum like SIPrefix
	
     /**
     * Default constructor (private).
     */
	private BinaryPrefix() {
		// Utility class no visible constructor.
	}

	/**
	 * Returns the specified unit multiplied by the factor
	 * <code>2<sup>10</sup></code> (binary prefix).
	 * 
	 * @param unit any unit.
	 * @return <code>unit.times(1024)</code>.
	 */
	public static <Q extends Quantity<Q>> Unit<Q> KIBI(Unit<Q> unit) {
		return unit.multiply(1024);
	}

	/**
	 * Returns the specified unit multiplied by the factor
	 * <code>2<sup>20</sup></code> (binary prefix).
	 * 
	 * @param unit any unit.
	 * @return <code>unit.times(1048576)</code>.
	 */
	public static <Q extends Quantity<Q>> Unit<Q> MEBI(Unit<Q> unit) {
		return unit.multiply(1048576);
	}

	/**
	 * Returns the specified unit multiplied by the factor
	 * <code>2<sup>30</sup></code> (binary prefix).
	 * 
	 * @param unit any unit.
	 * @return <code>unit.times(1073741824)</code>.
	 */
	public static <Q extends Quantity<Q>> Unit<Q> GIBI(Unit<Q> unit) {
		return unit.multiply(1073741824);
	}

	/**
	 * Returns the specified unit multiplied by the factor
	 * <code>2<sup>40</sup></code> (binary prefix).
	 * 
	 * @param unit any unit.
	 * @return <code>unit.times(1099511627776L)</code>.
	 */
	public static <Q extends Quantity<Q>> Unit<Q> TEBI(Unit<Q> unit) {
		return unit.multiply(1099511627776L);
	}

	/**
	 * Returns the specified unit multiplied by the factor
	 * <code>2<sup>50</sup></code> (binary prefix).
	 * 
	 * @param unit any unit.
	 * @return <code>unit.times(1125899906842624L)</code>.
	 */
	public static <Q extends Quantity<Q>> Unit<Q> PEBI(Unit<Q> unit) {
		return unit.multiply(1125899906842624L);
	}

	/**
	 * Returns the specified unit multiplied by the factor
	 * <code>2<sup>60</sup></code> (binary prefix).
	 * 
	 * @param unit any unit.
	 * @return <code>unit.times(1152921504606846976L)</code>.
	 */
	public static <Q extends Quantity<Q>> Unit<Q> EXBI(Unit<Q> unit) {
		return unit.multiply(1152921504606846976L);
	}
}
