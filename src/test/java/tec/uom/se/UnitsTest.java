package tec.uom.se;

import static org.junit.Assert.assertEquals;
import static tec.uom.se.util.SI.GRAM;
import static tec.uom.se.util.SIPrefix.KILO;

import org.junit.Test;

public class UnitsTest {
//	private final AbstractUnit<Length> sut = new BaseUnit<Length>("m");
	
    @Test
    public void testOf() {
        assertEquals(KILO(GRAM), AbstractUnit.of("kg"));
    }
}

