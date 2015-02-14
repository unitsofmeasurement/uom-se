/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright (c) 2005-2015, Jean-Marie Dautelle, Werner Keil, V2COM.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 *    and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of JSR-363 nor the names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package tec.uom.se.spi;

import tec.uom.se.AbstractSystemOfUnits;
import tec.uom.se.function.Nameable;
import tec.uom.se.util.SI;

import javax.measure.Unit;
import javax.measure.quantity.Speed;
import javax.measure.spi.SystemOfUnits;

import static tec.uom.se.util.SI.METRES_PER_SECOND;

/**
 * <p> This class defines commonly used units outside the {@link SI} standard.
 *
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.3, February 14, 2015
*/
public class CommonUnits extends AbstractSystemOfUnits implements Nameable {

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
}
