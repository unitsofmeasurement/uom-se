package tec.uom.se;

import static org.junit.Assert.assertEquals;
import static tec.uom.se.util.SI.GRAM;
import static tec.uom.se.util.SIPrefix.KILO;

import javax.measure.quantity.Length;

import org.junit.Ignore;
import org.junit.Test;

import tec.uom.se.unit.BaseUnit;

public class UnitsSETest {
	private final AbstractUnit<Length> sut = new BaseUnit<Length>("m");
    
    @Test
    @Ignore
    public void testReturnedClass() {
    	assertEquals("Q", String.valueOf(sut.getActualType()));
    }
}

