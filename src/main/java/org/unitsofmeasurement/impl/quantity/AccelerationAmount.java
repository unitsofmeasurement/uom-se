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
import javax.measure.quantity.Acceleration;

import org.unitsofmeasurement.impl.BaseQuantity;

/**
 * Represents the rate of change of velocity with respect to time.
 * The metric system unit for this quantity is "m/s²" (metre per square second).
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 1.6.1, $Date: 2014-04-03 03:38:25 +0200 (Do, 03 Apr 2014) $
 */
public final class AccelerationAmount extends BaseQuantity<Acceleration> 
   implements Acceleration {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3979825836742796484L;

	public AccelerationAmount(Number number, Unit<Acceleration> unit) {
		super(number, unit);
	}
}
