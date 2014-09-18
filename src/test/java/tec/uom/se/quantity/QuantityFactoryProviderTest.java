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

import static org.junit.Assert.assertEquals;
import static tec.uom.se.util.SI.KILOGRAM;
import static tec.uom.se.util.SI.METRE;
import static tec.uom.se.util.SI.MINUTE;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Time;

import org.junit.Test;

/**
 * @author Werner Keil
 *
 */
public class QuantityFactoryProviderTest {

	@Test
	public void testLength() {
		Quantity<Length> l =  QuantityFactoryProvider.of(Length.class).create(23.5, METRE); // 23.0 km
		assertEquals(23.5d, l.getValue());
		assertEquals(METRE, l.getUnit());
		assertEquals("m", l.getUnit().getSymbol());
	}


	@Test
	public void testMass() {
		Quantity<Mass> m = QuantityFactoryProvider.of(Mass.class).create(10, KILOGRAM); // 10 kg
		assertEquals(10, m.getValue());
		assertEquals(KILOGRAM, m.getUnit());
		assertEquals("kg", m.getUnit().getSymbol());
		assertEquals("10 kg", m.toString());
	}

	@Test
	public void testTime() {
		Quantity<Time> t = QuantityFactoryProvider.of(Time.class).create(40, MINUTE); // 40 min
		assertEquals(40, t.getValue());
		assertEquals(MINUTE, t.getUnit());
		assertEquals("s", t.getUnit().getSymbol()); // FIXME this should be "min", tweak for TransformedUnit
		assertEquals("40 min", t.toString());
	}

}
