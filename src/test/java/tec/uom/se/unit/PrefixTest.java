/*
 * Units of Measurement Implementation for Java SE
 * Copyright © 2005-2015, Jean-Marie Dautelle, Werner Keil, V2COM.
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
package tec.uom.se.unit;

import static org.junit.Assert.assertEquals;
import static tec.uom.se.unit.MetricPrefix.*;
import static tec.uom.se.unit.Units.GRAM;
import static tec.uom.se.unit.Units.KILOGRAM;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;

import org.junit.Ignore;
import org.junit.Test;

import tec.uom.se.quantity.Quantities;

public class PrefixTest {
	@Test
	public void testKilo() {
		// TODO how to handle equals for units?
		assertEquals(KILOGRAM.getSymbol(), KILO(GRAM).getSymbol());
		assertEquals(KILOGRAM.toString(), KILO(GRAM).toString());
	}
	
	@Test
	public void testMega() {
		Quantity<Mass> m1 = Quantities.getQuantity(1.0, MEGA(Units.GRAM));
		assertEquals(1d, m1.getValue());
		assertEquals("Mg", m1.getUnit().toString());
	}
	
	@Test
	public void testMicro2() {
		Quantity<Length> m1 = Quantities.getQuantity(1.0, Units.METRE);
		assertEquals(1d, m1.getValue());
		assertEquals("m", m1.getUnit().toString());
				
		Quantity<Length> m2 = m1.to(MICRO(Units.METRE));
		assertEquals(1000000.0d, m2.getValue());
		assertEquals("µm", m2.getUnit().toString());
	}
	
	@Test
	@Ignore
	public void testNano() {
		Quantity<Mass> m1 = Quantities.getQuantity(1.0, Units.GRAM);
		assertEquals(1d, m1.getValue());
		assertEquals("g", m1.getUnit().toString());
				
		Quantity<Mass> m2 = m1.to(NANO(Units.GRAM));
		assertEquals(1000000000.0d, m2.getValue());
		assertEquals("ng", m2.getUnit().toString());
	}
	
	@Test
	public void testNano2() {
		Quantity<Length> m1 = Quantities.getQuantity(1.0, Units.METRE);
		assertEquals(1d, m1.getValue());
		assertEquals("m", m1.getUnit().toString());
				
		Quantity<Length> m2 = m1.to(NANO(Units.METRE));
		assertEquals(1000000000.0d, m2.getValue());
		assertEquals("nm", m2.getUnit().toString());
	}
}
