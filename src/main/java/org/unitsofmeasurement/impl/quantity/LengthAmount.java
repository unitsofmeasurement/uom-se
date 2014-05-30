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
package org.unitsofmeasurement.impl.quantity;

import org.unitsofmeasurement.impl.BaseQuantity;

import javax.measure.Unit;
import javax.measure.quantity.Length;

/**
 * Represents the extent of something along its greatest
 * dimension or the extent of space between two objects or places.
 * The metric system unit for this quantity is "m" (metre).
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 1.6, $Date: 2013-12-25 $
 */
public final class LengthAmount extends BaseQuantity<Length> implements Length {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1088138019909223368L;

	public LengthAmount(Number number, Unit<Length> unit) {
		super(number, unit);
	}
}
