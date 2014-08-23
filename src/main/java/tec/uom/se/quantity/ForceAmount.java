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
package tec.uom.se.quantity;

import javax.measure.Unit;
import javax.measure.quantity.Force;

import tec.uom.se.BaseQuantity;

/**
 * Represents a quantity that tends to produce an acceleration of a body in the
 * direction of its application. The metric system unit for this quantity is "N"
 * (Newton).
 * 
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 1.3, $Date: 2013-05-21 $
 */
public final class ForceAmount extends BaseQuantity<Force> implements Force {

	public ForceAmount(Number number, Unit<Force> unit) {
		super(number, unit);
	}
}
