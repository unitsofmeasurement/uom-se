package tec.uom.se.util;

import static org.junit.Assert.assertEquals;
import static tec.uom.se.util.SI.METRE;
import static tec.uom.se.util.SIPrefix.YOTTA;
import static tec.uom.se.util.SIPrefix.ZETTA;

import java.math.BigInteger;

import org.junit.Test;

import tec.uom.se.function.RationalConverter;

public class SITest {
	@Test
	public void testPrefixes() {
		assertEquals(YOTTA(METRE).getConverterTo(ZETTA(METRE)),
				new RationalConverter(new BigInteger("9"), new BigInteger("500000000000000000000")));
	}
}
