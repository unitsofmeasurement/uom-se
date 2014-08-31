package tec.uom.se.quantity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.measure.quantity.Length;

import org.junit.Assert;
import org.junit.Test;

import tec.uom.se.AbstractQuantity;
import tec.uom.se.util.SI;
import tec.uom.se.util.US;

public class QuantityTest {

    @Test
    public void toTest() {
        Length metre = (Length) AbstractQuantity.of(10, SI.METRE);
        Length foot = (Length) metre.to(US.FOOT);
        BigDecimal value = (BigDecimal) foot.getValue();
        value.setScale(4, RoundingMode.HALF_EVEN);
        BigDecimal expected = BigDecimal.valueOf(32.8084);
        Assert.assertEquals(expected, value.setScale(4, RoundingMode.HALF_EVEN));
    }
}
