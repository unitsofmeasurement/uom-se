package tec.uom.se.quantity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.measure.Quantity;
import javax.measure.quantity.Length;

import org.junit.Assert;
import org.junit.Test;

import tec.uom.se.QuantityFactory;
import tec.uom.se.util.SI;
import tec.uom.se.util.US;

public class QuantityTest {

    @Test
    public void toTest() {
        Quantity<Length> metre = QuantityFactory.of(BigDecimal.TEN, SI.METRE);
        Quantity<Length> foot =  metre.to(US.FOOT);
        BigDecimal value = (BigDecimal) foot.getValue();
        value.setScale(4, RoundingMode.HALF_EVEN);
        BigDecimal expected = BigDecimal.valueOf(32.8084);
        Assert.assertEquals(expected, value.setScale(4, RoundingMode.HALF_EVEN));
    }
}
