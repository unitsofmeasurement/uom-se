/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright (c) 2005-2015, Jean-Marie Dautelle, Werner Keil, V2COM.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of JSR-363 nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
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

import static tec.uom.se.spi.internal.NonSI.AVOIRDUPOIS_POUND_DIVIDEND;
import static tec.uom.se.spi.internal.NonSI.AVOIRDUPOIS_POUND_DIVISOR;
import static tec.uom.se.util.SI.*;
import static tec.uom.se.util.SIPrefix.MICRO;

import javax.measure.Unit;
import javax.measure.quantity.Area;
import javax.measure.quantity.DynamicViscosity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Temperature;
import javax.measure.quantity.Time;
import javax.measure.quantity.Volume;
import javax.measure.spi.SystemOfUnits;

import tec.uom.se.AbstractSystemOfUnits;
import tec.uom.se.unit.AlternateUnit;
import tec.uom.se.unit.ProductUnit;
import tec.uom.se.unit.TransformedUnit;
import tec.uom.se.util.US;


/**
 * <p>
 * This class contains units from the Imperial system.
 * </p>
 * <p>
 * 
 * @noextend This class is not intended to be extended by clients.
 * 
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.3, $Date: 2015-01-28 $
 * @see <a
 *      href="http://en.wikipedia.org/wiki/http://en.wikipedia.org/wiki/Imperial_unit">Wikipedia:
 *      Imperial Units</a>
 */
public final class Imperial extends AbstractSystemOfUnits  {

	/**
	 * Default constructor (prevents this class from being instantiated).
	 */
	private Imperial() {
	}

	/**
	 * Returns the unique instance of this class.
	 * 
	 * @return the Imperial instance.
	 */
	public static SystemOfUnits getInstance() {
		return INSTANCE;
	}

	private static final Imperial INSTANCE = new Imperial();

	// //////////
	// Length //
	// //////////

	/**
	 * A unit of length equal to <code>0.0254 m</code> (standard name
	 * <code>in</code>).
	 */
//	public static final Unit<Length> INCH = tec.uom.se.util.US.INCH;
	
	// ////////
	// Mass //
	// ////////

	/**
	 * A unit of mass equal to <code>453.59237 grams</code> (avoirdupois pound,
	 * standard name <code>lb</code>).
	 */
	static final Unit<Mass> POUND = addUnit(KILOGRAM.multiply(
			AVOIRDUPOIS_POUND_DIVIDEND).divide(AVOIRDUPOIS_POUND_DIVISOR));

	/**
	 * A unit of mass equal to <code>1 / 16 {@link #POUND}</code> (standard name
	 * <code>oz</code>).
	 */
	public static final Unit<Mass> OUNCE = addUnit(POUND.divide(16));
	
	/**
	 * A unit of mass equal to <code>2240 {@link #POUND}</code> (long ton,
	 * standard name <code>ton_uk</code>).
	 */
	public static final Unit<Mass> TON_UK = addUnit(POUND.multiply(2240));

	/**
	 * A unit of mass equal to <code>1000 kg</code> (metric ton, standard name
	 * <code>t</code>).
	 */
	public static final Unit<Mass> METRIC_TON = addUnit(KILOGRAM.multiply(1000));

	// ///////////////
	// Temperature //
	// ///////////////

	/**
	 * A unit of temperature equal to <code>5/9 °K</code> (standard name
	 * <code>°R</code>).
	 */
	static final Unit<Temperature> RANKINE = addUnit(KELVIN.multiply(5)
			.divide(9));

	/**
	 * A unit of temperature equal to degree Rankine minus
	 * <code>459.67 °R</code> (standard name <code>°F</code>).
	 * 
	 * @see #RANKINE
	 */
	static final Unit<Temperature> FAHRENHEIT = addUnit(RANKINE
			.shift(459.67));

	// /////////
	// Angle //
	// /////////

	
	// ////////////
	// TimeUnit //
	// ////////////
	/**
	 * A unit of time equal to <code>60 s</code> (standard name <code>min</code>
	 * ).
	 */
	static final Unit<Time> MINUTE = addUnit(SECOND.multiply(60));

