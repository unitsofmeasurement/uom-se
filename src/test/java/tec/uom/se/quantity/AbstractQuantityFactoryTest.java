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

import org.junit.Test;

import tec.uom.se.quantity.AbstractQuantityFactory;

import javax.measure.Quantity;
import javax.measure.quantity.*;

import static org.junit.Assert.*;
import static tec.uom.se.util.SI.*;

/**
 * @author Werner Keil
 *
 */
public class AbstractQuantityFactoryTest {

	@Test
	public void testLength() {
		Quantity<Length> l =  AbstractQuantityFactory.getInstance(Length.class).create(23.5, METRE); // 23.0 km
		assertEquals(23.5d, l.getValue());
		assertEquals(METRE, l.getUnit());
		assertEquals("m", l.getUnit().getSymbol());
	}

	
	@Test
	public void testMass() {
		Quantity<Mass> m = AbstractQuantityFactory.getInstance(Mass.class).create(10, KILOGRAM); // 10 kg
		assertEquals(10, m.getValue());
		assertEquals(KILOGRAM, m.getUnit());
		assertEquals("kg", m.getUnit().getSymbol());
		assertEquals("10 kg", m.toString());
	}
	
	@Test
	public void testTime() {
		Quantity<Time> t = AbstractQuantityFactory.getInstance(Time.class).create(40, MINUTE); // 40 min
		assertEquals(40, t.getValue());
		assertEquals(MINUTE, t.getUnit());
		assertEquals("s", t.getUnit().getSymbol()); // FIXME this should be "min", tweak for TransformedUnit
		assertEquals("40 min", t.toString());
	}

}
