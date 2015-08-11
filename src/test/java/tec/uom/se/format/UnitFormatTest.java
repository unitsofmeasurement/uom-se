/*
 *  Unit-API - Units of Measurement API for Java
 *  Copyright (c) 2005-2014, Jean-Marie Dautelle, Werner Keil, V2COM.
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
package tec.uom.se.format;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static tec.uom.se.unit.MetricPrefix.KILO;
import static tec.uom.se.unit.MetricPrefix.MEGA;
import static tec.uom.se.unit.Units.HERTZ;
import static tec.uom.se.unit.Units.METRE;
import static tec.uom.se.unit.Units.SECOND;

import java.io.IOException;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.ParserException;
import javax.measure.format.UnitFormat;
import javax.measure.quantity.Frequency;
import javax.measure.quantity.Length;
import javax.measure.quantity.Speed;

import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

import org.junit.Before;
import org.junit.Test;


/**
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 *
 */
public class UnitFormatTest {
	private Quantity<Length> sut;

	@Before
	public void init() {
		//sut = DefaultQuantityFactoryService.getQuantityFactory(Length.class).create(10, METRE);
		sut = Quantities.getQuantity(10, METRE);
	}

	@Test
	public void testFormat() {
		Unit<Frequency> hz = HERTZ;
		assertEquals("Hz", hz.toString());
	}
	
	@Test
	public void testFormat2() {
		Unit<Frequency> mhz = MEGA(HERTZ);
		assertEquals("Mhz", mhz.toString());
	}

	@Test
	public void testFormat3() {
		Unit<Frequency> khz = KILO(HERTZ);
		assertEquals("khz", khz.toString());
	}
	
	@Test
	public void testFormat4() {
		Unit<Speed> kph = Units.KILOMETRES_PER_HOUR;
		assertEquals("km/h", kph.toString());
	}
	
	@Test
	public void testFormatLocal() {
		final UnitFormat format = LocalUnitFormat.getInstance();
		final Appendable a = new StringBuilder();
		try {
			format.format(METRE, a);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		assertEquals(METRE, sut.getUnit());
		assertEquals("m", a.toString());

		final Appendable a2 = new StringBuilder();
		@SuppressWarnings("unchecked")
		Unit<Speed> v = (Unit<Speed>) METRE.divide(SECOND);
		try {
			format.format(v, a2);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		assertEquals("m/s", a2.toString());
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testParseLocal() {
		final UnitFormat format = LocalUnitFormat.getInstance();
		try {
			Unit<?> u = format.parse("min");
			assertEquals("min", u.getSymbol());
		} catch (ParserException e) {
			fail(e.getMessage());
		}
	}
}
