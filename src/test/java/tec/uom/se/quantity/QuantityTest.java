package tec.uom.se.quantity;

import javax.measure.Quantity;

import org.junit.Test;

import tec.uom.se.AbstractQuantity;
import tec.uom.se.util.SI;
import tec.uom.se.util.US;

@SuppressWarnings({"rawtypes", "unchecked"})
public class QuantityTest {

    @Test
    public void asf() {
        Quantity len = AbstractQuantity.of(10, SI.METRE);
        Quantity len2 = (Quantity)len.to(US.FOOT);
        System.out.println(len2);
    }
}
