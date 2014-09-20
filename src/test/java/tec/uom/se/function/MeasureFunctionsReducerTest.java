package tec.uom.se.function;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import javax.measure.Quantity;
import javax.measure.function.QuantityFactory;
import javax.measure.quantity.Time;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tec.uom.se.quantity.QuantityFactoryProvider;
import tec.uom.se.util.SI;

public class MeasureFunctionsReducerTest {

    private QuantityFactory<Time> timeFactory;
    private Quantity<Time> day;
    private Quantity<Time> hours;
    private Quantity<Time> minutes;
    private Quantity<Time> seconds;

    @Before
    public void init() {
        timeFactory = QuantityFactoryProvider.of(Time.class);
        minutes = timeFactory.create(15, SI.MINUTE);
        hours = timeFactory.create(18, SI.HOUR);
        day = timeFactory.create(1, SI.DAY);
        seconds = timeFactory.create(100, SI.SECOND);
    }

    @Test
    public void minTest() {
        List<Quantity<Time>> times = getTimes();
        Quantity<Time> quantity = times.stream().reduce(MeasureFunctions.min()).get();
        Assert.assertEquals(seconds, quantity);

         List<Quantity<Time>> secondsList = Arrays.asList(
         timeFactory.create(300, SI.SECOND),
         timeFactory.create(130, SI.SECOND), seconds,
         timeFactory.create(10000, SI.SECOND));
         Quantity<Time> minSeconds =
         secondsList.stream().reduce(MeasureFunctions.min()).get();
         Assert.assertEquals(seconds, minSeconds);
    }

    @Test
    public void maxTest() {
        List<Quantity<Time>> times = getTimes();
        Quantity<Time> quantity = times.stream().reduce(MeasureFunctions.max()).get();
        Assert.assertEquals(day, quantity);
        Quantity<Time> max = timeFactory.create(20, SI.DAY);
         List<Quantity<Time>> dayList = Arrays.asList(
         timeFactory.create(3, SI.DAY),
         timeFactory.create(5, SI.DAY),
         max);
         Quantity<Time> maxDay =
                 dayList.stream().reduce(MeasureFunctions.max()).get();
         Assert.assertEquals(max, maxDay);
    }

    @Test
    public void sumTest() {
        List<Quantity<Time>> dayList = Arrays.asList(
        timeFactory.create(3, SI.DAY),
        timeFactory.create(5, SI.DAY),
        timeFactory.create(20, SI.DAY));
        Quantity<Time> sumDay = dayList.stream().reduce(MeasureFunctions.sum()).get();
        assertEquals(Double.valueOf(sumDay.getValue().doubleValue()), Double.valueOf(28));
        assertEquals(sumDay.getUnit(), SI.DAY);
    }

    @Test
    public void shouldSumWhenHasDifferentTimeUnits() {
        List<Quantity<Time>> dayList = Arrays.asList(
        timeFactory.create(48, SI.HOUR),
        timeFactory.create(5, SI.DAY),
        timeFactory.create(1440, SI.MINUTE));
        Quantity<Time> sumHour = dayList.stream().reduce(MeasureFunctions.sum()).get();
        assertEquals(Double.valueOf(sumHour.getValue().doubleValue()), Double.valueOf(192));
        assertEquals(sumHour.getUnit(), SI.HOUR);
    }

    @Test
    public void sumWithConvertTest() {

        List<Quantity<Time>> dayList = Arrays.asList(
        timeFactory.create(48, SI.HOUR),
        timeFactory.create(5, SI.DAY),
        timeFactory.create(1440, SI.MINUTE));

        Quantity<Time> sumHour = dayList.stream().reduce(MeasureFunctions.sum(SI.HOUR)).get();
        Quantity<Time> sumDay = dayList.stream().reduce(MeasureFunctions.sum(SI.DAY)).get();
        Quantity<Time> sumMinute = dayList.stream().reduce(MeasureFunctions.sum(SI.MINUTE)).get();
        Quantity<Time> sumSecond = dayList.stream().reduce(MeasureFunctions.sum(SI.SECOND)).get();

        assertEquals(Double.valueOf(sumHour.getValue().doubleValue()), Double.valueOf(192));
        assertEquals(sumHour.getUnit(), SI.HOUR);

        assertEquals(Double.valueOf(sumDay.getValue().doubleValue()), Double.valueOf(8));
        assertEquals(sumDay.getUnit(), SI.DAY);

        assertEquals(Double.valueOf(sumMinute.getValue().doubleValue()), Double.valueOf(11520));
        assertEquals(sumMinute.getUnit(), SI.MINUTE);

        assertEquals(Double.valueOf(sumSecond.getValue().doubleValue()), Double.valueOf(691200));
        assertEquals(sumSecond.getUnit(), SI.SECOND);
    }

    private List<Quantity<Time>> getTimes() {
        return Arrays.asList(day, hours, minutes, seconds);
    }
}
