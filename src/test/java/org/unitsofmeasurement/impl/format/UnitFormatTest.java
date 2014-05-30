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
package org.unitsofmeasurement.impl.format;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.unitsofmeasurement.impl.quantity.LengthAmount;

import javax.measure.Unit;
import javax.measure.format.ParserException;
import javax.measure.format.UnitFormat;
import javax.measure.quantity.Length;
import javax.measure.quantity.Speed;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.unitsofmeasurement.impl.format.UCUMFormat.Variant.*;
import static org.unitsofmeasurement.impl.util.SI.*;

/**
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 *
 */
public class UnitFormatTest {
	private Length sut;

	@Before
	public void init() {
		sut = new LengthAmount(10, METRE);
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
		Unit<Speed> v = (Unit<Speed>) sut.getUnit().divide(SECOND);
		try {
			format.format(v, a2);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		assertEquals("m/s", a2.toString());
	}

	@Test
	public void testFormatUCUMPrint() {
		final UnitFormat format = UCUMFormat.getInstance(PRINT);
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
		Unit<Speed> v = (Unit<Speed>) sut.getUnit().divide(SECOND);
		try {
			format.format(v, a2);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		assertEquals("1/s.m", a2.toString());
	}

	@Test
	public void testFormatUCUMCS() {
		final UnitFormat format = UCUMFormat.getInstance(CASE_SENSITIVE);
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
		Unit<Speed> v = (Unit<Speed>) sut.getUnit().divide(SECOND);
		try {
			format.format(v, a2);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		assertEquals("1/s.m", a2.toString());
	}

	@Test
	public void testFormatUCUMCI() {
		final UnitFormat format = UCUMFormat.getInstance(CASE_INSENSITIVE);
		final Appendable a = new StringBuilder();
		try {
			format.format(METRE, a);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		assertEquals(METRE, sut.getUnit());
		assertEquals("M", a.toString());
	}

	@Test
	@Ignore
	public void testParseLocal() {
		final UnitFormat format = LocalUnitFormat.getInstance();
		try {
			Unit<?> u = format.parse("min");
			assertEquals("min", u.getSymbol());
		} catch (ParserException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testParseUCUMCS() {
		final UnitFormat format = UCUMFormat.getInstance(CASE_SENSITIVE);
		try {
			Unit<?> u = format.parse("min");
			assertEquals(MINUTE, u);
		} catch (ParserException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseUCUMCI() {
		final UnitFormat format = UCUMFormat.getInstance(CASE_INSENSITIVE);
		try {
			Unit<?> u = format.parse("M");
			assertEquals(METRE, u);
		} catch (ParserException e) {
			fail(e.getMessage());
		}
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testParseUCUMPrint() {
		final UnitFormat format = UCUMFormat.getInstance(PRINT);
		try {
			Unit<?> u = format.parse("kg");
			assertEquals(KILOGRAM, u);
		} catch (ParserException e) {
			fail(e.getMessage());
		}
	}
}
