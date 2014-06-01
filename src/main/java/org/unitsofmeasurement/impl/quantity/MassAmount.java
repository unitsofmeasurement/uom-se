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

import javax.measure.Unit;
import javax.measure.quantity.Mass;

/**
 * Represents the measure of the quantity of matter that a body or an object contains.
 * The mass of the body is not dependent on gravity and therefore is different from but
 * proportional to its weight.
 * The metric system unit for this quantity is "kg" (kilogram).
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 1.7, $Date: 2013-12-26 $
 */
public final class MassAmount extends BaseQuantity<Mass> implements Mass {

	/**
	 * 
	 */
//	private static final long serialVersionUID = -3190275944382844647L;

	public MassAmount(Number number, Unit<Mass> unit) {
		super(number, unit);
	}
}
