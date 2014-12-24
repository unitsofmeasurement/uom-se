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
package tec.uom.se.function;

import static org.junit.Assert.*;
import static tec.uom.se.util.SI.*;
import static tec.uom.se.util.SIPrefix.*;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.UnitConverter;
import javax.measure.quantity.Length;

import org.junit.Test;

import tec.uom.se.quantity.Quantities;

public class UnitConverterTest {
	private Unit<Length> sourceUnit = METRE;
	private Unit<Length> targetUnit = CENTI(METRE);
	
	@Test
	public void testDouble() {
		UnitConverter converter = sourceUnit.getConverterTo(targetUnit);
		double length1 = 4.0;
		double length2 = 6.0;
		double result1 = converter.convert(length1);
		double result2 = converter.convert(length2);
		assertEquals(400, result1, 0);
		assertEquals(600, result2, 0);
	}
	
	@Test
	public void testQuantity() {
		Quantity<Length> quantLength1 = Quantities.getQuantity(4.0, sourceUnit);
		Quantity<Length> quantLength2 = Quantities.getQuantity(6.0, targetUnit);
		Quantity<Length> quantResult1 = quantLength1.to(targetUnit);
		assertNotNull(quantResult1);
		assertEquals(Double.valueOf(400), quantResult1.getValue());
		assertEquals(targetUnit, quantResult1.getUnit());
	}

}
