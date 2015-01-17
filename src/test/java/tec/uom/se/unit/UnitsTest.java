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
package tec.uom.se.unit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import tec.uom.se.AbstractUnit;
import tec.uom.se.util.SI;
import tec.uom.se.util.SIPrefix;
import tec.uom.se.util.UCUM;
import tec.uom.se.util.US;
import static org.junit.Assert.*;

/**
 *
 * @author Werner Keil
 */
public class UnitsTest {

	public UnitsTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Test
	public void testOf() {
		assertEquals(SIPrefix.KILO(SI.GRAM), AbstractUnit.parse("kg")); // TODO: Problem
															// with kg...
	}

	@Test
	public void testAnnotate() {
	}

	@Test
	public void testGetAnnotation() {
	}

	@Test
	public void testGetUnannotatedUnit() {
	}

	@Test
	public void testIsSystemUnit() {
	}

	@Test
	public void testToString() {
		assertEquals("kg", SIPrefix.KILO(UCUM.GRAM).toString());
		assertEquals("lb", UCUM.POUND.toString());
		assertEquals("uz", UCUM.OUNCE.toString()); // TODO try using UCUM.Print for toString()
		assertEquals("g", UCUM.GRAM.toString());
		assertEquals("lb", US.POUND.toString());
	}

	@Test
	public void testGetConverterToSystemUnit() {
	}

	@Test
	public void testGetSymbol() {
		assertEquals("lb", UCUM.POUND.getSymbol());
		assertEquals("oz", UCUM.OUNCE.getSymbol());
		assertEquals("kg", SIPrefix.KILO(UCUM.GRAM).getSymbol());
		assertEquals("g", UCUM.GRAM.getSymbol());
		assertEquals("lb", US.POUND.getSymbol());
	}

	@Test
	public void testGetSystemUnit() {
	}

	@Test
	public void testGetProductUnits() {
	}

	@Test
	public void testGetDimension() {
	}

	@Test
	public void testIsCompatible() {
	}

	@Test
	public void testAsType() {
	}

	@Test
	public void testGetConverterTo() {
	}

	@Test
	public void testGetConverterToAny() {
	}

	@Test
	public void testAlternate() {
	}

	@Test
	public void testTransform() {
	}

	@Test
	public void testAdd() {
	}

	@Test
	public void testMultiply_double() {
	}

	@Test
	public void testMultiply_ErrorType() {
	}

	@Test
	public void testInverse() {
	}

	@Test
	public void testDivide_double() {
	}

	@Test
	public void testDivide_ErrorType() {
	}

	@Test
	public void testRoot() {
	}

	@Test
	public void testPow() {
	}

	@Test
	public void testHashCode() {
	}

	@Test
	public void testEquals() {
	}

}