	/**
	 * A unit of duration equal to <code>60 {@link #MINUTE}</code> (standard
	 * name <code>h</code>).
	 */
	static final Unit<Time> HOUR = addUnit(MINUTE.multiply(60));

	// ////////////
	// Velocity //
	// ////////////


	// ////////
	// Area //
	// ////////

	/**
	 * A unit of area (standard name <code>sft</code>
	 * ).
	 */
//	public static final Unit<Area> SQUARE_FOOT = US.SQUARE_FOOT;
	
	/**
	 * One acre is 43,560 <code>square feet</code> (standard name <code>a</code>
	 * ).
	 */
	public static final Unit<Area> ACRE = addUnit(US.SQUARE_FOOT.multiply(43560));

	// ///////////////
	// Data Amount //
	// ///////////////
	

	// //////////
	// Energy //
	// //////////


	// //////////
	// Volume //
	// //////////
	/**
	 * A unit of volume equal to one cubic decimeter (default label
	 * <code>L</code>, also recognized <code>ÂµL, mL, cL, dL</code>).
	 */
	static final Unit<Volume> LITRE = addUnit(CUBIC_METRE.divide(1000));

	/**
	 * A unit of volume equal to one cubic inch (<code>in³</code>).
	 */
	static final Unit<Volume> CUBIC_INCH = addUnit(new AlternateUnit<Volume>(US.INCH.pow(3), "in³"));

	/**
	 * A unit of volume equal to <code>4.546 09 {@link #LITRE}</code> (standard
	 * name <code>gal_uk</code>).
	 */
	public static final Unit<Volume> GALLON_UK = addUnit(LITRE.multiply(454609)
			.divide(100000));

	/**
	 * A unit of volume equal to one UK gallon, Liquid Unit.
	 */
	public static final Unit<Volume> GALLON_LIQUID = addUnit(CUBIC_INCH
			.multiply(277.42));
	
	/**
	 * A unit of volume equal to <code>1 / 160 {@link #GALLON_UK}</code>
	 * (standard name <code>oz_fl_uk</code>).
	 */
	static final Unit<Volume> OUNCE_LIQUID_UK = addUnit(GALLON_UK
			.divide(160));
	
	/**
	 * A unit of volume equal to <code>1 / 160 {@link #GALLON_LIQUID}</code>
	 * (standard name <code>oz_fl</code>).
	 */
	public static final Unit<Volume> OUNCE_LIQUID = OUNCE_LIQUID_UK;

	/**
	 * A unit of volume equal to <code>5 {@link #OUNCE_LIQUID}</code> (standard name
	 * <code>gi</code>).
	 */
	public static final Unit<Volume> GILL = addUnit(OUNCE_LIQUID.multiply(5));

	/**
	 * A unit of volume equal to <code>20 {@link #OUNCE_LIQUID}</code> (standard name
	 * <code>pt</code>).
	 */
	public static final Unit<Volume> PINT = addUnit(OUNCE_LIQUID.multiply(20));

	/**
	 * A unit of volume equal to <code>40 {@link #OUNCE_LIQUID}</code> (standard name
	 * <code>qt</code>).
	 */
	public static final Unit<Volume> QUART = addUnit(OUNCE_LIQUID.multiply(40));
	
	/**
	 * A unit of volume <code>~ 1 drop or 0.95 grain of water </code> (standard
	 * name <code>min</code>).
	 */
	public static final Unit<Volume> MINIM = addUnit(MICRO(LITRE).multiply(
			59.1938802d));

	/**
	 * A unit of volume equal to <code>20 {@link #MINIM}</code> (standard name
	 * <code>fl scr</code>).
	 */
	public static final Unit<Volume> FLUID_SCRUPLE = addUnit(MINIM.multiply(60));

	/**
	 * A unit of volume equal to <code>3 {@link #FLUID_SCRUPLE}</code> (standard name
	 * <code>fl drc</code>).
	 */
	public static final Unit<Volume> FLUID_DRACHM = addUnit(FLUID_SCRUPLE.multiply(3));

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
    
	public String getName() {
		return getClass().getSimpleName();
	}
}
