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

import org.unitsofmeasurement.impl.AbstractUnit;

import javax.measure.Unit;
import javax.measure.quantity.Speed;
import javax.measure.util.SystemOfUnits;

import static org.unitsofmeasurement.impl.util.SI.METRES_PER_SECOND;


public class CommonUnits extends AbstractSystemOfUnits {

	private CommonUnits() {
		
	}
	
	private static final CommonUnits INSTANCE = new CommonUnits();
	
	public String getName() {
		return "Common Units";
	}

	/**
	 * A unit of velocity expressing the number of international {@link #KILOMETRE
	 * kilometres} per {@link #HOUR hour} (abbreviation <code>kph</code>).
	 */
	public static final Unit<Speed> KILOMETRES_PER_HOUR = addUnit(
			METRES_PER_SECOND.multiply(0.277778d)).asType(Speed.class);
	
	/**
	 * Returns the unique instance of this class.
	 * 
	 * @return the Imperial instance.
	 */
	public static SystemOfUnits getInstance() {
		return INSTANCE;
	}
	
    /**
     * Adds a new unit not mapped to any specified quantity type.
     *
     * @param  unit the unit being added.
     * @return <code>unit</code>.
     */
    private static <U extends Unit<?>>  U addUnit(U unit) {
        INSTANCE.units.add(unit);
        return unit;
    }
    
	/**
	 * Adds a new named unit to the collection.
	 * 
	 * @param unit the unit being added.
	 * @param name the name of the unit.
	 * @return <code>unit</code>.
	 */
	@SuppressWarnings("unchecked")
	private static <U extends AbstractUnit<?>> U addUnit(U unit, String name) {
		if (name != null && unit instanceof AbstractUnit) {
			AbstractUnit<?> aUnit = (AbstractUnit<?>)unit;
			//aUnit.setName(name);
			INSTANCE.units.add(aUnit);
			return (U) aUnit;
		}
		INSTANCE.units.add(unit);
		return unit;
	}

}
